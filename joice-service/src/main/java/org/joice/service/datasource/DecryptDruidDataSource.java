/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.service.datasource;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * 扩展DruidDataSource,增加数据库密码密文解密功能
 * @author HuHui
 * @version $Id: DecryptDruidDataSource.java, v 0.1 2017年9月4日 下午5:10:57 HuHui Exp $
 */
public class DecryptDruidDataSource extends DruidDataSource {

    /**  */
    private static final long serialVersionUID = 2541031155400100961L;

    @Override
    public void setPassword(String password) {
        try {
            this.password = ConfigTools.decrypt(password);
        } catch (Exception e) {
            throw new RuntimeException("机密数据库密码出错", e);
        }
        super.setPassword(this.password);
    }

}
