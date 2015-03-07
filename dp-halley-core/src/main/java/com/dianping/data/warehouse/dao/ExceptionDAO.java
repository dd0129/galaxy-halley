package com.dianping.data.warehouse.dao;

import com.dianping.data.warehouse.domain.ExceptionAlertDO;

import java.util.List;

/**
 * Created by Sunny on 14-7-30.
 */
public interface ExceptionDAO {

    public List<ExceptionAlertDO> getExceptionAlertsByProduct(String product);

}
