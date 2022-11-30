package ms.restassured.constants;

import lombok.Getter;
import ms.utils.PropertiesLoader;

public enum RestUrlMapper {
    STREAMING_BASE_URL(PropertiesLoader.getStreamingBaseUrl()),
    SESSIONS_BASE_URL(PropertiesLoader.getSessionsBaseUrl()),
    STREAMING_RECORDING(STREAMING_BASE_URL.getUrl() + "/api/v2/beam"),
    LIST_SESSIONS(SESSIONS_BASE_URL.getUrl() + "/v2/sessions"),
    RETRIEVE_SESSION(SESSIONS_BASE_URL.getUrl() + "/v2/sessions/%s");


    @Getter
    private String url;
    RestUrlMapper(String url) {
        this.url = url;
    }
}
