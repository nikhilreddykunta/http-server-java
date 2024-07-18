import responses.HttpResponseCode;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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

       Socket clientSocket = serverSocket.accept(); // Wait for connection from client.
       System.out.println("accepted new connection");


       BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
       OutputStream out = clientSocket.getOutputStream();
       String clientMessage = in.readLine();

       String[] message = parseMessage(clientMessage);

       String request = message[0];
       String[] requestLine = request.split(" ");

       String target = requestLine[1];

       StringBuilder respone = new StringBuilder(HttpResponseCode.httpVersion);
       respone.append(" ");

       if("/".equals(target)) {
            respone.append(HttpResponseCode.http200)
                    .append(HttpResponseCode.crlf)
                    .append(HttpResponseCode.crlf);
       }
       else if(target.contains("echo")) {
         System.out.println("processing echo request");
            String[] requestParams = target.split("/");
            String str = requestParams[requestParams.length-1];

            respone.append(HttpResponseCode.http200)
                    .append(HttpResponseCode.crlf)
                    .append("Content-Type: text/plain")
                    .append(HttpResponseCode.crlf)
                    .append("Content-Length: ")
                    .append(str.length())
                    .append(HttpResponseCode.crlf)
                    .append(HttpResponseCode.crlf)
                    .append(str);
//                    .append(HttpResponseCode.crlf);
         System.out.println("Response: "+respone);

       }
       else {
           respone.append(HttpResponseCode.http404)
                   .append(HttpResponseCode.crlf)
                   .append(HttpResponseCode.crlf);
       }

       out.write(respone.toString().getBytes());


//       clientSocket.getOutputStream().write("HTTP/1.1 200 OK\\r\\n\\r\\n".getBytes());
//       OutputStream out = clientSocket.getOutputStream();
//       out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());

       clientSocket.close();
       serverSocket.close();


     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }

    private static String[] parseMessage(String clientMessage) {
      return clientMessage.split(HttpResponseCode.crlf);

    }
}
