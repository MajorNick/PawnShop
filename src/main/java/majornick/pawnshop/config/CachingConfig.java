package majornick.pawnshop.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@EnableCaching
public class CachingConfig {

    @Bean
    @Primary
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("directory"),
                new ConcurrentMapCache("branches")));
        return cacheManager;
    }

    @Bean
    public CacheManager newCacheManager() {
        return new ConcurrentMapCacheManager("branches");
    }
}