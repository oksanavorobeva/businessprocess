package com.example.ReportsService.config;


import com.example.ReportsService.persistence.model.ReportKey;
import com.example.ReportsService.usecasses.dto.ReportCacheDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;


@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    @Bean
    public RedisTemplate<ReportKey, ReportCacheDto> redisTemplate(
            RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
        RedisTemplate<ReportKey, ReportCacheDto> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        template.setKeySerializer(new Jackson2JsonRedisSerializer<>(objectMapper, ReportKey.class));

        Jackson2JsonRedisSerializer<ReportCacheDto> jsonRedisSerializer
                = new Jackson2JsonRedisSerializer<>(objectMapper, ReportCacheDto.class);
        template.setValueSerializer(jsonRedisSerializer);
        template.setHashValueSerializer(jsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }
}

