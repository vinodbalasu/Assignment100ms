package ms.tests;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ms.constants.TestGroups;
import ms.helpers.SessionHelper;
import ms.models.session.SessionResponseDTO;
import ms.restassured.entity.IAPIResponse;
import ms.utils.PropertiesLoader;
import org.testng.annotations.Test;

public class SessionsTest extends TestBase {
    final ObjectMapper objectMapper = new ObjectMapper();

    @Test(groups = TestGroups.SESSIONS_TEST)
    public void validateRetrieveSessionById() throws JsonProcessingException {
        final SessionHelper sessionHelper = new SessionHelper();
        //get the sessionId from the properties
        final String sessionId = (String) PropertiesLoader.properties.get("session_id");
        final IAPIResponse sessionResponse = retrieveSessionById(sessionId, false);
        sessionHelper.validateSessionResponse(sessionResponse);
         final SessionResponseDTO sessionResponseDTO = objectMapper.readValue(sessionResponse.getResponseText(),
                    SessionResponseDTO.class);

        sessionHelper.validateSessionResponsePayload(sessionResponseDTO);
    }

    @Test(groups = TestGroups.SESSIONS_TEST)
    public void validateRetrieveSessionByIdForInvalidToken() throws JsonProcessingException {
        final SessionHelper sessionHelper = new SessionHelper();
        //get the sessionId from the properties
        final String sessionId = (String) PropertiesLoader.properties.get("session_id");
        final IAPIResponse sessionResponse = retrieveSessionById(sessionId, true);
        sessionHelper.validateInvalidTokenSessionsResponse(sessionResponse);
    }

    @Test(groups = TestGroups.SESSIONS_TEST)
    public void validateRetrieveSessionByIdForInvalidSessionId() throws JsonProcessingException {
        final SessionHelper sessionHelper = new SessionHelper();
        //get the sessionId from the properties
        final String sessionId = "invalid";
        final IAPIResponse sessionResponse = retrieveSessionById(sessionId, false);
        sessionHelper.validateInvalidSessionIdResponse(sessionResponse);
    }

}
