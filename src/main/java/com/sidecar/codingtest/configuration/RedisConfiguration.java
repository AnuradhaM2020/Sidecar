/*
 * package com.sidecar.codingtest.configuration;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
 * import org.springframework.data.redis.core.RedisTemplate; import
 * org.springframework.data.redis.serializer.GenericToStringSerializer;
 * 
 * @Configuration public class RedisConfiguration {
 * 
 * @Bean JedisConnectionFactory jedisConnectionFactory() { return new
 * JedisConnectionFactory(); }
 * 
 * @Bean RedisTemplate<String, Object> redisTemplate(){ RedisTemplate<String,
 * Object> redisTemplate = new RedisTemplate<String, Object>();
 * redisTemplate.setConnectionFactory(jedisConnectionFactory());
 * redisTemplate.setValueSerializer(new
 * GenericToStringSerializer<Object>(Object.class)); return redisTemplate; }
 * 
 * }
 */