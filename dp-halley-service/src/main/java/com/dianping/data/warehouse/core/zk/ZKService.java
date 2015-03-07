package com.dianping.data.warehouse.core.zk;


import com.dianping.data.warehouse.core.lion.LionUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Zookeeper工具类
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ZKService {

    private static final Logger LOG = LoggerFactory.getLogger(ZKService.class);

    private String ZK_CONNECTION_URL;

	private final String DQC_NAMESPACE = "halley_ns";
	private final String DQC_ZK_CONNECTION_URL_LABEL = "galaxy-halley.zk.connection.url";

	private final int SLEEP_BETWEEN_TRIES = 1000;

	private final int RETRY_COUNT = 10;

	private CuratorFramework zk;

	@PostConstruct
	public void init() {
	    ZK_CONNECTION_URL = LionUtil.getProperty(DQC_ZK_CONNECTION_URL_LABEL);
	}

	public synchronized CuratorFramework getZookeeper() {
		if (zk == null) {
			// 重试指定的次数，且每一次重试之间停顿的时间逐渐增加
			org.apache.curator.RetryPolicy retryPolicy = new ExponentialBackoffRetry(SLEEP_BETWEEN_TRIES, RETRY_COUNT);

            zk = CuratorFrameworkFactory.builder().connectString(ZK_CONNECTION_URL).namespace(DQC_NAMESPACE)
                    .retryPolicy(retryPolicy).connectionTimeoutMs(5000).build();
            zk.start();
		}
		return zk;
	}

	public synchronized void close() {
	    if (zk != null) {
	        CloseableUtils.closeQuietly(zk);
	    }
	}

}