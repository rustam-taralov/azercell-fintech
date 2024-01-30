package az.azercellfintech.gateway.util;

import az.azercellfintech.gateway.exception.JsonParserException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;

import static az.azercellfintech.gateway.factory.ObjectMapperFactory.MAPPER_FACTORY;


@UtilityClass
public class MapperUtil {

    public static <T> String mapToString(T source) {
        try {
            return MAPPER_FACTORY.getObjectMapper().writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new JsonParserException(e.getMessage());
        }
    }

    public static <T> T mapToObject(String source, Class<T> targetClass) {
        try {
            return MAPPER_FACTORY.getObjectMapper().readValue(source,targetClass);
        } catch (JsonProcessingException e) {
            throw new JsonParserException(e.getMessage());
        }
    }

    public static <T> T mapToObject(InputStream source, Class<T> targetClass) {
        try {
            return MAPPER_FACTORY.getObjectMapper().readValue(source,targetClass);
        } catch (IOException e) {
            throw new JsonParserException(e.getMessage());
        }
    }
}
