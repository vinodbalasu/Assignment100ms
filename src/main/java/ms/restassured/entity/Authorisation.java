package ms.restassured.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ms.restassured.constants.AuthType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authorisation {
    //Authorisation can be basic, preemptive, digest, form and OAuth type. OAuth is special case where OAuth 1 needs
    //  consumerKey, consumerSecret, accessToken, tokenSecret and Oauth2 just needs accessToken


    private AuthType authType;
    private String userName;
    private String password;

    //for oauth2
    private String accessToken;

    //for oauth1
    private OAuthCreds oAuthCreds;
}
