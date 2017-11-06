package com.sunveee.joice.cache.model;

/**
 * 缓存Key
 * 
 * @author 51
 * @version $Id: Key.java, v 0.1 2017年10月30日 下午2:37:04 51 Exp $
 */
public class Key extends BaseModel {
    /**  */
    private static final long serialVersionUID = -7622279807609331894L;

    private String            keyStr;

    public Key(String keyStr) {
        this.keyStr = keyStr;
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

}
