package az.azercellfintech.customer.ms.configuration;

import az.azercellfintech.customer.ms.exception.ClientException;
import az.azercellfintech.customer.ms.util.MapperUtil;
import com.fasterxml.jackson.databind.JsonNode;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.experimental.FieldDefaults;

import static java.lang.String.valueOf;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        var errorMessage = convertMessage(methodKey, response);
        return new ClientException(valueOf(response.status()), errorMessage);
    }

    private String convertMessage(String methodKey, Response response) {
        var defaultErrorMessage = "Error occurred while send request to " + methodKey.split("#")[0];
        JsonNode messageResponse;
        try {
            messageResponse = MapperUtil.mapToObject(response.body().asInputStream(), JsonNode.class);
            return messageResponse.has("message") ? messageResponse.get("message").asText() : defaultErrorMessage;
        } catch (Exception ex) {
            return defaultErrorMessage;
        }
    }
}

