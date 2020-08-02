package com.hejun.eduService.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration	
public class RedisConfig {
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		//创建RedisTemplate对象（Spring-Data-Redis提供的，高度封装的redis数据库操作工具类）
		RedisTemplate<String, Object> redistemplate = new RedisTemplate<>();
		//配置连接工厂
		redistemplate.setConnectionFactory(redisConnectionFactory);
		
		//创建Json序列化配置（Jackson序列化配置对象）
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
		
		//创建ObjectMapper对象
		ObjectMapper om = new ObjectMapper();
		//配置ObjectMapper属性
		// 解决jackson2无法反序列化LocalDateTime的问题
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
		
		//将ObjectMapper注入到Jackson序列化配置的属性中
		jackson2JsonRedisSerializer.setObjectMapper(om);
		
		//String的序列化配置
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		
		//key采用String的序列化配置
		redistemplate.setKeySerializer(stringRedisSerializer);
		
		//Hash的key也采用String的序列化配置
		redistemplate.setHashKeySerializer(stringRedisSerializer);
		
		//value的序列化采用Jackson序列化配置
		redistemplate.setValueSerializer(jackson2JsonRedisSerializer);
		
		//Hash的value序列化采用Jackson序列化配置
		redistemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		
		return redistemplate;
	}
	
	   @Bean
	    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
	        StringRedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory);
	        redisTemplate.setEnableTransactionSupport(false);
	        return redisTemplate;
	    }
	   
	   /**
	    * 用redis作为spring缓存
	    * @param redisConnectionFactory
	    * @return
	    */
	   @Bean
	    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

	        RedisCacheManager redisCacheManager = new RedisCacheManager(
	                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
	                // 默认策略，未配置的 key 会使用这个
	                this.getRedisCacheConfigurationWithTtl(3600),
	                // 指定 key 策略
	                this.getRedisCacheConfigurationMap()
	        );
	        redisCacheManager.setTransactionAware(true);
	        return redisCacheManager;
	    }
	   /**
	    * 指定key的缓存设置特定的过期时间
	    * @return
	    */
	    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
	        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>(16);
//	        redisCacheConfigurationMap.put("product", this.getRedisCacheConfigurationWithTtl(1800));
	        return redisCacheConfigurationMap;
	    }

	    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {

	        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();

	        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =  new Jackson2JsonRedisSerializer<>(Object.class);
	        ObjectMapper om = new ObjectMapper();
	        // 解决jackson2无法反序列化LocalDateTime的问题
	        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	        om.registerModule(new JavaTimeModule());
	        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
	        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
	        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
	        jackson2JsonRedisSerializer.setObjectMapper(om);

	        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
	        redisCacheConfiguration = redisCacheConfiguration
	                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
	                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
	                .entryTtl(Duration.ofSeconds(seconds));

	        return redisCacheConfiguration;
	    }

}
