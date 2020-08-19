package br.com.central.errors.infrastructure.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("event"),
                new ConcurrentMapCache("user"))
        );

        return cacheManager;
    }

    @Scheduled(fixedRate = 60000)
    private void process() {
        CacheManager manager = cacheManager();
        manager.getCacheNames().forEach(name -> manager.getCache(name).clear());
    }
}
