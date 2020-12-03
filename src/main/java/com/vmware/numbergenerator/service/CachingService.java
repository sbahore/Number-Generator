
package com.vmware.numbergenerator.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Shweta Bahore
 * @version 1.0.0
 * This service provides caching for the application
 **/
@Service
public class CachingService {
	private static final Cache<String, List<String>> numberSequenceCache = Caffeine.newBuilder()
	        .expireAfterWrite(180, TimeUnit.DAYS)
	        .build();

	private static final Cache<String, Integer> idsCache = Caffeine.newBuilder()
	        .expireAfterWrite(180, TimeUnit.DAYS)
	        .build();

	public static @Nullable Integer getIdFromCache(String key) {
		return idsCache.getIfPresent(key);
	}

	public static void putIdInCache(String key, Integer value) {
		idsCache.put(key, value);
	}

	public static void purgeIdFromCache(String key) {
		idsCache.invalidate(key);
	}

	public static void putDataInCache(String key, List<String> value) {
		numberSequenceCache.put(key, value);
	}

	public static Map<String, List<String>> getDataFromCache() {
		return numberSequenceCache.asMap();
	}


}
