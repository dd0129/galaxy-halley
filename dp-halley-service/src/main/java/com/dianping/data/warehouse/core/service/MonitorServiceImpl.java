package com.dianping.data.warehouse.core.service;

import com.dianping.data.warehouse.core.common.Const;
import com.dianping.data.warehouse.core.dao.MonitorDAO;
import com.dianping.data.warehouse.core.util.LionUtils;
import com.dianping.data.warehouse.halley.domain.MonitorDO;
import com.dianping.data.warehouse.halley.service.MonitorService;
import com.dianping.pigeon.remoting.provider.config.annotation.Service;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by Sunny on 14-8-12.
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    private static Logger logger = LoggerFactory.getLogger(MonitorServiceImpl.class);

    @Resource
    private MonitorDAO monitorDAO;

    @Override
    public int rollback(String gitPath, String filePath) {
        return 0;
    }

    /**
     * 获得所有的值班人员
     */
    @Override
    public List<MonitorDO> getMonitors() {
        logger.info("get monitors");
        try {
            List<MonitorDO> monitorDOs = monitorDAO.getMonitors();
            if (monitorDOs == null || monitorDOs.isEmpty())
                return null;
            else
                return monitorDOs;
        } catch (Exception e) {
            logger.error("getMonitor ", e);
            return null;
        }
    }

    /**
     * 在值班人员表中插入一条新纪录
     */
    @Override
    public Integer addMonitorRecord(MonitorDO monitorDO) {
        logger.info("add monitor record: " + monitorDO.getPinyinName());
        return monitorDAO.addMonitorRecord(monitorDO);
    }

    /**
     * 用户是否是管理员
     */
    @Override
    public boolean isAdmin(int loginId) {
        logger.info("is admin: " + loginId);
        String aclAddress = LionUtils.getValue(Const.LION_ACL_RUL);
        String aclAuthorityKey = LionUtils.getValue(Const.LION_ACL_AK_ID + "1");
        if (aclAddress == null)
            aclAddress = Const.ACL_ADDRESS_PRODUCT;
        if (aclAuthorityKey == null)
            aclAuthorityKey = Const.ACL_ADMIN_KEY_PRODUCT;
        String url = getACLRequestAddress(aclAddress, loginId, aclAuthorityKey);
        String context = null;
        try {
            context = Jsoup.connect(url).ignoreContentType(true).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (context == null)
            return false;
        JSONObject jsonObj = JSONObject.fromObject(context);
        return jsonObj.getInt("msg") == 1;
    }

    /**
     * 用户是否是值班人员
     */
    @Override
    public boolean isMonitor(String pinyinName) {
        logger.info("is monitor: " + pinyinName);
        List<MonitorDO> monitorDOs = monitorDAO.getMonitors();
        if (monitorDOs == null || monitorDOs.isEmpty())
            return false;
        MonitorDO currentMonitor = monitorDOs.get(0);
        if (currentMonitor == null)
            return false;
        return currentMonitor.getPinyinName().equals(pinyinName);
    }

    private String getACLRequestAddress(String aclAddress, int loginId, String aclAuthorityKey) {
        return aclAddress + "/hasPower?info={login_id:" + loginId + ",authcode:" + aclAuthorityKey + "}";
    }

}
