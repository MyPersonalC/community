package pc.community.model;

import lombok.Data;

/*
    id bigint auto_increment,
	title varchar(50),
	description text,
	gmt_create bigint,
	gmt_modified bigint,
	creator bigint,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	tag varchar(256),
 */
@Data
public class Question {
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
}
