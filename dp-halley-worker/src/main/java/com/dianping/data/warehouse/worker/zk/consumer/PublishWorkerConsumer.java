package com.dianping.data.warehouse.worker.zk.consumer;

import com.dianping.data.warehouse.halley.domain.PublishFileDO;
import com.dianping.data.warehouse.worker.common.Const;
import com.dianping.data.warehouse.worker.dao.PublishLogDAO;
import com.dianping.data.warehouse.worker.hdfs.HdfsUtils;
import com.dianping.data.warehouse.worker.jgit.JGitUtils;
import com.dianping.data.warehouse.worker.utils.HalleyStrUtils;
import com.dianping.data.warehouse.worker.utils.WorkerUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.queue.QueueConsumer;
import org.apache.curator.framework.state.ConnectionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("publishWorkerConsumter")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class PublishWorkerConsumer implements QueueConsumer<PublishFileDO> {

    @Resource
    private PublishLogDAO publishLogDAO;

    private static Logger logger = LoggerFactory.getLogger(PublishWorkerConsumer.class);

    @Override
	public void consumeMessage(PublishFileDO publishFile){
        logger.info("worker node consume starts:" + publishFile.getPublishId());
        String ip = null;
        try{
            ip = WorkerUtils.getHostIP();
            String hdfsFile = HalleyStrUtils.concatPathStr(Const.HDFS_DIR,publishFile.getFilename());
            String localFile = getLocalFile(publishFile);
            boolean flag = HdfsUtils.download(hdfsFile, localFile);
            publishFile.setHost(ip);
            publishFile.setFlag(flag);
            publishFile.setTimestamp(HalleyStrUtils.getCurrentTime());
        }catch(Exception e){
            logger.error("worker consumer fail",e);
            publishFile.setFlag(false);
        }finally{
            try{
                publishLogDAO.insertPublishFile(publishFile);
            }catch(Exception e){
                logger.error("save publish file error",e);
            }
        }
        logger.info("worker node consume ends:" + publishFile.getPublishId());
	}

    public String getLocalFile(PublishFileDO publishFile){
        if(publishFile.getProjectName().equals("warehouse") ||
                publishFile.getProjectName().equals("honesty_online")){
            return HalleyStrUtils.concatPathStr(JGitUtils.getWorkerBaseDir(publishFile.getProjectName()),publishFile.getFilename());
        }else if(publishFile.getProjectName().equals("data_analysis")){
            if(publishFile.getFilename().startsWith("/data_analysis") ||
                    publishFile.getFilename().startsWith("data_analysis")){
                String fileName = StringUtils.substringAfter(publishFile.getFilename(),"data_analysis");
                return HalleyStrUtils.concatPathStr(JGitUtils.getWorkerBaseDir(publishFile.getProjectName()),fileName);
            }else{
                return HalleyStrUtils.concatPathStr(JGitUtils.getWorkerBaseDir(publishFile.getProjectName()),publishFile.getFilename());
            }
        }else{
            return null;
        }
    }

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {

    }



}