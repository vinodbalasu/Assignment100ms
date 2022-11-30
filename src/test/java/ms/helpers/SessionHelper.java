package ms.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ms.models.session.GetSessionErrorResponse;
import ms.models.session.SessionResponseDTO;
import ms.restassured.constants.HttpStatuses;
import ms.restassured.entity.IAPIResponse;
import ms.utils.PropertiesLoader;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class SessionHelper {
    public static final String SESSIONS_SCHEMA_PATH = "schema/session_schema.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    public void validateSessionResponse(IAPIResponse sessionResponse) {
        Assert.assertEquals(sessionResponse.getStatusCode(), HttpStatuses.OK);
        //validate json schema
        sessionResponse.validateJsonSchema(SESSIONS_SCHEMA_PATH);

    }

    public void validateSessionResponsePayload(SessionResponseDTO sessionResponseDTO) {
        //validate roomId, SessionId
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(sessionResponseDTO.getId(), PropertiesLoader.properties.get("session_id"));
        softAssert.assertEquals(sessionResponseDTO.getRoomId(), PropertiesLoader.properties.get("room_id"));
        softAssert.assertAll();
    }

    public void validateInvalidTokenSessionsResponse(IAPIResponse sessionResponse) throws JsonProcessingException {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(sessionResponse.getStatusCode(), HttpStatuses.UNAUTHORIZED);
        final GetSessionErrorResponse getSessionErrorResponse =
                objectMapper.readValue(sessionResponse.getResponseText(), GetSessionErrorResponse.class);
        softAssert.assertEquals(getSessionErrorResponse.getMessage(),
                "token contains an invalid number of segments");
        softAssert.assertEquals(getSessionErrorResponse.getData().getDescription(),
                "rpc error: code = PermissionDenied desc = token contains an invalid number of segments");
        softAssert.assertAll();
    }

    public void validateInvalidSessionIdResponse(IAPIResponse response) throws JsonProcessingException {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), HttpStatuses.NOT_FOUND);
        final GetSessionErrorResponse getSessionErrorResponse =
                objectMapper.readValue(response.getResponseText(), GetSessionErrorResponse.class);
        softAssert.assertEquals(getSessionErrorResponse.getMessage(),
                "Error: Session not found");
        softAssert.assertEquals(getSessionErrorResponse.getData().getDescription(),
                "Error: Session not found");
        softAssert.assertAll();

    }
}
