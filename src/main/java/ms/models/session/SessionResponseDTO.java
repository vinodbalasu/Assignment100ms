package ms.models.session;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionResponseDTO {

    @JsonProperty("id")
    public String id;
    @JsonProperty("room_id")
    public String roomId;
    @JsonProperty("customer_id")
    public String customerId;
    @JsonProperty("active")
    public Boolean active;
    @JsonProperty("peers")
    public Map<String, PeerDTO> peers;
    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("updated_at")
    public String updatedAt;
    @JsonProperty("_id")
    public String _id;
}
