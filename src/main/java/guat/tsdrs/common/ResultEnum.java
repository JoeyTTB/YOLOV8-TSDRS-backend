package guat.tsdrs.common;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(1, "成功"),
    ERROR(0, "错误"),
    ACCOUNT_NOT_FOUND(2, "账户不存在"),
    USER_LOGIN_FAILED(3, "账号或密码错误"),
    USER_LOGOUT_SUCCESS(4, "登出成功"),
    LOGIN_SUCCESS(5, "登录成功"),
    USER_NEED_AUTH(6, "您还没有登录"),
    USER_HAD_EXIST(7, "账户已被使用，换一个试试吧"),
    INCORRECT_PASSWORD_CHECK(8, "两次密码不一致"),
    REGISTER_SUCCESS(9, "注册成功"),
    REGISTER_FAILED(10, "注册失败，请重试"),
    TOKEN_EXPIRED(11, "登录过期，请重新登录"),
    USER_HAS_LOGGED(12, "该账户已在别处登录"),
    OLD_PASSWORD_ERROR(13, "原始密码输入错误"),
    ILLEGAL_OPERATION(14, "非法操作"),
    USER_LACK_AUTH(15, "权限不足，无法访问"),
    ;

    private int code;
    private String msg;
    private ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
