package pc.community.cache;

import lombok.Data;
import org.springframework.stereotype.Component;
import pc.community.dto.HotTagDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Component
@Data
public class HotTagCache {

    private List<HotTagDTO> hots = new ArrayList<>();

    public void updateTags(Map<String, HotTagDTO> tages) {
        int max = 3;
        //优先队列，小根堆实现topN
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);
        tages.forEach((name, priority) -> {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority.getPriority());
            hotTagDTO.setQuestionCount(priority.getQuestionCount());
            hotTagDTO.setCommentCount(priority.getCommentCount());
            if (priorityQueue.size() < max) {
                priorityQueue.add(hotTagDTO);
            } else {
                HotTagDTO minHot = priorityQueue.peek();
                if (hotTagDTO.compareTo(minHot) > 0) {
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });
        List<HotTagDTO> sortedTags = new ArrayList<>();
        HotTagDTO poll = priorityQueue.poll();
        while (poll != null) {
            sortedTags.add(0, poll);
            poll = priorityQueue.poll();
        }
        hots = sortedTags;
    }
}
