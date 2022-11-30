package ms.helpers;

import ms.models.ExpectedResponse;
import ms.restassured.constants.HttpStatuses;
import ms.restassured.entity.IAPIResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class StreamingHelper {
    private Logger logger = LogManager.getLogger(StreamingHelper.class);

    public void validateStreamingResponse(IAPIResponse streamingResponse, ExpectedResponse expectedResponse) {
        logger.debug("validating streaming response...");
        //validate start streaming
        Assert.assertEquals(streamingResponse.getStatusCode().getStatusCode(), expectedResponse.getStatusCode(),
                "Status Code mismatch error");
        Assert.assertEquals(streamingResponse.getResponseText(), expectedResponse.getResponseMessage());
    }

    public void validateDuplicateStreamingResponse(IAPIResponse streamingResponse) {
        logger.debug("validating duplicate streaming response...");
        Assert.assertEquals(streamingResponse.getStatusCode(), HttpStatuses.INTERNAL_SERVER_ERROR,
                "Status Code mismatch error");
        Assert.assertEquals(streamingResponse.getResponseText(), "rpc error: code = AlreadyExists desc = " +
                "beam already started");
    }

    public void validateStreamingResponseForInvalidToken(IAPIResponse streamingResponse) {
        logger.debug("validating invalid token streaming response...");
        Assert.assertEquals(streamingResponse.getStatusCode().getStatusCode(),
                HttpStatuses.UNAUTHORIZED.getStatusCode(), "Status Code mismatch error");
        Assert.assertEquals(streamingResponse.getResponseText(), "token contains an invalid number of segments\n");
    }
}
