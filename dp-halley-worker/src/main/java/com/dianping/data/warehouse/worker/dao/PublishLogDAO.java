package com.dianping.data.warehouse.worker.dao;



import com.dianping.data.warehouse.halley.domain.PublishFileDO;

import java.util.List;

/**
 * Created by hongdi.tang on 14-6-23.
 */
public interface PublishLogDAO {
    public List<PublishFileDO> getPublishListByID(String id);

    public void insertPublishFile(PublishFileDO file);
}
