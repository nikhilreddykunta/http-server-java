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
        initialize();
        readRequest();
        parseRequest();
        processRequest();

        try {
            out = clientSocket.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        for(int i=1; i< message.length-1; i++) {
            if(message[i].contains("User-Agent")) {
                this.requestHeader.setUserAgent(message[i].trim());
            }
            else if(message[i].contains("Host")) {
                this.requestHeader.setHost(message[i].trim());
            }
            else if(message[i].contains("Accept")) {
                this.requestHeader.setAccept(message[i].trim());
            }
        }
    }

    private void parseRequestBody(String s) {
        this.requestBody.setBody(s);
    }

    private void processRequest() {
        if(requestLine.getRequestUrl().contains("/echo")) {
            requestController = new EchoController();
        }
        else if(requestLine.getRequestUrl().contains("/user-agent")) {
            requestController = new UserAgentController();
        }
        else if("/".equals(requestLine.getRequestUrl())) {
            requestController = new DefaultController();
        }
        else {
            requestController = new NotFoundController();
        }

        response = requestController.processRequest();


    }

}
