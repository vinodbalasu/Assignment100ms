package ms.controllers;

import ms.clients.SessionsClient;
import ms.clients.StreamingClient;
import ms.models.streaming.StreamingDTO;
import ms.models.streaming.StreamingTestdata;
import ms.restassured.entity.ApiRequest;
import ms.restassured.entity.IAPIResponse;
import ms.restassured.entity.Param;
import ms.utils.TokenGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MSController {
    private static Logger logger = LogManager.getLogger(MSController.class);
    private SessionsClient sessionsClient = new SessionsClient();
    private StreamingClient streamingClient = new StreamingClient();

    //build the request and make an api call
    public IAPIResponse makeStreamingCall(StreamingDTO streamingDTO, boolean isInvalidToken) {
        logger.debug("build ApiRequest for Streaming call...");
        final List<Param> params = getParamsWithAuthorizationBearerToken(isInvalidToken);
        final ApiRequest apiRequest = ApiRequest.builder().body(streamingDTO).headers(params).build();
        return streamingClient.streamingService(apiRequest);
    }

    public IAPIResponse retrieveSessionById(String sessionId, boolean isInvalidToken) {
        logger.debug("build ApiReques for getSessions call...");
        final List<Param> params = getParamsWithAuthorizationBearerToken(isInvalidToken);
        final ApiRequest apiRequest = ApiRequest.builder().headers(params).build();
        return sessionsClient.getSession(apiRequest, sessionId);
    }

    private List<Param> getParamsWithAuthorizationBearerToken(boolean isInvalidToken) {
        final List<Param> params = new ArrayList<>();
        params.add(Param.builder().key("Content-Type").value("application/json").build());
        String token = TokenGenerator.getInstance().getManagementToken();
        if (isInvalidToken) {
            token = "invalid";
        }
        params.add(Param.builder().key("Authorization").value("Bearer " +token).build());
        return params;
    }
}
