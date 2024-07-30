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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Set;

@Setter
@Getter
//To handle /echo request
public class EchoController extends RequestController{


    public EchoController() {
        super();
    }

    public EchoController(Request request, OutputStream out) {
        super(request, out);
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
        String compressionType = null;
        System.out.println("Accept-Encoding content:"+this.request.getRequestHeader().getAccceptEncoding());
        if(this.request.getRequestHeader().getAccceptEncoding() != null)
            compressionType = compressionSupported(this.request.getRequestHeader().getAccceptEncoding(), SupportedCompressions.getCompressionTypes());
        if(compressionType != null) {
            response.append("Content-Encoding: gzip")
                    .append(HttpResponseCode.crlf);
        }

        response.append("Content-Type: text/plain")
                .append(HttpResponseCode.crlf);

        if(compressionType != null){
            byte[] compressedResponse = new GzipCompression().compress(str);
            String decompressedResponse = new GzipCompression().decompress(compressedResponse);
            System.out.println("Compressed response: "+compressedResponse);
            System.out.println("Decompressed response: "+decompressedResponse);

            response.append("Content-Length: ")
                    .append(compressedResponse.length)
                    .append(HttpResponseCode.crlf);

            //response header end
            response.append(HttpResponseCode.crlf);
            try {
                out.write(response.toString().getBytes("UTF-8"));
                out.write(compressedResponse);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            response.append("Content-Length: ")
                    .append(str.length())
                    .append(HttpResponseCode.crlf);

            //response header end
            response.append(HttpResponseCode.crlf);
            response.append(str);

            try {
                out.write(response.toString().getBytes("UTF-8"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
