package ms.restassured.entity;

import lombok.*;

import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MultiPart {
    private String controlName;
    private String contentBody;
    private String mimeType;
    private File file;
}
