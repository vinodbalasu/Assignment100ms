package ms.models.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetSessionErrorResponse {
    @JsonProperty("code")
    public Integer code;
    @JsonProperty("message")
    public String message;
    @JsonProperty("data")
    public ErrorData data;

    @Getter
    @Setter
    public static class ErrorData {
        public String description;
    }
}
