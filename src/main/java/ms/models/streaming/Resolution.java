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
public class Resolution {
    @JsonProperty("width")
    public Integer width;
    @JsonProperty("height")
    public Integer height;
}
