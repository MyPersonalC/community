package pc.community.mapper;

import org.apache.ibatis.annotations.*;
import pc.community.model.Question;

import java.util.concurrent.CopyOnWriteArrayList;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset}, #{size}")
    CopyOnWriteArrayList<Question> list(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{creator} limit #{offset}, #{size}")
    CopyOnWriteArrayList<Question> listByUserId(@Param("creator") Long userId, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where creator = #{creator}")
    Integer countByUserID(@Param("creator") Long userId);

    @Select("select * from question where id = #{id}")
    Question getQuestionById(@Param("id")Long id);

    @Update("update question set title = #{title}, description = #{description}, gmt_modified = #{gmt_modified},tag = #{tag} where id = #{id}")
    void update(Question question);
}
