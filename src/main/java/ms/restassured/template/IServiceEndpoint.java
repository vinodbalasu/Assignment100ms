package ms.restassured.template;

import ms.restassured.constants.HttpMethod;
import ms.restassured.entity.*;

import java.util.List;

public interface IServiceEndpoint {

    String url();

    HttpMethod httpMethod();

    List<Param> queryParameters();

    List<Param> pathParameters();

    List<Param> headers();

    String requestBody();

    Authorisation auth();

    SSLConfigData sslConfig();

    List<MultiPart> multiPart();

    List<Param> formParam();
}
