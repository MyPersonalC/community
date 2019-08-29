package pc.community.contronller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pc.community.dto.QuestionDTO;
import pc.community.model.User;
import pc.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model,
                           HttpServletRequest request){
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        //未登录用户可以查看问题页
//        User user = (User) request.getSession().getAttribute("user");
//        if (user == null){
//            return "index";
//        }
//        questionDTO.setUser(user);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
