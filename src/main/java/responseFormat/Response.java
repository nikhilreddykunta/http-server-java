package responseFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Response {

    StatusLine statusLine;
    ResponseHeader responseHeader;
    ResponseBody responseBody;
}
