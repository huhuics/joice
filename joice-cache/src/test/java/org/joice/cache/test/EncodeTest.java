/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.test;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author HuHui
 * @version $Id: EncodeTest.java, v 0.1 2017年11月14日 下午3:02:48 HuHui Exp $
 */
public class EncodeTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "��թ";
        String gbkStr = new String(str.getBytes(), "UTF-8");
        System.out.println(gbkStr);

        String utf8Str = new String(gbkStr.getBytes("UTF-8"), "UTF-8");
        System.out.println(utf8Str);

    }

}
