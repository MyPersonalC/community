package pc.community.advice;


import com.alibaba.fastjson.JSON;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import pc.community.dto.ReslutDTO;
import pc.community.exception.CustomizeErrorCode;
import pc.community.exception.CustomizeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomzieExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable ex, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentype = request.getContentType();
        if("application/json".equals(contentype)){
            //返回json
            ReslutDTO reslutDTO;
            if (ex instanceof CustomizeException) {
                reslutDTO = ReslutDTO.errorOf((CustomizeException) ex);
            } else {
                reslutDTO = ReslutDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
            try {
                //返回json格式
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(reslutDTO));
                writer.close();
            } catch (IOException e) {
            }
            return null;
        }else {
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
