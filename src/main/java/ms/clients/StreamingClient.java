package ms.clients;

import ms.endpoints.destinations.StreamingEndpoint;
import ms.restassured.entity.ApiRequest;
import ms.restassured.entity.IAPIResponse;

public class StreamingClient {
    public IAPIResponse streamingService(ApiRequest apiRequest) {
        return new StreamingEndpoint(apiRequest).call();
    }
}
