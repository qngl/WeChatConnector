package com.vwfsag.fso.service;

import java.util.List;
import java.util.Map;

/**
 * @author liqiang
 *
 */
public interface CacheService {

	<T> T get(Class<T> cacheType, String key);

	<T> Map<String, T> get(Class<T> cacheType, List<String> keys);

	<T> void put(Class<T> cacheType, String key, T value);

	<T> void put(Class<T> cacheType, Map<String, T> elements);

	<T> List<T> getList(Class<T> cacheType, String listKey);

	<T> void putList(Class<T> cacheType, String listKey, List<T> value);

	<T> void clear(Class<T> cacheType);

}
