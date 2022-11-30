package ms.restassured.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SSLConfigData {
    private String keyStorePath;
    private String keyStorePassword;
}
