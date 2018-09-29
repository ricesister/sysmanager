package cps.fs.APImanagerSys.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 缓存
 * @author fs
 * @date 2018年9月20日
 * @description
 */
public class MapCache {
	/**
	 * 默认缓存
	 */
	private static final int DEFAULT_CACHES = 1024;
	
	private static final MapCache INS = new MapCache();
	public static MapCache single() {
		return INS;
	}
	
	
	/**
	 * 缓存容器
	 */
	private Map<String, CacheObject> cachePool;
	
	/**
	 * 构造
	 * 
	 */
	public MapCache() {
        this(DEFAULT_CACHES);
    }
	
    public MapCache(int cacheCount) {
        cachePool = new ConcurrentHashMap<>(cacheCount);
    }
    
    /**
     * 读取一个缓存
     * @param key
     * @return
     */
    public <T> T get(String key) {
    	CacheObject cacheObject = cachePool.get(key);
    	if(null != cacheObject) {
    		long cur = System.currentTimeMillis() / 1000;
    		if(cacheObject.getExpired() <= 0 || cacheObject.getExpired() > cur) {
    			Object result = cacheObject.getValue();
    			return (T) result;
    		}
    	}
    	return null;
    }
    
    /**
     * 读取一个hash类型缓存
     * @param key
     * @param field
     * @return
     */
    public <T> T hashGet(String key, String field) {
    	key = key + ":" + field;
    	return this.get(key);
    }
    
    /**
     * 设置一个缓存
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
    	this.set(key, value,-1);
    	
    }
    
    /**
     * 设置一个缓存并带上过期时间
     * @param key
     * @param value
     * @param expired
     */
    public void set(String key, Object value, long expired) {
    	expired = expired > 0? System.currentTimeMillis() / 1000 + expired : expired;
    	CacheObject cacheObject = new CacheObject(key, value, expired);
    	cachePool.put(key, cacheObject);
    }
    /**
     * 设置一个hash缓存
     *
     * @param key   缓存key
     * @param field 缓存field
     * @param value 缓存value
     */
    public void hset(String key, String field, Object value) {
        this.hset(key, field, value, -1);
    }
    
    /**
     * 设置一个hash缓存并带过期时间
     *
     * @param key     缓存key
     * @param field   缓存field
     * @param value   缓存value
     * @param expired 过期时间，单位为秒
     */
    public void hset(String key, String field, Object value, long expired) {
        key = key + ":" + field;
        expired = expired > 0 ? System.currentTimeMillis() / 1000 + expired : expired;
        CacheObject cacheObject = new CacheObject(key, value, expired);
        cachePool.put(key, cacheObject);
    }
    
    /**
     * 根据key删除缓存
     *
     * @param key 缓存key
     */
    public void del(String key) {
        cachePool.remove(key);
    }

    /**
     * 清空缓存
     */
    public void clean() {
        cachePool.clear();
    }
    
    
    /**
     * 根据key和field删除缓存
     *
     * @param key   缓存key
     * @param field 缓存field
     */
    public void hdel(String key, String field) {
        key = key + ":" + field;
        this.del(key);
    }
    
	static class CacheObject {
		private String key;
		private Object value;
		private long expired;
		
		public CacheObject(String key, Object value, long expired) {
            this.key = key;
            this.value = value;
            this.expired = expired;
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public long getExpired() {
            return expired;
        }
	}
	
	
}
