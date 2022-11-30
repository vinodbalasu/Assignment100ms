package ms.endpoints.sessions;

import ms.restassured.constants.HttpMethod;
import ms.restassured.constants.RestUrlMapper;
import ms.restassured.entity.*;
import ms.restassured.template.BaseApiEndpoint;

import java.util.List;

public class RetrieveSessionsEndpoint extends BaseApiEndpoint {
    private String sessionId;

    public RetrieveSessionsEndpoint(ApiRequest apiRequest, String sessionId) {
        super(apiRequest);
        this.sessionId = sessionId;
    }

    @Override
    public String url() {
        return String.format(RestUrlMapper.RETRIEVE_SESSION.getUrl(), sessionId);
    }

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public Authorisation auth() {
        return null;
    }

    @Override
    public SSLConfigData sslConfig() {
        return null;
    }

    @Override
    public List<MultiPart> multiPart() {
        return null;
    }

    @Override
    public List<Param> formParam() {
        return null;
    }
}
