package com.dianping.data.warehouse.halley.domain;

import java.io.Serializable;

/**
 * Created by hongdi.tang on 2014/9/10.
 */
public class TaskOwnerDO implements Serializable{
    private static final long serialVersionUID = 9157286313558103192L;
    private String pinyinName;
    private String mail;
    private String phone;

    public String getPinyinName() {
        return pinyinName;
    }

    public void setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
