package ms.restassured.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OAuthCreds {
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String tokenSecret;
}
