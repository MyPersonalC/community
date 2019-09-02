## PC社区

## 资料
 - [Spring 文档](https://spring.io/guides)
 - [spring boot的使用](https://spring.io/guides/gs/serving-web-content/) 
 - [spring boot依赖](https://elasticsearch.cn/explore)
 - [ssh连接GitHub](https://help.github.com/en/articles/connecting-to-github-with-ssh)
 - [Bootstrap 文档](https://v3.bootcss.com/getting-started/)
 - [Github OAuth](https://developer.github.com/apps/building-oauth-apps/)
 - [Thymeleaf](https://www.thymeleaf.org/)
 - [Tencent SDK Demo](https://github.com/tencentyun/cos-java-sdk-v5/tree/master/src/main/java/com/qcloud/cos/demo)
## 工具
 - [git下载](https://git-scm.com.download)
 - [Visual-paradigm](https://www.visual-paradigm.com)
 - [Flyway](https://flywaydb.org/getstarted/firststeps/maven)
 - [Lombok](https://www.projectlombok.org)
 - [markdown工具](https://pandao.github.io/editor.md/)
 - [tencent SDK](https://cloud.tencent.com/document/product/436/10199)
## 脚本
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
```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```