package org.lnson.artemis.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.TimeZone;

public class JsonCommon {

    private final static Logger logger = LoggerFactory.getLogger(JsonCommon.class);

    private static ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return mapper;
    }

    public static String serializeObject(Object value) {
        try {
            return mapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            logger.error(GenerateCommon.printException(e));
            return null;
        }
    }

    public static String serializeProperties(Properties properties) {
        ObjectMapper mapper = mapper();

        // 新建propertiesType,并添加至typeFactory的_typeCache缓存中
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MapType propertiesType = MapType.construct(Properties.class, SimpleType.constructUnsafe(String.class), SimpleType.constructUnsafe(Object.class));
        LRUMap<Object, JavaType> cache = new LRUMap<Object, JavaType>(16, 200);
        cache.put(Properties.class, propertiesType);

        // 指定typeFactory
        TypeFactory typeFactory = TypeFactory.defaultInstance().withCache(cache);
        mapper.setTypeFactory(typeFactory);

        ObjectWriter writer = mapper.writer();
        try {
            return writer.writeValueAsString(properties);
        } catch (JsonProcessingException e) {
            logger.error(GenerateCommon.printException(e));
            return null;
        }
    }

    public static <T extends Object> T deserializeObject(String value, Class<T> clazz) {
        try {
            return mapper().readValue(value, clazz);
        } catch (IOException e) {
            logger.error(GenerateCommon.printException(e));
            return null;
        }
    }

}
