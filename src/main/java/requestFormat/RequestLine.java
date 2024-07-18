package requestFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class RequestLine {
    String requestType; //GET, POST, ...
    String requestUrl;
    String httpVersion; //HTTP/1.1
}
