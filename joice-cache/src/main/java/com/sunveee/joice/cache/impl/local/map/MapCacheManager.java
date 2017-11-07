package com.sunveee.joice.cache.impl.local.map;

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

import org.joice.common.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunveee.joice.cache.CacheManager;
import com.sunveee.joice.cache.config.CacheConfig;
import com.sunveee.joice.cache.model.Value;
import com.sunveee.joice.cache.serializer.HessianSerializer;
import com.sunveee.joice.cache.serializer.Serializer;

/**
 * Map缓存管理类
 * <p>包括清理过期缓存、持久化缓存、恢复缓存
 * 
 * @author 51
 * @version $Id: MapCacheManager.java, v 0.1 2017年11月8日 上午9:16:42 51 Exp $
 */
public class MapCacheManager implements CacheManager {
    private static final Logger      logger   = LoggerFactory.getLogger(MapCacheManager.class);

    private final MapCache           mapCache;

    private final Serializer<Object> serializer;

    private boolean                  runnable = true;

    public MapCacheManager(MapCache mapCache) {
        this.mapCache = mapCache;
        this.serializer = new HessianSerializer<Object>();
    }

    @Override
    public void run() {
        while (runnable) {
            // 清理过期缓存
            cleanUpExpired();
            try {
                Thread.sleep(mapCache.getConfig().getPersistTimeInterval() * 1000);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
            // 持久化缓存
            persist();
        }

    }

    @Override
    public void cleanUpExpired() {
        LogUtil.info(logger, "开始清理过期缓存");
        int cnt = 0;
        Iterator<Entry<String, Value>> iterator = mapCache.getCache().entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, Value> entry = iterator.next();
            Value value = entry.getValue();
            if (value.isExpire()) {
                iterator.remove();
                ++cnt;
            }
        }
        LogUtil.info(logger, "过期缓存清理完毕,清理数量:{0}", cnt);

    }

    @Override
    public void persist() {
        if (!mapCache.getConfig().isPersistent()) {
            return;
        }
        LogUtil.info(logger, "开始持久化缓存");
        File file = getCacheFile();
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

    @SuppressWarnings("unchecked")
    @Override
    public void restore() {
        LogUtil.info(logger, "开始从磁盘读取缓存");
        String filePath = mapCache.getConfig().getPersistFileFolderPath() + File.separator + mapCache.getConfig().getPersistFilename();
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
                    ConcurrentHashMap<String, Value> map = (ConcurrentHashMap<String, Value>) obj;
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

    public boolean isRunnable() {
        return runnable;
    }

    public void setRunnable(boolean runnable) {
        this.runnable = runnable;
    }

    private File getCacheFile() {
        CacheConfig config = mapCache.getConfig();
        String folderPath = config.getPersistFileFolderPath();
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = folderPath + File.separator + config.getPersistFilename();
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

}
