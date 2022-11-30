package ms.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpectedResponse {
    @JsonProperty("response_message")
    public String responseMessage;
    @JsonProperty("statusCode")
    public Integer statusCode;
}
