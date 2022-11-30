package ms.restassured.constants;

import lombok.Getter;

public enum HttpStatuses {
    OK(200, "ok"),
    CREATED(201, "created"),
    ACCEPTED(202, "accepted"),
    NO_CONTENT(204, "no_content"),
    BAD_REQUEST(400, "bad_request"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden"),
    NOT_FOUND(404, "not_found"),
    METHOD_NOT_ALLOWED(405, "method_not_allowed"),
    CONFLICT(409,"conflict"),
    UNPROCESSABLE_ENTITY(422, "unprocessable_entity"),
    FAILED_DEPENDENCY(424, "failed_dependency"),
    TOO_MANY_REQUESTS(429, "too_many_requests"),
    INTERNAL_SERVER_ERROR(500, "internal_server_error"),
    BAD_GATEWAY(502, "bad_gateway"),
    SERVICE_UNAVAILABLE(503, "service_unavailable"),
    GATEWAY_TIMEOUT(504,"gateway_timeout");



    @Getter
    private int statusCode;
    @Getter
    private String description;
    HttpStatuses(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public static HttpStatuses fetchFromStatusCode(int statusCode) {
        for (HttpStatuses httpStatuses : HttpStatuses.values()) {
            if (httpStatuses.getStatusCode() == statusCode)
                return httpStatuses;
        }
        return null;
    }
}
