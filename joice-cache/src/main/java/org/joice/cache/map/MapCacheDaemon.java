/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.map;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.joice.cache.config.CacheConfig;
import org.joice.cache.serializer.HessianSerializer;
import org.joice.cache.serializer.Serializer;
import org.joice.cache.to.CacheWrapper;
import org.joice.cache.util.OSUtil;
import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MapCache的守护线程,负责缓存的持久化等工作
 * @author HuHui
 * @version $Id: MapCacheDaemon.java, v 0.1 2017年10月19日 下午9:09:05 HuHui Exp $
 */
public class MapCacheDaemon implements Runnable {

    private static final Logger      logger   = LoggerFactory.getLogger(MapCacheDaemon.class);

    private final MapCache           mapCache;

    private final CacheConfig        config;

    private final Serializer<Object> serializer;

    private boolean                  isRun    = true;

    private final String             fileName = "map.cache";

    public MapCacheDaemon(MapCache mapCache, CacheConfig config) {
        LogUtil.info(logger, "MapCacheDaemon init...");
        this.mapCache = mapCache;
        this.config = config;
        this.serializer = new HessianSerializer<Object>();
        LogUtil.info(logger, "MapCacheDaemon init success!");
    }

    @Override
    public void run() {
        while (isRun) {
            //清理过期缓存
            clearCache();
            try {
                Thread.sleep(config.getTimeBetweenPersist() * 1000);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
            //持久化缓存
            persistCache();
        }
    }

    /**
     * 清理过期缓存
     */
    public void clearCache() {
        LogUtil.info(logger, "开始清理过期缓存");
        int cnt = 0;
        ConcurrentHashMap<String, Object> cache = mapCache.getCache();
        Iterator<Entry<String, Object>> iterator = cache.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, Object> entry = iterator.next();
            Object obj = entry.getValue();
            if (obj instanceof CacheWrapper) {
                CacheWrapper wrapper = (CacheWrapper) obj;
                if (wrapper.isExpire()) {
                    iterator.remove();
                    ++cnt;
                }
            }
        }
        LogUtil.info(logger, "过期缓存清理完毕,清理数量:{0}", cnt);
    }

    /**
     * 持久化缓存数据
     */
    public void persistCache() {
        if (!config.isPersist()) {
            return;
        }
        LogUtil.info(logger, "开始持久化缓存");
        File file = createCacheFile();
        OutputStream out = null;
        BufferedOutputStream bos = null;
        try {
            out = new FileOutputStream(file);
            bos = new BufferedOutputStream(out);
            bos.write(serializer.serialize(mapCache.getCache()));
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            try {
                bos.close();
                out.close();
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        LogUtil.info(logger, "缓存持久化完成");
    }

    /**
     * 从磁盘读取缓存
     */
    @SuppressWarnings("unchecked")
    public void readCacheFromDisk() {
        LogUtil.info(logger, "开始从磁盘读取缓存");
        String filePath = getSavePath() + fileName;
        File file = new File(filePath);
        if (file.exists() && file.length() > 0) {
            InputStream in = null;
            BufferedInputStream bis = null;
            ByteArrayOutputStream baos = null;
            try {
                in = new FileInputStream(file);
                bis = new BufferedInputStream(in);
                baos = new ByteArrayOutputStream();

                byte[] bytes = new byte[1024];
                while (bis.read(bytes) != -1) {
                    baos.write(bytes);
                }

                Object obj = serializer.deserialize(baos.toByteArray());
                if (obj instanceof ConcurrentHashMap) {
                    ConcurrentHashMap<String, Object> map = (ConcurrentHashMap<String, Object>) obj;
                    mapCache.getCache().putAll(map);
                    LogUtil.info(logger, "缓存读取了{0}个对象", map.size());
                }
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                try {
                    bis.close();
                    in.close();
                    baos.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }
        LogUtil.info(logger, "缓存读取完毕");
    }

    public File createCacheFile() {
        String folderPath = getSavePath();
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = folderPath + fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        return file;
    }

    /**
     * 获取缓存存储路径
     */
    private String getSavePath() {
        String configPath = config.getPersistecePath();
        String savePath = "";
        if (OSUtil.isLinux()) {
            savePath = configPath + File.separatorChar;
        } else if (OSUtil.isWindows()) {
            savePath = "F:" + configPath + File.separatorChar;
        }
        return savePath;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean isRun) {
        this.isRun = isRun;
    }

}
