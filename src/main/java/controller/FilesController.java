package controller;

import requestFormat.Request;
import responses.HttpResponseCode;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesController extends RequestController{

    private static final String directory =
//            "D:\\misc\\";
            "/tmp/data/codecrafters.io/http-server-tester/";

    public FilesController() {
        super();
    }

    public FilesController(Request request) {
        super(request);
    }
    @Override
    public String processRequest() {

        String[] requestUrl = this.request.getRequestLine().getRequestUrl().split("/");

        String fileName = requestUrl[requestUrl.length-1];

        boolean fileExists = checkFilesExists(fileName);

        if(fileExists) {
            return okResponse(fileName);
        }
        else {
            return notFoundResponse(fileName);
        }

//        return response.toString();
    }

    private String notFoundResponse(String fileName) {
        response.append(HttpResponseCode.httpVersion)
                .append(" ")
                .append(HttpResponseCode.http404)
                .append(HttpResponseCode.crlf)
                .append(HttpResponseCode.crlf);

        return response.toString();
    }

    private String okResponse(String fileName) {

        StringBuilder fileContent = new StringBuilder();
        File file = new File(directory+fileName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(
              new FileReader(file)
            );

            String currentLine;

            while(true) {
                try {
                    if (!((currentLine = br.readLine()) != null)) break;
                    else {
                        fileContent.append(currentLine);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            response.append(HttpResponseCode.httpVersion)
                    .append(" ")
                    .append(HttpResponseCode.http200)
                    .append(HttpResponseCode.crlf)
                    .append("Content-Type: application/octet-stream")
                    .append(HttpResponseCode.crlf)
                    .append("Content-Length: ")
                    .append(fileContent.length())
                    .append(HttpResponseCode.crlf)
                    .append(HttpResponseCode.crlf)
                    .append(fileContent.toString());

            return response.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    private boolean checkFilesExists(String fileName) {

        String fullPath = directory+fileName;

        Path path = Paths.get(fullPath);
        System.out.println("path: "+path);

        return Files.exists(path);
    }
}
