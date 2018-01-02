package org.joice.common.dao.domain;

import java.util.Date;

import org.joice.common.base.BaseDomain;
import org.joice.common.util.Money;

public class BizUserAccount extends BaseDomain {

    /**  */
    private static final long serialVersionUID = 4220587095292118354L;

    private Integer           id;

    private String            userId;

    private Money             accountBalance;

    private Date              gmtUpdate;

    private Date              gmtCreate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Money getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Money accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}