## PC社区

## 资料
 - [Spring 文档](https://spring.io/guides)
 - [spring boot的使用](https://spring.io/guides/gs/serving-web-content/) 
 - [spring boot依赖](https://elasticsearch.cn/explore)
 - [ssh连接GitHub](https://help.github.com/en/articles/connecting-to-github-with-ssh)
 - [Bootstrap 文档](https://v3.bootcss.com/getting-started/)
 - [Github OAuth](https://developer.github.com/apps/building-oauth-apps/)
 - [Thymeleaf](https://www.thymeleaf.org/)
## 工具
 - [git下载](https://git-scm.com.download)
 - [Visual-paradigm](https://www.visual-paradigm.com)
 - [Flyway](https://flywaydb.org/getstarted/firststeps/maven)
 - [Lombok](https://www.projectlombok.org)
##脚本
```sql
CREATE CACHED TABLE USER(
    ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR(50),
    TOKEN CHARACTER(36),
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT
)
```
```bash
mvn flyway:migrate
```