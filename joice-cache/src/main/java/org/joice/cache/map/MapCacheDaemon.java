/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.map;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;

import org.joice.cache.config.CacheConfig;
import org.joice.cache.serializer.HessianSerializer;
import org.joice.cache.serializer.Serializer;
import org.joice.cache.util.LogUtil;
import org.joice.cache.util.OSUtil;
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

    private boolean                  isRun    = false;

    private final String             fileName = "map.cache";

    public MapCacheDaemon(MapCache mapCache, CacheConfig config) {
        LogUtil.info(logger, "MapCacheDaemon init...");
        this.mapCache = mapCache;
        this.config = config;
        this.serializer = new HessianSerializer<Object>();
        LogUtil.info(logger, "MapCacheDaemon init success!");
    }

    /**
     * 持久化缓存数据
     */
    public void persistCache() {
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
    }

    /**
     * 从磁盘读取缓存
     */
    @SuppressWarnings("unchecked")
    public void readCacheFromDisk() {
        String filePath = getSavePath() + fileName;
        File file = new File(filePath);
        int fileLength = (int) file.length();
        if (file.exists() && fileLength > 0) {
            InputStream in = null;
            BufferedInputStream bis = null;
            try {
                in = new FileInputStream(file);
                bis = new BufferedInputStream(in);
                byte[] b = new byte[fileLength];
                bis.read(b);
                Object obj = serializer.deserialize(b);
                if (obj instanceof ConcurrentHashMap) {
                    mapCache.getCache().putAll((ConcurrentHashMap<String, Object>) obj);
                }
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                try {
                    bis.close();
                    in.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }
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

    @Override
    public void run() {
    }

}
