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
//To handle all other request which will return 404 NOT FOUND response
public class NotFoundController extends RequestController{

    public NotFoundController() {
        super();
    }

    public NotFoundController(Request request) {
        super(request);
    }

    @Override
    public String processRequest() {

        response.append(HttpResponseCode.httpVersion)
                .append(" ")
                .append(HttpResponseCode.http404)
                .append(HttpResponseCode.crlf)
                .append(HttpResponseCode.crlf);

        return response.toString();
    }
}
