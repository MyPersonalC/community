package pc.community.mapper;

import org.apache.ibatis.annotations.Param;
import pc.community.dto.QuestionQueryDTO;
import pc.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    int incView(@Param("record") Question record);
    int incCommentCount(@Param("record") Question record);
    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}