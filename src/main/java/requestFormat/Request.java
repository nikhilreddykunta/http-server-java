package requestFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Request {
    RequestLine requestLine;
    RequestHeader requestHeader;
    RequestBody requestBody;
}
