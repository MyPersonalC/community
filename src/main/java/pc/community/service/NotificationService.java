package pc.community.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pc.community.dto.NotificationDTO;
import pc.community.dto.PageInationDTO;
import pc.community.enums.NotificationStatusEnum;
import pc.community.enums.NotificationTypeEnum;
import pc.community.exception.CustomizeErrorCode;
import pc.community.exception.CustomizeException;
import pc.community.mapper.NotificationMapper;
import pc.community.mapper.UserMapper;
import pc.community.model.Notification;
import pc.community.model.NotificationExample;
import pc.community.model.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;

    public PageInationDTO<NotificationDTO> list(Long userId, Integer page, Integer size) {

        PageInationDTO<NotificationDTO> pageInationDTO = new PageInationDTO<>();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        notificationExample.setOrderByClause("gmt_create desc");
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);

        //防止传入的page越界

        Integer totalPage = totalPage(totalCount, size);
        page = isInBound(totalPage, page);
        pageInationDTO.setPageInation(totalPage, page);

        //列出page页面
        Integer offset = size * (page - 1);

        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, size));

        if (notifications.size() == 0) {
            return pageInationDTO;
        }
        CopyOnWriteArrayList<NotificationDTO> notificationDTOS = new CopyOnWriteArrayList<>();

        for (Notification notification: notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);

        }

        pageInationDTO.setData(notificationDTOS);
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

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!notification.getReceiver().equals(user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
