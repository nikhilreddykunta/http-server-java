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

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(4221);

            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            ExecutorService es = Executors.newFixedThreadPool(10);

            while (true) {
                System.out.println("Waiting for connection");
                Socket clientSocket = serverSocket.accept();
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                System.out.println("Connection accepted");
                RequestHandler requestHandler = new RequestHandler(clientSocket);
                es.execute(requestHandler);
                System.out.println("Spawned a new thread for processing request");
            }


//       clientSocket.getOutputStream().write("HTTP/1.1 200 OK\\r\\n\\r\\n".getBytes());
//       OutputStream out = clientSocket.getOutputStream();
//       out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());



        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
