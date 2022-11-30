package ms.models.streaming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreamingTestdata {
    @JsonProperty("test_name")
    public String testName;
    @JsonProperty("start_streaming")
    public StartStreaming startStreaming;
    @JsonProperty("stop_streaming")
    public StopStreaming stopStreaming;
}
