package ms.restassured.entity;

import ms.restassured.constants.HttpStatuses;

import java.util.Map;

public interface IAPIResponse extends IJsonSchemaValidator{
    String getResponseText();

    HttpStatuses getStatusCode();

    String getHeaderValue(String key);

    Map<String, String> getAllHeaders();
}
