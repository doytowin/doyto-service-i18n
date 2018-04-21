//package win.doyto.i18n.configurer;
//
//import java.util.HashMap;
//import java.util.Map;
//import javax.annotation.Resource;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.cache.CacheManager;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisSentinelConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * RedisConfigurer
// *
// * @author f0rb on 2017-04-19.
// */
//@RefreshScope
//@Configuration
//@AutoConfigureAfter(RedisAutoConfiguration.class)
//public class RedisConfigurer {
//    @Value("${redis.default.expiration:300}")
//    private Long redisDefaultExpiration;
//
//    @Resource
//    private RedisProperties redisProperties;
//
//    @Bean
//    public LettuceConnectionFactory connectionFactory() {
//        LettuceConnectionFactory lettuceConnectionFactory;
//        if (redisProperties.getSentinel() != null) {
//            RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
//            RedisSentinelConfiguration sentinelServersConfig = new RedisSentinelConfiguration().master(sentinel.getMaster());
//            for (String hostPort : sentinel.getNodes().split(",")) {
//                int colonIndex = hostPort.indexOf(':');
//                String host = hostPort.substring(0, colonIndex);
//                int port = Integer.parseInt(hostPort.substring(colonIndex + 1));
//                sentinelServersConfig.sentinel(host, port);
//            }
//            lettuceConnectionFactory = new LettuceConnectionFactory(sentinelServersConfig);
//        } else { //single server
//            lettuceConnectionFactory = new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
//        }
//        lettuceConnectionFactory.setDatabase(redisProperties.getDatabase());
//        if (redisProperties.getPassword() != null) {
//            lettuceConnectionFactory.setPassword(redisProperties.getPassword());
//        }
//        return lettuceConnectionFactory;
//    }
//
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setConnectionFactory(connectionFactory);
//        return template;
//    }
//
//    @Bean
//    public CacheManager cacheManager(@Qualifier("redisTemplate") RedisTemplate<Object, Object> redisOperations) {
//        RedisCacheManager redisCacheManager = new RedisCacheManager(redisOperations);
//        redisCacheManager.setUsePrefix(true);
//        redisCacheManager.setDefaultExpiration(redisDefaultExpiration);
//
//        Map<String, Long> expiresMap = new HashMap<>();
//        redisCacheManager.setExpires(expiresMap);
//
//        return redisCacheManager;
//    }
//
//}
