package com.dianping.data.warehouse.core.common;

import com.dianping.data.warehouse.halley.domain.TaskDO;
import org.junit.Test;

/**
 * Created by hongdi.tang on 14-6-18.
 */
public class ValidatorUtilsTest {
    @Test
    public void testValidateModel() throws Exception {
        TaskDO task = new TaskDO();
        task.setTaskId(12312);
        task.setDatabaseSrc("");
        task.setAddUser(" ");
        ValidatorUtils.validateModel(task);
    }
}
