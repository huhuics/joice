/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.type;

import java.util.Comparator;

import org.joice.cache.to.AutoLoadTO;

/**
 * 缓存数据加入队列时排序枚举
 * @author HuHui
 * @version $Id: AutoLoadQueueSortType.java, v 0.1 2017年9月28日 下午2:56:13 HuHui Exp $
 */
public enum AutoLoadQueueSortType {

    /** 默认顺序 */
    NONE(0, null),

    ;

    private Integer                id;

    private Comparator<AutoLoadTO> comparator;

    private AutoLoadQueueSortType(Integer id, Comparator<AutoLoadTO> comparator) {
        this.id = id;
        this.comparator = comparator;
    }

    public static AutoLoadQueueSortType getById(Integer id) {
        if (null == id) {
            return NONE;
        }
        AutoLoadQueueSortType values[] = AutoLoadQueueSortType.values();
        for (AutoLoadQueueSortType tmp : values) {
            if (id.intValue() == tmp.getId().intValue()) {
                return tmp;
            }
        }
        return NONE;
    }

    public Integer getId() {
        return id;
    }

    public Comparator<AutoLoadTO> getComparator() {
        return comparator;
    }

}
