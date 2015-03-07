package com.dianping.data.warehouse.external;

import com.dianping.data.warehouse.domain.InstanceDO;
import com.dianping.data.warehouse.halley.domain.TaskReturnDO;

/**
 * Created by hongdi.tang on 14-4-1.
 */

public interface ExternalExecuter {

    TaskReturnDO execute(InstanceDO inst);
}
