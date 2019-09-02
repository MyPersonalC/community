package pc.community.dto;

import lombok.Data;
import pc.community.model.User;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private String tag;
    private Long commentCount;
    private Long viewCount;
    private Long likeCount;
    private User user;
}
