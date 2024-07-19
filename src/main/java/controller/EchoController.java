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
//To handle /echo request
public class EchoController extends RequestController{


    public EchoController() {
        super();
    }

    public EchoController(Request request) {
        super(request);
    }

    @Override
    public String processRequest() {
        String[] requestUrl = this.request.getRequestLine().getRequestUrl().split("/");
        String str = requestUrl[requestUrl.length-1];

        response.append(HttpResponseCode.httpVersion)
                .append(" ")
                .append(HttpResponseCode.http200)
                .append(HttpResponseCode.crlf)
                .append("Content-Type: text/plain")
                .append(HttpResponseCode.crlf)
                .append("Content-Length: ")
                .append(str.length())
                .append(HttpResponseCode.crlf)
                .append(HttpResponseCode.crlf)
                .append(str);

        return response.toString();
    }
}
