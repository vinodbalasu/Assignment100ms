package ms.dataproviders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ms.models.streaming.StreamingDTO;
import ms.models.streaming.StreamingTestdata;
import ms.utils.PropertiesLoader;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StreamingDataProvider {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DataProvider(name = "fetchValidCases")
    public Object[] getValidStreamingCases() throws IOException {
        //read json from resources and convert it into DTO and modify the data for the fields as and when necessary.
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("testdata/streaming_data_valid.json");
        return loadTestDataFromJSON(inputStream);
    }

    @DataProvider(name = "fetchInValidCases")
    public Object[] getInValidStreamingCases() throws IOException {
        //read json from resources and convert it into DTO and modify the data for the fields as and when necessary.
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("testdata/streaming_data_invalid.json");
        return loadTestDataFromJSON(inputStream);
    }

    @DataProvider(name = "fetchSingleValidCase")
    public Object[] getValidStreamingCase() throws IOException {
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("testdata/streaming_data_valid.json");
        final Object[] object = new Object[1];
        object[0] = loadTestDataFromJSON(inputStream)[0];
        return object;
    }


    private Object[] loadTestDataFromJSON(InputStream inputStream) throws IOException {
        TypeReference<List<StreamingTestdata>> typeReference = new TypeReference<List<StreamingTestdata>>() {};
        final List<StreamingTestdata> streamingTestdataList = objectMapper.readValue(inputStream, typeReference);
        updateRoomIdAndMeetingUrlsInTestdata(streamingTestdataList);
        return streamingTestdataList.toArray();
    }

    public void updateRoomIdAndMeetingUrlsInTestdata(final List<StreamingTestdata> streamingTestdataList) {
        for (StreamingTestdata streamingTestdata : streamingTestdataList) {
            //check for replacements in start_streaming and stop_streaming objects
            final StreamingDTO startStreaming = streamingTestdata.getStartStreaming().getBody();
            if (startStreaming.getRoomId() !=null && startStreaming.getRoomId().equals("valid") )
                startStreaming.setRoomId((String) PropertiesLoader.properties.get("room_id"));
            if (startStreaming.getMeetingUrl() != null && startStreaming.getMeetingUrl().equals("valid"))
                startStreaming.setMeetingUrl((String) PropertiesLoader.properties.get("host_url"));

            final StreamingDTO stopStreaming = streamingTestdata.getStopStreaming().getBody();
            if (stopStreaming.getRoomId() !=null && stopStreaming.getRoomId().equals("valid"))
                stopStreaming.setRoomId((String) PropertiesLoader.properties.get("room_id"));
        }
    }

}
