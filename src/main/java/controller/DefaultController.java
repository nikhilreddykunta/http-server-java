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
//To handle default / request
public class DefaultController extends RequestController{

    public DefaultController() {
        super();
    }

    public DefaultController(Request request) {
        super(request);
    }

    @Override
    public String processRequest() {

        response.append(HttpResponseCode.httpVersion)
                .append(" ")
                .append(HttpResponseCode.http200)
                .append(HttpResponseCode.crlf)
                .append(HttpResponseCode.crlf);


        return response.toString();
    }
}
