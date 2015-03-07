package com.dianping.data.warehouse.halley.client;

import com.dianping.data.warehouse.halley.domain.TaskReturnDO;

import java.util.Map;

/**
 * Created by hongdi.tang on 14-4-3.
 */
public interface TaskClient {
    public TaskReturnDO run(Map<String,String> cmds);
}
