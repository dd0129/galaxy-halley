package com.dianping.data.warehouse.halley.service;

import com.dianping.data.warehouse.halley.domain.TaskSQLParserDO;
import com.dianping.data.warehouse.halley.domain.TaskSQLParserDO;

/**
 * Created by hongdi.tang on 14-6-9.
 */
public interface TaskPublish {

    public int rollback(String gitPath, String filePath);

    /**
     * 任务发布
     * 返回DOL解析结果
     * 请在使用前保证参数均不为null
     */
    public TaskSQLParserDO publish(String gitPath, String filePath);
}
