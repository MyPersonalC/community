package pc.community.dto;

import lombok.Data;

@Data
public class HotTagDTO implements Comparable{
    private String name;
    private Long questionCount;
    private Long commentCount;
    private Long priority;

    public HotTagDTO(String name, Long questionCount, Long commentCount, Long priorty) {
        this.name = name;
        this.questionCount = questionCount;
        this.commentCount = commentCount;
        this.priority = priorty;
    }

    public HotTagDTO() {}

    @Override
    public int compareTo(Object o) {
        return (int)(this.getPriority() - ((HotTagDTO)o).getPriority());
    }
}
