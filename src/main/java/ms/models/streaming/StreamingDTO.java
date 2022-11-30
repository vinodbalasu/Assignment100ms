package ms.models.streaming;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreamingDTO {
    @JsonProperty("operation")
    public String operation;
    @JsonProperty("room_id")
    public String roomId;
    @JsonProperty("meeting_url")
    public String meetingUrl;
    @JsonProperty("rtmp_urls")
    public List<String> rtmpUrls = null;
    @JsonProperty("record")
    public Boolean record;
    @JsonProperty("resolution")
    public Resolution resolution;
}
