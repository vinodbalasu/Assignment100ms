package ms.restassured.entity;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import ms.restassured.constants.HttpStatuses;
import org.hamcrest.Matcher;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestAssuredResponse implements IAPIResponse {

    private Response response;
    private HttpStatuses httpStatusCode;
    private String responseAsText;
    private Map<String, String> responseHeaders;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RestAssuredResponse(Response response) {
        this.response = response;
        this.httpStatusCode = HttpStatuses.fetchFromStatusCode(response.getStatusCode());
        this.responseAsText = response.getBody().asString().replaceAll("^\"|\"$\n", "");
        setResponseHeaders(response.getHeaders().asList());
    }

    private void setResponseHeaders(List<Header> headers) {
        responseHeaders = new HashMap<>();
        for (Header header : headers) {
            responseHeaders.put(header.getName(), header.getValue());
        }
    }

    @Override
    public String getResponseText() {
        return this.responseAsText;
    }

    @Override
    public HttpStatuses getStatusCode() {
        return this.httpStatusCode;
    }

    @Override
    public String getHeaderValue(String key) {
        return this.responseHeaders.get(key);
    }

    @Override
    public Map<String, String> getAllHeaders() {
        return this.responseHeaders;
    }

    @Override
    public void validateJsonSchema(String schemaFilePath) {
        final InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(schemaFilePath);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(inputStream), new Matcher[0]);
    }

    public static void main(String[] args) {
        String str = "\"Beam started for room\"\n";
        System.out.println(str);
        str = str.replaceAll("^\"|\"$\n", "");
        System.out.println(str);
    }
}
