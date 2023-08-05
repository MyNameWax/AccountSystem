package cn.rzpt.common;

public enum ErrorCode {

    SUCCESS(0, "ok", ""),
    PLATFORM_NAME_EXIST(30001,"已存在",""),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    NOT_LOGIN(40100, "未登录", ""),
    ACCOUNT_EXIST_ERROR(40101, "账号已存在", ""),
    ACCOUNT_NOT_ALLOW_LOGIN(40102, "账号被封禁", ""),
    VERITY_CODE_ERROR(40103, "验证码错误", ""),
    NO_AUTH(40101, "无权限", ""),
    NOT_ALLOW_INSERT(41001,"数据过多",""),
    TASK_EXISTS(41002,"任务已存在",""),
    SYSTEM_ERROR(50000, "系统内部异常", "");

    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述（详情）
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    public String getDescription() {
        return description;
    }
}
