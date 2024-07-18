import handler.RequestHandler;
import responses.HttpResponseCode;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");

        // Uncomment this block to pass the first stage

        try {
            ServerSocket serverSocket = new ServerSocket(4221);

            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            ExecutorService es = Executors.newFixedThreadPool(10);

            while(true) {
                System.out.println("Waiting for connection");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted");
                RequestHandler requestHandler = new RequestHandler(clientSocket);
                es.execute(requestHandler);
                System.out.println("Spawned a new thread for processing request");
            }


            System.out.println("accepted new connection");

















            StringBuilder response = new StringBuilder(HttpResponseCode.httpVersion);
            response.append(" ");

            if ("/".equals(target)) {
                response.append(HttpResponseCode.http200)
                        .append(HttpResponseCode.crlf)
                        .append(HttpResponseCode.crlf);
            } else if (target.contains("echo")) {
                System.out.println("processing echo request");
                String[] requestParams = target.split("/");
                String str = requestParams[requestParams.length - 1];

                response.append(HttpResponseCode.http200)
                        .append(HttpResponseCode.crlf)
                        .append("Content-Type: text/plain")
                        .append(HttpResponseCode.crlf)
                        .append("Content-Length: ")
                        .append(str.length())
                        .append(HttpResponseCode.crlf)
                        .append(HttpResponseCode.crlf)
                        .append(str);
//                    .append(HttpResponseCode.crlf);
                System.out.println("Response: " + response);

            } else if ("/user-agent".equals(target)) {
                System.out.println("processing user-agent request");
                String userAgent = null;

                System.out.println("Message length: " + message.length);

                for (int i = 1; i < message.length; i++) {
//                System.out.print("checking for user-agent");
                    if (message[i].contains("User-Agent")) {
                        String tmp = message[i];
                        userAgent = tmp.substring(12);
                        System.out.println("User-Agent: " + userAgent);
                        break;
                    }
                }

                response.append(HttpResponseCode.http200)
                        .append(HttpResponseCode.crlf)
                        .append("Content-Type: text/plain")
                        .append(HttpResponseCode.crlf)
                        .append("Content-Length: ")
                        .append(userAgent.length())
                        .append(HttpResponseCode.crlf)
                        .append(HttpResponseCode.crlf)
                        .append(userAgent);

            } else {
                response.append(HttpResponseCode.http404)
                        .append(HttpResponseCode.crlf)
                        .append(HttpResponseCode.crlf);
            }

            out.write(response.toString().getBytes());


//       clientSocket.getOutputStream().write("HTTP/1.1 200 OK\\r\\n\\r\\n".getBytes());
//       OutputStream out = clientSocket.getOutputStream();
//       out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());

            clientSocket.close();
            serverSocket.close();


        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

}
