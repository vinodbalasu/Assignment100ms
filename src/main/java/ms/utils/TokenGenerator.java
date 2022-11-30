package ms.utils;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class TokenGenerator {
    private String managementToken;
    private String hmsClientToken;
    private static TokenGenerator tokenGenerator;

    private TokenGenerator() {}

    public static TokenGenerator getInstance() {
        if (tokenGenerator == null)
            return new TokenGenerator();
        return tokenGenerator;
    }

    public String getManagementToken() {
        if (managementToken == null)
            return generateManagementToken();
        return managementToken;
    }

    public String getHmsClientToken() {
        if (hmsClientToken == null)
            return generateHmsClientToken();
        return hmsClientToken;
    }


    private String generateManagementToken() {
        final String appAccessKey = System.getProperty("app_access_key");
        final String appSecret = System.getProperty("app_secret");

        final Map<String, Object> payload = new HashMap<>();
        payload.put("access_key", appAccessKey);
        payload.put("type", "management");
        payload.put("version", 2);
        return Jwts.builder().setClaims(payload).setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + 86400 * 1000))
                .setIssuedAt(Date.from(Instant.ofEpochMilli(System.currentTimeMillis() - 60000)))
                .setNotBefore(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, appSecret.getBytes()).compact();
    }

    //temporarily not needed
    private String generateHmsClientToken() {
        Map<String, Object> payload = new HashMap<>();
        payload.put(System.getProperty("access_key"), System.getProperty("app_access_key"));
        payload.put("room_id", System.getProperty("room_id"));
        payload.put("user_id", "<user_id>");
        payload.put("role", "<role>");
        payload.put("type", "app");
        payload.put("version", 2);
        return Jwts.builder().setClaims(payload).setId(UUID.randomUUID().toString())
                .setExpiration(new Date(System.currentTimeMillis() + 86400 * 1000))
                .setIssuedAt(Date.from(Instant.ofEpochMilli(System.currentTimeMillis() - 60000)))
                .setNotBefore(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, "<app_secret>".getBytes()).compact();
    }
}
