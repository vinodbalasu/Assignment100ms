package ms.models.streaming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ms.models.ExpectedResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopStreaming {
    @JsonProperty("body")
    public StreamingDTO body;
    @JsonProperty("response")
    public ExpectedResponse response;
}
