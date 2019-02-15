package com.jkfq.authserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * ${DESCRIPTION}
 *
 * @Author
 * @create 2019-01-31
 **/
public class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public JsonUtils() {

    }

    public static <T> T serializable(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        } else {
            try {
                return mapper.readValue(json, clazz);
            } catch (IOException var3) {
                return null;
            }
        }
    }

    public static String deserializer(Object json) {
        if (json == null) {
            return null;
        } else {
            try {
                return mapper.writeValueAsString(json);
            } catch (JsonProcessingException var2) {
                return null;
            }
        }
    }

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
