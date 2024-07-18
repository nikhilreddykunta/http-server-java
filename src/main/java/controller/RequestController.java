package controller;

import lombok.Getter;
import lombok.Setter;
import requestFormat.Request;


public abstract class RequestController {

    public StringBuilder response;
    public Request request;

    public abstract String processRequest();
}
