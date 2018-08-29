package com.vwfsag.fso.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;

@Service("hazelcastService")
public class HazelcastCacheServiceImpl implements CacheService {

	@Autowired
	private HazelcastInstance hazelcastInstance;

	@Override
	public <T> T get(Class<T> cacheType, String key) {
		Map<String, T> cache = hazelcastInstance.getMap(cacheType.getName());
		return cache.get(key);
	}

	@Override
	public <T> Map<String, T> get(Class<T> cacheType, List<String> keys) {
		Map<String, T> cache = hazelcastInstance.getMap(cacheType.getName());
		Map<String, T> results = new LinkedHashMap<String, T>();
		for (String key : keys) {
			results.put(key, cache.get(key));
		}
		return results;
	}

	@Override
	public <T> void put(Class<T> cacheType, String key, T value) {
		Map<String, T> cache = hazelcastInstance.getMap(cacheType.getName());
		cache.put(key, value);
	}

	@Override
	public <T> void put(Class<T> cacheType, Map<String, T> elements) {
		Map<String, T> cache = hazelcastInstance.getMap(cacheType.getName());
		cache.putAll(elements);
	}

	@Override
	public <T> List<T> getList(Class<T> cacheType, String listKey) {
		Map<String, List<T>> cache = hazelcastInstance.getMap(cacheType.getName());
		List<T> results = cache.get(listKey);
		return results;
	}

	@Override
	public <T> void putList(Class<T> cacheType, String listKey, List<T> value) {
		Map<String, List<T>> cache = hazelcastInstance.getMap(cacheType.getName());
		cache.put(listKey, value);
	}

	@Override
	public <T> void clear(Class<T> cacheType) {
		Map<String, List<T>> cache = hazelcastInstance.getMap(cacheType.getName());
		cache.clear();
	}

}
