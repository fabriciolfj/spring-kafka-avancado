package com.github.fabriciolfj.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Objects;

public class CustomJsonDeserializer<T> implements Deserializer<T> {

    private ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> deserializeClass;

    public CustomJsonDeserializer(Class<T> deserializeClass) {
        Objects.requireNonNull(deserializeClass, "Deserialized class must not null");
        this.deserializeClass = deserializeClass;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, deserializeClass);
        } catch (IOException e) {
            throw new SerializationException(e.getMessage());
        }
    }
}
