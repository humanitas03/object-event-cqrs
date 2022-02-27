package com.example.objecteventcqrs.configuration;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    static class CustomDurationSerializer extends JsonSerializer<Duration> {

        @Override
        public void serialize(Duration value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
                gen.writeNumber(value.toMinutes());
        }
    }

    static class CustomDurationDeSerializer extends JsonDeserializer<Duration> {

        @Override
        public Duration deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JacksonException {
            return Duration.ofMinutes(p.getLongValue());
        }
    }

    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        var javaTimeModule = new JavaTimeModule();
        javaTimeModule
            .addSerializer(
                LocalDate.class,
                new LocalDateSerializer(
                    DateTimeFormatter.ISO_DATE
                )
            )

            .addSerializer(
                LocalDateTime.class,
                new LocalDateTimeSerializer(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                )
            )
            .addSerializer(
                Duration.class,
                new CustomDurationSerializer()
            )
            .addDeserializer(
                Duration.class,
                new CustomDurationDeSerializer()
            )
            .addDeserializer(
                LocalDateTime.class,
                new LocalDateTimeDeserializer(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                )
            )
            .addSerializer(
                LocalTime.class,
                new LocalTimeSerializer(
                    DateTimeFormatter.ISO_LOCAL_TIME
                )
            )
            .addDeserializer(
                LocalTime.class,
                new LocalTimeDeserializer(
                    DateTimeFormatter.ISO_LOCAL_TIME
                )
            )
        ;
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
}
