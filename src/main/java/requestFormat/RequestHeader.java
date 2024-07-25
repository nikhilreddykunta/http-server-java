package requestFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class RequestHeader {
    String host;
    String userAgent;
    String accept;
    String contentType;
    String contentLength;
}
