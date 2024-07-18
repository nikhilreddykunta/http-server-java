package handler;

import lombok.AllArgsConstructor;
import requestFormat.Request;
import requestFormat.RequestBody;
import requestFormat.RequestHeader;
import requestFormat.RequestLine;
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


    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        initialize();
        readRequest();
        parseRequestHeader();

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

    private void parseRequestHeader() {

    }
}
