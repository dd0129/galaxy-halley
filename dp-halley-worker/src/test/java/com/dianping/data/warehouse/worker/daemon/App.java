package com.dianping.data.warehouse.worker.daemon;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统计进程入口类
 * 
 * @author outofmemory.cn
 * @date 2013-03-06
 */
public class App implements Daemon {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {       
        logger.info("radar alarm main start ...");
        App app = new App();
        app.init(null);
        app.start();
        logger.info("radar alarm  main started");
    }


    @Override
    public void init(DaemonContext dc) throws DaemonInitException, Exception {
        logger.info("initializing ...");
    }

    @Override
    public void start() throws Exception {
        logger.info("starting ...");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void stop() throws Exception {
        logger.info("stopping ...");
    }

    @Override
    public void destroy() {
        logger.info("done.");
    }

 }