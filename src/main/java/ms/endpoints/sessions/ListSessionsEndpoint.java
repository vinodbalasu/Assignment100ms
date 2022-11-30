package ms.endpoints.sessions;

import ms.restassured.constants.HttpMethod;
import ms.restassured.constants.RestUrlMapper;
import ms.restassured.entity.*;
import ms.restassured.template.BaseApiEndpoint;

import java.util.List;

public class ListSessionsEndpoint extends BaseApiEndpoint {


    public ListSessionsEndpoint(ApiRequest apiRequest) {
        super(apiRequest);
    }

    @Override
    public String url() {
        return RestUrlMapper.LIST_SESSIONS.getUrl();
    }

    @Override
    public HttpMethod httpMethod() {
        return null;
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
