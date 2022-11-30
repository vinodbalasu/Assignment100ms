package ms.models.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListSessionsResponseDTO {
    int limit;
    List<SessionResponseDTO> data;
}
