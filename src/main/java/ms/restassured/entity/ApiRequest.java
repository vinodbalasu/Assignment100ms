package ms.restassured.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequest<T> {
    private T body;
    private List<Param> headers;
    private List<Param> pathParameters;
    private List<Param> queryParameters;
}
