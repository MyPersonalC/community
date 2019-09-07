package pc.community.schedule;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pc.community.cache.HotTagCache;
import pc.community.dto.HotTagDTO;
import pc.community.mapper.QuestionMapper;
import pc.community.model.Question;
import pc.community.model.QuestionExample;


import java.util.*;

@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 12 * 60 * 60 * 1000)
    public void hotTagSchedule() {
        int offset = 0;
        int limit = 5;
        log.info("hot schedule start {}", new Date());
        List<Question> list = new ArrayList<>();
        Map<String, HotTagDTO> priorities = new HashMap<>();

        while (offset == 0 || list.size() == limit) {
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : list) {

                String[] tags = StringUtils.split(makeQueryStringAllRegExp(question.getTag()), ",|，");
                for (String tag : tags) {
                    Long priorty;
                    Long questionCount;
                    Long commentCount;
                    if (priorities.get(tag)==null){
                        priorty = 5 + question.getCommentCount();
                        questionCount = 1L;
                        commentCount = question.getCommentCount();
                    }else {
                        if (priorities.get(tag).getPriority() == null || priorities.get(tag).getPriority()==0) {
                            priorty = 5 + question.getCommentCount();
                        }else {
                            priorty = priorities.get(tag).getPriority() + 5 + question.getCommentCount();
                        }
                        if (priorities.get(tag).getQuestionCount() == null || priorities.get(tag).getQuestionCount()==0) {
                            questionCount = 1L;
                        }else {
                            questionCount = priorities.get(tag).getQuestionCount() + 1;
                        }
                        if (priorities.get(tag).getCommentCount() == null || priorities.get(tag).getCommentCount()==0) {
                            commentCount = question.getCommentCount();
                        }else {
                            commentCount = priorities.get(tag).getCommentCount() + question.getCommentCount();
                        }
                    }
                    priorities.put(tag,new HotTagDTO(tag,questionCount,commentCount,priorty));
                }
            }
            offset += limit;
        }
        hotTagCache.updateTags(priorities);
        log.info("hot schedule stop {}", new Date());
    }

    private String makeQueryStringAllRegExp(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        return str.replace("\\", "\\\\").replace("*", "\\*")
                .replace("+", "\\+").replace("|", "\\|")
                .replace("{", "\\{").replace("}", "\\}")
                .replace("(", "\\(").replace(")", "\\)")
                .replace("^", "\\^").replace("$", "\\$")
                .replace("[", "\\[").replace("]", "\\]")
                .replace("?", "\\?").replace(".", "\\.")
                .replace("&", "\\&");
    }
}
