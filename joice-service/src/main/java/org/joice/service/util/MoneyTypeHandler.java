/**
 * Beijing Jiaotong University
 * Copyright (c) 1896-2016 All Rights Reserved.
 */
package org.joice.service.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joice.common.util.Money;

/**
 * 处理表示Money的字段
 * @author HuHui
 * @version $Id: MoneyTypeHandler.java, v 0.1 2016年6月12日 下午10:21:45 HuHui Exp $
 */
public class MoneyTypeHandler extends BaseTypeHandler<Money> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Money parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter.getCent());
    }

    @Override
    public Money getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Money money = new Money();
        money.setCent(rs.getLong(columnName));
        return money;
    }

    @Override
    public Money getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Money money = new Money();
        money.setCent(rs.getLong(columnIndex));
        return money;
    }

    @Override
    public Money getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Money money = new Money();
        money.setCent(cs.getLong(columnIndex));
        return money;
    }

}
