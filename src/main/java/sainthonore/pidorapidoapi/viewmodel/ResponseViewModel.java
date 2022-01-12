package sainthonore.pidorapidoapi.viewmodel;

import lombok.Data;

@Data
public class ResponseViewModel {
    Integer status;
    String error, success, message;
}