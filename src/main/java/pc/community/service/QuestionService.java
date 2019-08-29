package pc.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pc.community.dto.PageInationDTO;
import pc.community.dto.QuestionDTO;
import pc.community.mapper.QuestionMapper;
import pc.community.mapper.UserMapper;
import pc.community.model.Question;
import pc.community.model.User;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 不仅仅使用questionmapper，同时使用usermapper，起到组装的作用，
 * 当一个请求同时要使用到user和question时，需要service层来进行协调
 */
@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PageInationDTO list(Integer page, Integer size) {
        CopyOnWriteArrayList<QuestionDTO> questionDTOS = new CopyOnWriteArrayList<>();
        PageInationDTO pageInationDTO = new PageInationDTO();
        Integer totalCount = questionMapper.count();

        //防止传入的page越界
        Integer totalPage = totalPage(totalCount, size);
        page = isInBound(totalPage, page);
        pageInationDTO.setPageInation(totalPage, page);

        //列出page页面
        Integer offset = size * (page - 1);
        CopyOnWriteArrayList<Question> questions = questionMapper.list(offset, size);
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageInationDTO.setQuestionlist(questionDTOS);
        return pageInationDTO;
    }

    public PageInationDTO list(Long userId, Integer page, Integer size) {
        CopyOnWriteArrayList<QuestionDTO> questionDTOS = new CopyOnWriteArrayList<>();
        PageInationDTO pageInationDTO = new PageInationDTO();
        Integer totalCount = questionMapper.countByUserID(userId);

        //防止传入的page越界

        Integer totalPage = totalPage(totalCount, size);
        page = isInBound(totalPage, page);
        pageInationDTO.setPageInation(totalPage, page);

        //列出page页面
        Integer offset = size * (page - 1);
        CopyOnWriteArrayList<Question> questions = questionMapper.listByUserId(userId, offset, size);
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageInationDTO.setQuestionlist(questionDTOS);
        return pageInationDTO;
    }



    private Integer isInBound(Integer totalPage, Integer page) {
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        return page;
    }


    public Integer totalPage(Integer totalCount, Integer size) {
        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        return totalPage;
    }


    public QuestionDTO getQuestionById(Long id) {
        Question question = questionMapper.getQuestionById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null){
            //创建
            question.setGmt_create(System.currentTimeMillis());
            question.setGmt_modified(question.getGmt_create());
            questionMapper.create(question);
        }else {
            //更新
            question.setGmt_modified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
