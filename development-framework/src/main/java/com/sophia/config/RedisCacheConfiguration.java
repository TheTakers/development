package com.sophia.config;
//package com.mishop.common.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.lang3.reflect.FieldUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//
//import java.lang.reflect.Method;
//
///**
// * Created by Kim on 2015/9/21.
// */
//@Configuration
//@EnableCaching
//public class RedisCacheConfiguration extends CachingConfigurerSupport {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Bean
//    public KeyGenerator keyGenerator() {
//        return new KeyGenerator() {
//            @Override
//            public Object generate(Object target, Method method, Object... objects) {
//                StringBuilder sb = new StringBuilder();
//                for (Object object : objects) {
//                    sb.append(method.getReturnType().getName()).append("#");
//                    if (object instanceof Long) {
//                        sb.append(object);
//                    } else {
//                        try {
//                            sb.append(FieldUtils.readField(object.getClass().getSuperclass().getDeclaredField("id"), object, true));
//                        } catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }
//                return sb.toString();
//            }
//        };
//    }
//
//    @Bean
//    public CacheManager cacheManager() {
//        return new RedisCacheManager(redisTemplate());
//    }
//
//    public RedisTemplate<String, String> redisTemplate() {
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        stringRedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        stringRedisTemplate.afterPropertiesSet();
//        return stringRedisTemplate;
//    }
//
//}
