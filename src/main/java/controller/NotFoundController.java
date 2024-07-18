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
//To handle all other request which will return 404 NOT FOUND response
public class NotFoundController extends RequestController{

    @Override
    public String processRequest() {
        return null;
    }
}
