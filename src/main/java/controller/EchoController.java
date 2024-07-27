package controller;

import compression.GzipCompression;
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
                .append(HttpResponseCode.crlf);


        String compressionType = null;
        System.out.println("Accept-Encoding content:"+this.request.getRequestHeader().getAccceptEncoding());
        if(this.request.getRequestHeader().getAccceptEncoding() != null)
            compressionType = compressionSupported(this.request.getRequestHeader().getAccceptEncoding(), SupportedCompressions.getCompressionTypes());
        if(compressionType != null) {
            response.append("Content-Encoding: gzip")
                    .append(HttpResponseCode.crlf);
        }



        if(compressionType != null){
            String compressedResponse = new GzipCompression().compress(str);
            System.out.println(compressedResponse);

            response.append("Content-Length: ")
                    .append(compressedResponse.length())
                    .append(HttpResponseCode.crlf);

            //response header end
            response.append(HttpResponseCode.crlf);

            response.append(compressedResponse);
        }
        else {
            response.append("Content-Length: ")
                    .append(str.length())
                    .append(HttpResponseCode.crlf);

            //response header end
            response.append(HttpResponseCode.crlf);
            response.append(str);
        }


        return response.toString();
    }

    private String compressionSupported(String accceptEncoding, Set<String> compressionTypes) {

        System.out.println("checking compressionSupported");
        System.out.println("Accept-Encoding from client:" + accceptEncoding);
        System.out.println("Supported compressions: " +compressionTypes.toString());

        //substring after "Accept-Encoding: "
        accceptEncoding = accceptEncoding.substring(17);
        String[] clientCompressions = accceptEncoding.split(",");

        for(String s : clientCompressions) {
            if(compressionTypes.contains(s.trim())){
                System.out.println("Compression Type supported: "+s);
                return s.trim();
            }
        }

        return null;
    }
}
