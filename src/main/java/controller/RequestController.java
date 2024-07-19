package controller;

import lombok.Getter;
import lombok.Setter;
import requestFormat.Request;


public abstract class RequestController {

    public StringBuilder response;
    public Request request;

    public RequestController() {
        response = new StringBuilder();
    }

    public RequestController(Request request) {
        this.request = request;
        response = new StringBuilder();
    }

    public abstract String processRequest();
}
