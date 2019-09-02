package pc.community.dto;

import lombok.Data;
import pc.community.exception.CustomizeErrorCode;
import pc.community.exception.CustomizeException;

@Data
public class ReslutDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ReslutDTO errorOf(Integer code, String message) {
        ReslutDTO reslutDTO = new ReslutDTO();
        reslutDTO.setCode(code);
        reslutDTO.setMessage(message);
        return reslutDTO;
    }

    public static ReslutDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ReslutDTO errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(),ex.getMessage());
    }

    public static ReslutDTO okOf(){
        ReslutDTO reslutDTO = new ReslutDTO();
        reslutDTO.setCode(200);
        reslutDTO.setMessage("Success");
        return reslutDTO;
    }
    public static <T> ReslutDTO<T> okOf(T t){
        ReslutDTO<T> reslutDTO = new ReslutDTO<T>();
        reslutDTO.setCode(200);
        reslutDTO.setMessage("Success");
        reslutDTO.setData(t);
        return reslutDTO;
    }
}
