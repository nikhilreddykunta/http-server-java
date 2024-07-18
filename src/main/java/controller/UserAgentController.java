package controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import responseFormat.Response;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//To handle /user-agent request
public class UserAgentController extends RequestController{

    @Override
    public String processRequest() {
        return null;
    }
}
