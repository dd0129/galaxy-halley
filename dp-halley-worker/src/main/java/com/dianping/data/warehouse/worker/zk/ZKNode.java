package com.dianping.data.warehouse.worker.zk;

import com.dianping.data.warehouse.halley.domain.PublishFileDO;
import com.dianping.data.warehouse.worker.lion.LionUtil;
import com.dianping.data.warehouse.worker.utils.WorkerUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.DistributedPriorityQueue;
import org.apache.curator.framework.recipes.queue.DistributedQueue;
import org.apache.curator.framework.recipes.queue.QueueBuilder;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
    private static String COMPLETE_JOB_QUEUE_PATH;

    private DistributedPriorityQueue<PublishFileDO> jobQueue;
    private DistributedQueue<PublishFileDO> completeJobQueue;

    public QueueConsumer<PublishFileDO> jobQueueConsumer;
    public QueueConsumer<PublishFileDO> completeJobQueueConsumer;

    protected CuratorFramework zk;
    private final static String INCOMPLETEDQUEUE = "galaxy-halley.zk.publish.inCompleteQueue";
    private final static String COMPLETEDQUEUE = "galaxy-halley.zk.publish.completeQueue";

    static {
        JOB_QUEUE_PATH = LionUtil.getProperty(INCOMPLETEDQUEUE) ;
        COMPLETE_JOB_QUEUE_PATH = LionUtil.getProperty(COMPLETEDQUEUE);
    }

    public void init() throws Exception {
        zk = zkService.getZookeeper();
    }

    public void pushJobQueue(PublishFileDO item)  {
        try {
            jobQueue.put(item, 100);
        } catch (Exception ex) {
            LOG.error("推送未完成任务队列失败", ex);
            throw new RuntimeException("推送未完成任务队列失败", ex);
        }
    }

    /**
     * 推送到已完成任务队列
     * @param item
     */
    public void pushCompleteQueue(PublishFileDO item) {
        try {
            completeJobQueue.put(item);
        } catch (Exception ex) {
            LOG.error("推送完成任务队列失败", ex);
            throw new RuntimeException("推送完成任务队列失败", ex);
        }
    }

    /**
     * 开始监听ZooKeeper中的队列
     */
    public void start() throws Exception {
        String ip = WorkerUtils.getHostIP();
        LOG.info("worker host ip :" + ip);
        String jobPath = JOB_QUEUE_PATH + ip;
        LOG.info("worker job path: " +jobPath);
        this.jobQueue = QueueBuilder.builder(zk, jobQueueConsumer, new QueueItemSerializer(), jobPath).buildPriorityQueue(5);

        try {
            this.jobQueue.start();
        } catch (Exception ex) {
            LOG.error("未完成任务队列启动失败", ex);
            throw new RuntimeException("未完成任务队列启动失败", ex);
        }
    }

}