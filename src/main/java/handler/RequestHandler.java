package handler;

import controller.*;
import lombok.AllArgsConstructor;
import requestFormat.Request;
import requestFormat.RequestBody;
import requestFormat.RequestHeader;
import requestFormat.RequestLine;
import responseFormat.Response;
import responses.HttpResponseCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

@AllArgsConstructor
public class RequestHandler implements Runnable {

    Socket clientSocket;
    OutputStream out;
    BufferedReader in;
    String clientMessage;

    RequestLine requestLine;
    RequestHeader requestHeader;
    RequestBody requestBody;

    RequestController requestController;
    String response;


    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try {
            out = clientSocket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initialize();
        readRequest();
        parseRequest();
        processRequest();


    }

    private void initialize() {
        requestLine = new RequestLine();
        requestHeader = new RequestHeader();
        requestBody = new RequestBody();
    }

    private void readRequest() {
        StringBuilder clientMessageBuilder = new StringBuilder();
        try {


            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            for (String msg = in.readLine(); msg != null && !msg.equals(""); ) {
                System.out.println("reading msg: " + msg);
                clientMessageBuilder.append(msg)
                        .append(" ")
                        .append(HttpResponseCode.crlf);

                msg = in.readLine();
            }

            clientMessage = clientMessageBuilder.toString();

            System.out.println("Client message: " + clientMessage);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseRequest() {
        String[] message = clientMessage.split(HttpResponseCode.crlf);
        parseRequestLine(message[0]);
        parseRequestHeader(message);
        parseRequestBody(message[message.length-1]);


    }

    private void parseRequestLine(String s) {
        String[] requestLine = s.split(" ");
        this.requestLine.setRequestType(requestLine[0].trim());
        this.requestLine.setRequestUrl(requestLine[1].trim());
        this.requestLine.setHttpVersion(requestLine[2].trim());
    }

    private void parseRequestHeader(String[] message) {

        System.out.println("In RequestHandler -> parseRequestHeader");
        System.out.println("Message length: "+message.length);


        for(int i=1; i< message.length; i++) {
            System.out.println("message: "+message[i]);
            if(message[i].contains("User-Agent")) {
                this.requestHeader.setUserAgent(message[i].trim());
            }
            else if(message[i].contains("Host")) {
                this.requestHeader.setHost(message[i].trim());
            }
            else if(message[i].contains("Accept-Encoding")) {
                System.out.println("Setting Accept-Encoding in header: "+message[i]);
                this.requestHeader.setAccceptEncoding(message[i].trim());
            }
            else if(message[i].contains("Accept")) {
                this.requestHeader.setAccept(message[i].trim());
            }
            else if(message[i].contains("Content-Type")) {
                this.requestHeader.setContentType(message[i].trim());
            }
            else if(message[i].contains("Content-Length")) {
                this.requestHeader.setContentLength(message[i].trim());
            }

        }
    }

    private void parseRequestBody(String s) {

        if("POST".equals(this.requestLine.getRequestType())) {
            StringBuilder body = new StringBuilder();
            int contentLenght = Integer.parseInt(this.requestHeader.getContentLength().substring(16));

            try {
                for(int i=0; i<contentLenght; i++) {

                    body.append((char)in.read());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            this.requestBody.setBody(body.toString());
            System.out.println("request body: "+body.toString());
        }

        else {
            this.requestBody.setBody(s.toString());
        }

    }

    private void processRequest() {

        Request request = new Request(requestLine, requestHeader, requestBody);
        if(requestLine.getRequestUrl().contains("/echo")) {
            requestController = new EchoController(request, out);
            requestController.processRequest();
            return;
        }
        else if(requestLine.getRequestUrl().contains("/user-agent")) {
            requestController = new UserAgentController(request);
        }
        else if(requestLine.getRequestUrl().contains("/files")) {
            requestController = new FilesController(request);
        }
        else if("/".equals(requestLine.getRequestUrl())) {
            requestController = new DefaultController(request);
        }
        else {
            requestController = new NotFoundController(request);
        }

        response = requestController.processRequest();

        try {
            System.out.println("Response before sending to client: "+response);
            out.write(response.getBytes("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

}
