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
import pc.community.model.User;
import pc.community.service.NotificationService;
import pc.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if (action.equals("questions")) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的问题");
            PageInationDTO<QuestionDTO> pageInationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pageInation", pageInationDTO);
        } else if (action.equals("replies")) {
            PageInationDTO<NotificationDTO> pageInationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("pageInation", pageInationDTO);
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        return "profile";
    }
}
