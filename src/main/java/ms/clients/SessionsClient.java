package ms.clients;

import ms.endpoints.sessions.ListSessionsEndpoint;
import ms.endpoints.sessions.RetrieveSessionsEndpoint;
import ms.models.session.ListSessionsResponseDTO;
import ms.models.session.SessionResponseDTO;
import ms.restassured.entity.ApiRequest;
import ms.restassured.entity.IAPIResponse;
import ms.restassured.entity.RestAssuredResponse;

public class SessionsClient {
    //get all sessions
    public IAPIResponse getAllSessions(ApiRequest apiRequest) {
        return new ListSessionsEndpoint(apiRequest).call();
    }

    //retrieve a session by id
    public IAPIResponse getSession(ApiRequest apiRequest, String sessionId) {
        return new RetrieveSessionsEndpoint(apiRequest, sessionId).call();
    }
}
