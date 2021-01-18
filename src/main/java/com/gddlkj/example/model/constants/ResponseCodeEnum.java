package com.gddlkj.example.model.constants;

import lombok.Getter;
import lombok.Setter;

/**
 * @author blank
 * @since 2019-1-2 16:29:00
 */
public enum  ResponseCodeEnum {

    SUCCESS(200, "操作成功"),

    ERROR(500, "系统异常"),

    ACCESSDENIED(403, "不允许访问"),

    UNAUTHORIZED(401, "没有权限"),

    USER_EXITS(1002, "用户已存在"),

    USER_NOT_EXITS(1003, "用户不存在"),

    NOT_FOUND(1004, "找不到资源"),

    EXIST_CHILDREN(1005, "该权限有下级权限,不允许删除"),

    INCORRECT_PASSWORD(1006, "密码错误"),

    PASSWORD_NOT_SAME(1007, "两次输入的密码不一致"),

    FILE_IS_NULL(1008, "文件不能为空"),

    DICTIONARY_REPEAT(1009, "数据字典重复"),

    FILE_TYPE_ERROR(10011, "不支持的文件类型"),

    VERIFY_CODE_ERROR(10012, "验证码错误"),

    USER_COUNT_ERROR(10013, "已经达到用户上限,请重新获取授权");


    @Getter
    @Setter
    private Integer code;
    @Getter
    @Setter
    private String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
