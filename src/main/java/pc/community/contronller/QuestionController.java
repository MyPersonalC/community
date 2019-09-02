package pc.community.contronller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pc.community.dto.CommentDTO;
import pc.community.dto.QuestionDTO;
import pc.community.enums.CommentTypeEnum;
import pc.community.service.CommentService;
import pc.community.service.QuestionService;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model) {
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //累加阅读数//并发问题的产生
        questionService.incView(id);

        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
