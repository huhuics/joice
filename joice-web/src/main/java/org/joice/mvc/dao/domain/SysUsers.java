package org.joice.mvc.dao.domain;

import org.joice.common.base.BaseDomain;

public class SysUsers extends BaseDomain {

    /**  */
    private static final long serialVersionUID = 7174585560905356628L;

    private Long              id;

    private String            username;

    private String            password;

    private String            salt;

    private Boolean           locked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}