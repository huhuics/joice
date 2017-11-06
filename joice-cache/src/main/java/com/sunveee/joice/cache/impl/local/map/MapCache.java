package com.sunveee.joice.cache.impl.local.map;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunveee.joice.cache.Cache;
import com.sunveee.joice.cache.model.Key;
import com.sunveee.joice.cache.model.Value;

/**
 * 本地缓存实现类<br>
 * 以ConcurrentHashMap的方式实现本地缓存
 * 
 * @author 51
 * @version $Id: MapCache.java, v 0.1 2017年10月30日 下午5:11:33 51 Exp $
 */
public class MapCache implements Cache {

    private static final Logger              logger = LoggerFactory.getLogger(MapCache.class);

    private ConcurrentHashMap<String, Value> cache;

    public MapCache() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public void set(Key key, Value value) {
        String keyStr;
        if (null == key || null == value || StringUtils.isBlank(keyStr = key.getKeyStr())) {
            return;
        }
        cache.put(keyStr, value);
        LogUtil.info(logger, "MapCache写缓存成功: keyStr={0},value={1}", keyStr, value);

    }

    @Override
    public Value get(Key key) {
        LogUtil.info(logger, "MapCache读缓存: key={0}", key);
        String keyStr;
        if (null == key || StringUtils.isBlank(keyStr = key.getKeyStr())) {
            return null;
        }
        Value result = null;
        try {
            result = cache.get(keyStr);
        } catch (Exception e) {
            LogUtil.error(e, logger, "MapCache读缓存发生异常,key={0}", key);
        }
        if (null == result) {
            return null;
        }
        if (result.isExpire()) {
            cache.remove(keyStr);
            return null;
        }
        LogUtil.info(logger, "MapCache读缓存成功: key={0},result={1}", key, result);
        return result;
    }

}
