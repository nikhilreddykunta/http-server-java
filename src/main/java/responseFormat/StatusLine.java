package responseFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class StatusLine {
    String httpVersion;
    String statusCode;

}
