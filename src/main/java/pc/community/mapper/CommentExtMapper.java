package pc.community.mapper;

import org.apache.ibatis.annotations.Param;
import pc.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(@Param("record") Comment record);
}