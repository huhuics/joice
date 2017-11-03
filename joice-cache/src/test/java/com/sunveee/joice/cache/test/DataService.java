package com.sunveee.joice.cache.test;

public class DataService {

    private static String[] strData = { "str1", "str2", "str3" };

    public String getString(int index) {
        if (index < 0 || index >= strData.length) {
            return null;
        }
        return strData[index];
    }

}
