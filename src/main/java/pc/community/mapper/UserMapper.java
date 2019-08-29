package pc.community.mapper;

import org.apache.ibatis.annotations.*;
import pc.community.model.User;


@Mapper
public interface UserMapper {
    @Insert("insert into User(name,account_id,token,gmt_create,gmt_modified,bio,avatar_url) values (#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified},#{bio},#{avatar_url})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where account_id = #{account_id}")
    User findByAccountId(@Param("account_id") String account_id);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Long id);

    @Update("update user set name = #{name}, token = #{token}, gmt_modified = #{gmt_modified}, bio = #{bio}, avatar_url = #{avatar_url} where id = #{id}")
    void update(User dbUser);
}
