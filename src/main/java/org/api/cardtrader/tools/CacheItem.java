package org.api.cardtrader.tools;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheItem<T> {
		protected Logger logger = LogManager.getLogger(this.getClass());
		private Cache<String, T> guava;
		
		
		public CacheItem() {
			guava = CacheBuilder.newBuilder().build();
		}
		
		public void clear() {
			guava.invalidateAll();
			
		}

		public T getItem(String k) {
			return guava.getIfPresent(k);
		}

		public void put(T value, String key) throws IOException {
			guava.put(key, value);
			
		}
		
		public T getCached(String k, Callable<T> call)
		{
			if(getItem(k)==null)
				try {
					put(call.call(),k);
				} catch (Exception e) {
					logger.error(e);
					return null;
				}
			
			return getItem(k);
		}
		
	
}
