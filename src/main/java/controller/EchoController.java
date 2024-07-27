package controller;

import compression.SupportedCompressions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import requestFormat.Request;
import responseFormat.Response;
import responses.HttpResponseCode;

import java.util.Set;

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


        //response status line
        response.append(HttpResponseCode.httpVersion)
                .append(" ")
                .append(HttpResponseCode.http200)
                .append(HttpResponseCode.crlf);

        //response header
        response.append("Content-Type: text/plain")
                .append(HttpResponseCode.crlf)
                .append("Content-Length: ")
                .append(str.length());
        String compressionType = compressionSupported(this.request.getRequestHeader().getAccceptEncoding(), SupportedCompressions.getCompressionTypes());
        if(compressionType != null) {
            response.append("Content-Encoding: gzip")
                    .append(HttpResponseCode.crlf);
        }

        //response header end
        response.append(HttpResponseCode.crlf)
                .append(HttpResponseCode.crlf);


        response.append(str);

        return response.toString();
    }

    private String compressionSupported(String accceptEncoding, Set<String> compressionTypes) {

        //substring after "Accept-Encoding: "
        accceptEncoding = accceptEncoding.substring(17);
        String[] clientCompressions = accceptEncoding.split(",");

        for(String s : clientCompressions) {
            if(compressionTypes.contains(s.trim())){
                return s.trim();
            }
        }

        return null;
    }
}
