package ms.restassured.template;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ms.restassured.entity.ApiRequest;
import ms.restassured.entity.IAPIResponse;
import ms.restassured.entity.Param;
import ms.restassured.utility.RequestHandler;

import java.util.List;

public abstract class BaseApiEndpoint implements IServiceEndpoint {
    private ObjectMapper objectMapper = new ObjectMapper();
    ApiRequest apiRequest;

    public BaseApiEndpoint(ApiRequest apiRequest) {
        this.apiRequest = apiRequest;
    }


    public IAPIResponse call() {
        return  new RequestHandler().processAPIRequest(this);
    }

    public List<Param> queryParameters() {
        return this.apiRequest.getQueryParameters();
    }

    public List<Param> pathParameters() {
        return this.apiRequest.getPathParameters();
    }

    public List<Param> headers() {
        return this.apiRequest.getHeaders();
    }

    public String requestBody() {
        try {
            return objectMapper.writeValueAsString(this.apiRequest.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
