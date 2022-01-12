package sainthonore.pidorapidoapi.util;

import com.google.gson.Gson;

import org.springframework.stereotype.Component;

import sainthonore.pidorapidoapi.viewmodel.ResponseViewModel;

@Component
public class ResponseUtil {

    public String responseErrorBody(Integer status, String error, String message) {

        ResponseViewModel responseViewModel = new ResponseViewModel();
        responseViewModel.setError(error);
        responseViewModel.setStatus(status);
        responseViewModel.setMessage(message);
        return new Gson().toJson(responseViewModel);

        // return
        // "{\"Status\":"+status+",\"error\":\""+error+"\",\"message\":\""+message+"\"}";
    }

    public String responseSuccessBody(String message) {
        ResponseViewModel responseViewModel = new ResponseViewModel();
        responseViewModel.setStatus(200);
        responseViewModel.setMessage(message);
        return new Gson().toJson(responseViewModel);
    }
}