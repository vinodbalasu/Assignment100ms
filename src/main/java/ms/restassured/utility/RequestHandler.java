package ms.restassured.utility;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ms.restassured.constants.HttpMethod;
import ms.restassured.entity.Authorisation;
import ms.restassured.entity.IAPIResponse;
import ms.restassured.entity.RestAssuredResponse;
import ms.restassured.entity.SSLConfigData;
import ms.restassured.template.IServiceEndpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;


import java.io.PrintStream;

import static io.restassured.RestAssured.given;


public class RequestHandler {
    private static Logger logger = LogManager.getLogger(RequestHandler.class);

    public IAPIResponse processAPIRequest(IServiceEndpoint iServiceEndpoint) {
        return new RestAssuredResponse(submitRequest(iServiceEndpoint));
    }

    private Response submitRequest(IServiceEndpoint iServiceEndpoint) {
        Response response = processIServiceEndpoint(iServiceEndpoint);
        final String noOfRetries = System.getProperty("noOfRetries");
        int retries = (noOfRetries == null || noOfRetries.isEmpty()) ? 0 : Integer.parseInt(noOfRetries);
        for (int i = 0; i < retries && isResponse5xx(response); i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            response = processIServiceEndpoint(iServiceEndpoint);
        }
        return response;
    }


    private Response processIServiceEndpoint(IServiceEndpoint iServiceEndpoint) {
        RestAssured.registerParser("text/plain", Parser.JSON);
        RestAssured.registerParser("application/grpc", Parser.JSON);
        RestAssured.registerParser("text/html", Parser.JSON);

        final String url = iServiceEndpoint.url();
        final HttpMethod httpMethod = iServiceEndpoint.httpMethod();

        final RequestSpecification requestSpecification = buildRequestSpecification(iServiceEndpoint);
        final PrintStream stream = new PrintStream(IoBuilder.forLogger(logger).buildOutputStream());
        requestSpecification.filter(RequestLoggingFilter.logRequestTo(stream));
        requestSpecification.filter(ResponseLoggingFilter.logResponseTo(stream));
        return makeAPIRequestAsPerHTTPMethod(url, httpMethod, requestSpecification);
    }

    private Response makeAPIRequestAsPerHTTPMethod(String url, HttpMethod httpMethod,
                                                   RequestSpecification requestSpecification) {
        switch (httpMethod) {
            case GET:
                return requestSpecification.get(url);
            case POST:
                return requestSpecification.post(url);
            case HEAD:
                return requestSpecification.head(url);
            case PUT:
                return requestSpecification.put(url);
            case PATCH:
                return requestSpecification.patch(url);
            case DELETE:
                return requestSpecification.delete(url);
            case OPTIONS:
                return requestSpecification.options(url);
        }
        return null;
    }

    private RequestSpecification buildRequestSpecification(IServiceEndpoint iServiceEndpoint) {
        //basic config is built here
        final RestAssuredConfig config = RestAssuredConfig.config().encoderConfig(new EncoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false));

        //check for ssl config
        if (iServiceEndpoint.sslConfig() != null) {
            final SSLConfigData sslConfigData = iServiceEndpoint.sslConfig();
            config.sslConfig(SSLConfig.sslConfig().with().keyStore(sslConfigData.getKeyStorePath(),
                    sslConfigData.getKeyStorePassword()).and().allowAllHostnames());
        }

        final RequestSpecification requestSpecification = given().config(config);

        //check for auth
        if (iServiceEndpoint.auth() != null) {
            buildAuth(iServiceEndpoint.auth(), requestSpecification);
        }

        //formParams
        if (iServiceEndpoint.formParam() != null) {
            iServiceEndpoint.formParam().forEach(param -> requestSpecification.formParam(param.getKey(),
                    param.getValue()));
        }

        //multipart
        if (iServiceEndpoint.multiPart() != null) {
            buildMultiPart(iServiceEndpoint, requestSpecification);
        }

        //headers
        if (iServiceEndpoint.headers() != null) {
            iServiceEndpoint.headers().forEach(h -> requestSpecification.header(h.getKey(), h.getValue()));
        }
        //queryParameters
        if (iServiceEndpoint.queryParameters() != null) {
            iServiceEndpoint.queryParameters().forEach(h -> requestSpecification.queryParam(h.getKey(), h.getValue()));
        }
        //pathParameters
        if (iServiceEndpoint.pathParameters() != null) {
            iServiceEndpoint.pathParameters().forEach(h -> requestSpecification.pathParam(h.getKey(), h.getValue()));
        }

        //body
        if (iServiceEndpoint.requestBody() != null && !iServiceEndpoint.requestBody().isEmpty()) {
            requestSpecification.body(iServiceEndpoint.requestBody());
        }

        return requestSpecification;
    }

    private void buildMultiPart(IServiceEndpoint iServiceEndpoint, RequestSpecification requestSpecification) {
        iServiceEndpoint.multiPart().forEach(multiPart -> {
            if (multiPart.getContentBody() != null) {
                RestAssured.requestSpecification.multiPart(multiPart.getControlName(), multiPart.getContentBody());
            }
            if (multiPart.getFile() != null) {
                RestAssured.requestSpecification.multiPart(multiPart.getControlName(), multiPart.getFile(),
                        multiPart.getMimeType());
            }
        });
    }

    private void buildAuth(Authorisation auth, RequestSpecification requestSpecification) {
        //based on the type build auth
        switch (auth.getAuthType()) {
            case BASIC:
                requestSpecification.auth().basic(auth.getUserName(), auth.getPassword());
                break;
            case PREEMPTIVE:
                requestSpecification.auth().preemptive().basic(auth.getUserName(), auth.getPassword());
                break;
            case DIGEST:
                requestSpecification.auth().digest(auth.getUserName(), auth.getPassword());
                break;
            case OAUTH1:
                requestSpecification.auth().oauth(auth.getOAuthCreds().getConsumerKey(),
                        auth.getOAuthCreds().getConsumerSecret(), auth.getOAuthCreds().getAccessToken(),
                        auth.getOAuthCreds().getTokenSecret());
                break;
            case OAUTH2:
                requestSpecification.auth().oauth2(auth.getAccessToken());
                break;
        }
    }

    private boolean isResponse5xx(Response response) {
        return response.getStatusCode() >= 500 && response.getStatusCode() <505;
    }

}
