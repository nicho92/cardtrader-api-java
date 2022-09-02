package org.api.cardtrader.tools;

import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheManager<T> {
		protected Logger logger = LogManager.getLogger(this.getClass());
		private Cache<String, T> guava;
		private boolean enable=true;
		
		
		public void setEnable(boolean enable)
		{
			this.enable=enable;
		}
		
		public CacheManager() {
			guava = CacheBuilder.newBuilder().build();
		}
		
		public void clear() {
			guava.invalidateAll();
			
		}
		
		public void clear(String k) {
			guava.invalidate(k);
			
		}
		

		public T getItem(String k) {
			return guava.getIfPresent(k);
		}

		public void put(T value, String key) {
			guava.put(key, value);
			
		}
		
		public T getCached(String k, Callable<T> call)
		{
			
			if(!enable)
				try {
					return call.call();
				} catch (Exception e1) {
					logger.error("error calling",e1);
					return null;
				}
			
			if(getItem(k)==null) 
			{
				try {
					put(call.call(),k);
				} catch (Exception e) {
					logger.error("error getting " + k,e);
					return null;
				}
			}
			
			return getItem(k);
		}

		
	
}
