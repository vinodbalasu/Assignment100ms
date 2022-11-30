package ms.models.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeerDTO {
    @JsonProperty("id")
    public String id;
    @JsonProperty("session_id")
    public String sessionId;
    @JsonProperty("name")
    public String name;
    @JsonProperty("role")
    public String role;
    @JsonProperty("user")
    public String user;
    @JsonProperty("joined_at")
    public String joinedAt;
    @JsonProperty("left_at")
    public String leftAt;
}
