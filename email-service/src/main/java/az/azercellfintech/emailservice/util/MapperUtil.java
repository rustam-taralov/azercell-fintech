package az.azercellfintech.emailservice.util;

import az.azercellfintech.emailservice.exception.JsonParserException;
import az.azercellfintech.emailservice.factory.ObjectMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.experimental.UtilityClass;


@UtilityClass
public class MapperUtil {

    public static <T> String mapToString(T source) {
        try {
            return ObjectMapperFactory.MAPPER_FACTORY.getObjectMapper().writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new JsonParserException(e.getMessage());
        }
    }

    public static <T> T mapToObject(String source, Class<T> targetClass) {
        try {
            return ObjectMapperFactory.MAPPER_FACTORY.getObjectMapper().readValue(source,targetClass);
        } catch (JsonProcessingException e) {
            throw new JsonParserException(e.getMessage());
        }
    }
}
