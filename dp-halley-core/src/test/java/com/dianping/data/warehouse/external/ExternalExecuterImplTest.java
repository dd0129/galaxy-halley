package com.dianping.data.warehouse.external;

import com.dianping.data.warehouse.common.MockData;
import com.dianping.data.warehouse.domain.InstanceDO;
import org.junit.Test;

/**
 * Created by hongdi.tang on 14-5-4.
 */
public class ExternalExecuterImplTest {
    @Test
    public void testExecute() throws Exception {
        InstanceDO inst = MockData.genInstance();
        ExternalExecuter executer = new DQCExecuterImpl();
        executer.execute(inst);
    }
}
