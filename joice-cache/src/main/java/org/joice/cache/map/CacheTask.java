/**
 * 深圳金融电子结算中心
 * Copyright (c) 1995-2017 All Rights Reserved.
 */
package org.joice.cache.map;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.joice.cache.serializer.HessianSerializer;
import org.joice.cache.serializer.Serializer;
import org.joice.cache.to.CacheWrapper;
import org.joice.cache.util.OsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author HuHui
 * @version $Id: CacheTask.java, v 0.1 2017年9月28日 下午4:29:01 HuHui Exp $
 */
public class CacheTask implements Runnable, CacheChangeListener {

    private static final Logger logger       = LoggerFactory.getLogger(CacheTask.class);

    /** 缓存被修改的个数 */
    private AtomicInteger       cacheChanged = new AtomicInteger(0);

    private MapCacheManager     cacheManager;

    private volatile boolean    running      = false;

    private File                saveFile;

    private Serializer<Object>  persistSerializer;

    public CacheTask(MapCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void start() {
        if (!running) {
            loadCache();
            running = true;
        }
    }

    public void destroy() {
        persistCache(true);
        running = false;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        running = false;
    }

    /**
     * 从磁盘加载之前保存的缓存数据,避免系统刚启动时因为没有缓存,而造成数据库压力过大
     */
    @SuppressWarnings("unchecked")
    public void loadCache() {
        if (!cacheManager.isNeedPersist()) {
            return;
        }
        File file = getSaveFile();
        if (file == null || !file.exists()) {
            return;
        }
        BufferedInputStream bis = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte buf[] = new byte[1024];
            int len = -1;
            while ((len = bis.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            byte retArr[] = baos.toByteArray();
            Object obj = getPersistSerializer().deserialize(retArr, null);
            if (obj != null && obj instanceof ConcurrentHashMap) {
                cacheManager.getCache().putAll((ConcurrentHashMap<String, Object>) obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }
    }

    private File getSaveFile() {
        if (saveFile != null) {
            return saveFile;
        }
        String path = getSavePath();
        File savePath = new File(path);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        saveFile = new File(path + "map.cache");
        return saveFile;
    }

    /**
     * 获取缓存数据保存路径
     */
    private String getSavePath() {
        String persistFile = cacheManager.getPersistFile();
        if (StringUtils.isNotEmpty(persistFile)) {
            return persistFile;
        }
        String path = "/tmp/joice-cache/";
        String nsp = cacheManager.getConfig().getNamespace();
        if (StringUtils.isNotEmpty(nsp)) {
            path += nsp.trim() + "/";
        }
        if (OsUtil.getInstance().isLinux()) {
            return path;
        }
        return "F:" + path;
    }

    private void persistCache(boolean force) {
        if (!cacheManager.isNeedPersist()) {
            return;
        }
        int cnt = cacheChanged.intValue();
        if (!force && cnt <= cacheManager.getUnPersistMaxSize()) {
            return;
        }
        cacheChanged.set(0);
        FileOutputStream fos = null;
        try {
            byte[] data = getPersistSerializer().serialize(cacheManager.getCache());
            File file = getSaveFile();
            fos = new FileOutputStream(file);
            fos.write(data);
        } catch (Exception e) {
            cacheChanged.addAndGet(cnt);
            logger.error("", e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }
    }

    private Serializer<Object> getPersistSerializer() {
        if (persistSerializer == null) {
            //只有HessianSerializer才支持SoftReference序列化
            persistSerializer = new HessianSerializer();
        }
        return persistSerializer;
    }

    @Override
    public void cacheChange() {
        cacheChanged.incrementAndGet();
    }

    @Override
    public void cacheChange(int cnt) {
        cacheChanged.addAndGet(cnt);
    }

    @Override
    public void run() {
        while (running) {
            try {
                cleanCache();
                persistCache(false);
            } catch (Exception e) {
                logger.error("", e);
            }
            try {
                Thread.sleep(cacheManager.getClearAndPersistPeriod());
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
    }

    /**
     * 清理过期缓存
     */
    private void cleanCache() {
        Iterator<Entry<String, Object>> iterator = cacheManager.getCache().entrySet().iterator();
        int _cacheChanged = 0;
        int i = 0;
        while (iterator.hasNext()) {
            _cacheChanged += removeExpiredItem(iterator);
            i++;
            if (i == 2000) {
                i = 0;
                try {
                    //触发操作系统立刻重新进行一次CPU竞争,让其它线程获得CPU控制权
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    logger.error("", e);
                }
            }
        }
        if (_cacheChanged > 0) {
            cacheChange(_cacheChanged);
        }
    }

    @SuppressWarnings("unchecked")
    private int removeExpiredItem(Iterator<Entry<String, Object>> iterator) {
        int _cacheChanged = 0;
        Object value = iterator.next().getValue();
        if (value instanceof SoftReference) {
            SoftReference<CacheWrapper<Object>> reference = (SoftReference<CacheWrapper<Object>>) value;
            if (reference != null && reference.get() != null) {
                CacheWrapper<Object> tmp = reference.get();
                if (tmp.isExpired()) {
                    iterator.remove();
                    _cacheChanged++;
                }
            } else {
                iterator.remove();
                _cacheChanged++;
            }
        } else if (value instanceof ConcurrentHashMap) {
            ConcurrentHashMap<String, Object> hash = (ConcurrentHashMap<String, Object>) value;
            Iterator<Entry<String, Object>> iterator2 = hash.entrySet().iterator();
            while (iterator2.hasNext()) {
                Object tmpObj = iterator2.next().getValue();
                if (tmpObj instanceof SoftReference) {
                    SoftReference<CacheWrapper<Object>> reference = (SoftReference<CacheWrapper<Object>>) tmpObj;
                    if (reference != null && reference.get() != null) {
                        CacheWrapper<Object> tmp = reference.get();
                        if (tmp.isExpired()) {
                            iterator2.remove();
                            _cacheChanged++;
                        }
                    } else {
                        iterator2.remove();
                        _cacheChanged++;
                    }
                }
            }
            if (hash.isEmpty()) {
                iterator.remove();
            }
        } else {
            CacheWrapper<Object> tmp = (CacheWrapper<Object>) value;
            if (tmp.isExpired()) {
                iterator.remove();
                _cacheChanged++;
            }
        }
        return _cacheChanged;
    }
}
