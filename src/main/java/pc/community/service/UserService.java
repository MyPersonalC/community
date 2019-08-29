package pc.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pc.community.mapper.UserMapper;
import pc.community.model.User;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void insertOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccount_id());
        if(dbUser!=null){
            //更新
            dbUser.setGmt_modified(System.currentTimeMillis());
            dbUser.setAvatar_url(user.getAvatar_url());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            dbUser.setBio(user.getBio());
            userMapper.update(dbUser);
        }else {
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
        }
    }
}
