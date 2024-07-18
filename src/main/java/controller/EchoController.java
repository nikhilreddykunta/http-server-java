package controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import responseFormat.Response;
import responses.HttpResponseCode;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//To handle /echo request
public class EchoController extends RequestController{



    @Override
    public String processRequest() {



        response.append(HttpResponseCode.httpVersion)
                .append(" ")
                .append(HttpResponseCode.http200)
                .append(HttpResponseCode.crlf)
                .append("Content-Type: text/plain")
                .append(HttpResponseCode.crlf)
                .append("Content-Length: ")

        return response.toString();
    }
}
