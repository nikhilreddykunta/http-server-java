package controller;

import lombok.Getter;
import lombok.Setter;
import requestFormat.Request;

import java.io.OutputStream;


public abstract class RequestController {

    public StringBuilder response;
    public Request request;
    public OutputStream out;

    public RequestController() {
        response = new StringBuilder();
    }

    public RequestController(Request request) {
        this.request = request;
        response = new StringBuilder();
    }

    public RequestController(Request request, OutputStream out) {
        this.request = request;
        this.out = out;
        response = new StringBuilder();
    }

    public abstract String processRequest();
}
