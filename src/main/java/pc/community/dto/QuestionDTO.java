package pc.community.dto;

import lombok.Data;
import pc.community.model.User;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmt_create;
    private Long gmt_modified;
    private Long creator;
    private String tag;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;
    private User user;
}
