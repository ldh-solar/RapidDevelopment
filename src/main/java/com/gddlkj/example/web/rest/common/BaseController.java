package com.gddlkj.example.web.rest.common;

import com.gddlkj.example.model.constants.ResponseCodeEnum;
import com.gddlkj.example.model.dto.R;

import java.util.Date;

/**
 * @author blank
 * @since 2019-1-2 16:29:00
 */
public class BaseController {

    /**
     * 成功返回
     *
     * @param data 返回数据实体
     * @return 返回标准结构
     */
    protected <T> R<T> success(T data) {
        return new R<>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回(无数据)
     *
     * @return 返回标准结构
     */
    protected R success() {
        return new R<>(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 失败返回
     *
     * @param responseCodeEnum 异常状态枚举
     * @return 返回标准结构
     */
    protected R error(ResponseCodeEnum responseCodeEnum) {
        return new R<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage(), null);
    }

    /**
     * 失败返回打印日志时间
     *
     * @param responseCodeEnum 异常状态枚举
     * @return 返回标准结构
     */
    protected R error(ResponseCodeEnum responseCodeEnum, Long time) {
        return new R<>(responseCodeEnum.getCode(), responseCodeEnum.getMessage(), time);
    }


    /**
     * 自定义失败返回
     *
     * @param msg  异常信息
     * @param code 异常编码
     * @return 返回标准结构
     */
    protected R error(Integer code, String msg) {
        return new R<>(code, msg, new Date().getTime());
    }

    /**
     * 自定义失败返回打印日志时间
     *
     * @param msg  异常信息
     * @param code 异常编码
     * @return 返回标准结构
     */
    protected R error(Integer code, String msg, Long time) {
        return new R<>(code, msg, time);
    }
}
