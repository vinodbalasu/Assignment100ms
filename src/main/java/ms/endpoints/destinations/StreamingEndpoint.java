package ms.endpoints.destinations;

import ms.restassured.constants.HttpMethod;
import ms.restassured.constants.RestUrlMapper;
import ms.restassured.entity.*;
import ms.restassured.template.BaseApiEndpoint;

import java.util.List;

public class StreamingEndpoint extends BaseApiEndpoint {

    public StreamingEndpoint(ApiRequest request) {
        super(request);
    }

    @Override
    public String url() {
        return RestUrlMapper.STREAMING_RECORDING.getUrl();
    }

    @Override
    public HttpMethod httpMethod() {
        return HttpMethod.POST;
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
