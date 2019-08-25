package pc.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import pc.community.model.User;


@Mapper
public interface UserMapper {
    @Insert("insert into User(name,account_id,token,gmt_create,gmt_modified) values (#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified})")
    public void insert(User user);
}
