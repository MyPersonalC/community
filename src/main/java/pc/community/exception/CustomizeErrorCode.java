package pc.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你找的问题失踪了，换个试试呗~"),
    TARGET_PARAM_NOT_FOUND(2002,"选中任何问题或评论再进行回复撒~"),
    NO_LOGIN(2003,"别急嘛，登录一下再试咯~"),
    SYSTEM_ERROR(2004,"服务太火爆嘞，等下再试咯~"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"你回复的评论失踪了，换个试试呗~"),
    CONTENT_IS_EMPTY(2007,"你的评论为空嘞，先评价一下呗~"),
    READ_NOTIFICATION_FAIL(2008,"这不是你的信息偶(⊙o⊙)？"),
    NOTIFICATION_NOT_FOUND(2009,"没找到你刚刚的消息呢~"),
    UPLOAD_FILE_FAIL(2010,"你要上传的服务器开小差了呢，等等再试喔~"),
    UPLOAD_FILE_NOT_FOUND(2011,"你上传的文件未找到，重新试试呗~");
    private String message;
    private Integer code;
    CustomizeErrorCode(Integer code, String message){
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
