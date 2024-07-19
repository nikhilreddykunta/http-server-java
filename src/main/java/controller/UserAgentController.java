package controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import requestFormat.Request;
import responseFormat.Response;
import responses.HttpResponseCode;

@Setter
@Getter
//To handle /user-agent request
public class UserAgentController extends RequestController{

    public UserAgentController() {
        super();
    }

    public UserAgentController(Request request) {
        super(request);
    }
    @Override
    public String processRequest() {

        String userAgent = request.getRequestHeader().getUserAgent().substring(12).trim();

        response.append(HttpResponseCode.httpVersion)
                .append(" ")
                .append(HttpResponseCode.http200)
                .append(HttpResponseCode.crlf)
                .append("Content-Type: text/plain")
                .append(HttpResponseCode.crlf)
                .append("Content-Length: ")
                .append(userAgent.length())
                .append(HttpResponseCode.crlf)
                .append(HttpResponseCode.crlf)
                .append(userAgent);

        return response.toString();
    }
}
