package ms.tests;

import ms.constants.TestGroups;
import ms.dataproviders.StreamingDataProvider;
import ms.helpers.StreamingHelper;
import ms.models.streaming.StreamingTestdata;
import ms.restassured.entity.IAPIResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

public class RTMPStreamingTest extends TestBase {

    private static Logger logger = LogManager.getLogger(RTMPStreamingTest.class);


    @Test(groups = TestGroups.RTMP_STREAMING_TEST, dataProviderClass = StreamingDataProvider.class,
            dataProvider = "fetchValidCases")
    public void verifyStreamingApiValidCases(StreamingTestdata streamingTestdata) {
        logger.debug("Testcase name : " + streamingTestdata.getTestName());

        final StreamingHelper streamingHelper = new StreamingHelper();
        //start streaming
        logger.debug("start streaming");
        final IAPIResponse startStreamingResponse = makeStreamingCall(streamingTestdata.getStartStreaming().getBody(),
                false);
        streamingHelper.validateStreamingResponse(startStreamingResponse,
                streamingTestdata.getStartStreaming().getResponse());
        //stop streaming
        logger.debug("stop streaming");
        final IAPIResponse stopStreamingResponse = makeStreamingCall(streamingTestdata.getStopStreaming().getBody(),
                false);
        streamingHelper.validateStreamingResponse(stopStreamingResponse, streamingTestdata.getStopStreaming().getResponse());
    }

    @Test(groups = TestGroups.RTMP_STREAMING_TEST, dataProviderClass = StreamingDataProvider.class,
            dataProvider = "fetchInValidCases")
    public void verifyStreamingApiInvalidCases(StreamingTestdata streamingTestdata) {
        logger.debug("Testcase name : " + streamingTestdata.getTestName());
        final StreamingHelper streamingHelper = new StreamingHelper();
        //start streaming
        logger.debug("start streaming");
        final IAPIResponse startStreamingResponse = makeStreamingCall(streamingTestdata.getStartStreaming().getBody(),
                false);
        streamingHelper.validateStreamingResponse(startStreamingResponse,
                streamingTestdata.getStartStreaming().getResponse());
        //stop streaming
        logger.debug("stop streaming");
        final IAPIResponse stopStreamingResponse = makeStreamingCall(streamingTestdata.getStopStreaming().getBody(),
                false);
        streamingHelper.validateStreamingResponse(stopStreamingResponse, streamingTestdata.getStopStreaming().getResponse());
    }


    @Test(groups = TestGroups.RTMP_STREAMING_TEST, dataProviderClass = StreamingDataProvider.class,
            dataProvider = "fetchSingleValidCase")
    public void verifyDuplicateStreamingCase(StreamingTestdata streamingTestdata) {
        logger.debug("Testcase name : " + streamingTestdata.getTestName());
        final StreamingHelper streamingHelper = new StreamingHelper();
        //start streaming
        logger.debug("start streaming");
        final IAPIResponse startStreamingResponse = makeStreamingCall(streamingTestdata.getStartStreaming().getBody(),
                false);
        streamingHelper.validateStreamingResponse(startStreamingResponse,
                streamingTestdata.getStartStreaming().getResponse());

        //make streaming call again
        logger.debug("start streaming again");
        final IAPIResponse startStreamingResponse1 = makeStreamingCall(streamingTestdata.getStartStreaming().getBody(),
                false);
        streamingHelper.validateDuplicateStreamingResponse(startStreamingResponse1);
    }

    @Test(groups = TestGroups.RTMP_STREAMING_TEST, dataProviderClass = StreamingDataProvider.class,
            dataProvider = "fetchSingleValidCase")
    public void verifyStreamingCaseForInvalidToken(StreamingTestdata streamingTestdata) {
        logger.debug("Testcase name : " + streamingTestdata.getTestName());
        final StreamingHelper streamingHelper = new StreamingHelper();
        //start streaming
        logger.debug("start streaming");
        final IAPIResponse startStreamingResponse = makeStreamingCall(streamingTestdata.getStartStreaming().getBody(),
                true);
        streamingHelper.validateStreamingResponseForInvalidToken(startStreamingResponse);
    }

}
