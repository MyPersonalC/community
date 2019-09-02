package pc.community.contronller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pc.community.dto.NotificationDTO;
import pc.community.dto.PageInationDTO;
import pc.community.dto.QuestionDTO;
import pc.community.enums.NotificationTypeEnum;
import pc.community.model.User;
import pc.community.service.NotificationService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;



    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id,
                          HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id,user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType()==notificationDTO.getType() || NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }else {
            return "redirect:/";
        }
    }
}
