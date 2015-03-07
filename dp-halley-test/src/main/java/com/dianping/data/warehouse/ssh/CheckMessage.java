package com.dianping.data.warehouse.ssh;

/**
 * @author <a href="mailto:xiong.chen@dianping.com">chenxiong</a>
 * @since 14-5-4 PM6:40
 */
public enum  CheckMessage {
    PARAM_ERROR("参数错误"),
    MASTER_ERROR("主数据无任务对应的表信息"),
    REACH_RETRY_MAX_ERROR("达到重试次数上限");


    private final String message;

    private CheckMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
