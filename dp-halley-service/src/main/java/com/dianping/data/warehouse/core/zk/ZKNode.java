package com.dianping.data.warehouse.core.zk;

import com.dianping.data.warehouse.core.lion.LionUtil;
import com.dianping.data.warehouse.halley.domain.PublishFileDO;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.DistributedPriorityQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * ZooKeeper节点
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public abstract class ZKNode implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(ZKNode.class);

    @Resource
    private ZKService zkService;

    private static String JOB_QUEUE_PATH;

	private Map<String,DistributedPriorityQueue<PublishFileDO>> jobQueueMap = new HashMap<String, DistributedPriorityQueue<PublishFileDO>>();


    protected CuratorFramework zk;
    private final static String INCOMPLETEDQUEUE = "galaxy-halley.zk.publish.inCompleteQueue";

    static {
        JOB_QUEUE_PATH = LionUtil.getProperty(INCOMPLETEDQUEUE);
        LOG.info(JOB_QUEUE_PATH);
    }

    public void init() throws Exception {
        zk = zkService.getZookeeper();
    }

    public void pushJobQueue(PublishFileDO item)  {
	    try {
            for(org.apache.curator.framework.recipes.queue.DistributedPriorityQueue<PublishFileDO> jobQueue :jobQueueMap.values()){
                jobQueue.put(item, 100);
            }
	    } catch (Exception ex) {
	        LOG.error("推送未完成任务队列失败", ex);
            throw new RuntimeException("推送未完成任务队列失败", ex);
	    }
    }

	/**
	 * 开始监听ZooKeeper中的队列
	 */
    public void start() {
        String[] hosts = LionUtil.getProperty("galaxy-halley.worker_hosts").split(";");
        for(String host : hosts){
            String jobPath = JOB_QUEUE_PATH + host;
            DistributedPriorityQueue<PublishFileDO> jobQueue = QueueBuilder.builder(zk, null, new QueueItemSerializer(), jobPath).buildPriorityQueue(5);
            jobQueueMap.put(host,jobQueue);
        }

        try {
            for(DistributedPriorityQueue<PublishFileDO> queue : jobQueueMap.values()){
                queue.start();
            }
        } catch (Exception ex) {
            LOG.error("未完成任务队列启动失败", ex);
            throw new RuntimeException("未完成任务队列启动失败", ex);
        }

    }

}