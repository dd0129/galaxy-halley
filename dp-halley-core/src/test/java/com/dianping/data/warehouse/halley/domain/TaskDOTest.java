package com.dianping.data.warehouse.halley.domain;

import org.junit.Test;

/**
 * Created by hongdi.tang on 14-4-15.
 */
public class TaskDOTest {
    @Test
    public void testGetAddTime() throws Exception {
        String abc ="测试";
        System.out.println(abc);
        TaskDO task = new TaskDO();
        task.setTaskId(123123);;
        TaskDO t1 = (TaskDO)task.clone();
        System.out.println(task == t1);

    }
}
