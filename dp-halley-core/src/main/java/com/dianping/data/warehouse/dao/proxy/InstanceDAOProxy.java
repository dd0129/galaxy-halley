package com.dianping.data.warehouse.dao.proxy;

import com.dianping.data.warehouse.dao.InstanceDAO;
import com.dianping.data.warehouse.domain.InstanceDO;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * Created by hongdi.tang on 14-3-28.
 */
@Component("instDAOProxy")
public class InstanceDAOProxy {
    @Resource
    private InstanceDAO instDAO;

    public void saveInstance(InstanceDO inst){
        instDAO.saveInstance(inst);
        if(!CollectionUtils.isEmpty(inst.getInstRelaList())){
            instDAO.saveInstanceRela(inst.getInstRelaList());
        }
    }

}
