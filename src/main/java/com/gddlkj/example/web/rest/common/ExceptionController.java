package com.gddlkj.example.web.rest.common;

import com.gddlkj.example.exception.BaseBusinessException;
import com.gddlkj.example.model.constants.ResponseCodeEnum;
import com.gddlkj.example.model.dto.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


/**
 * @author blank
 * @since 2019-1-2 16:29:00
 */
@Slf4j
@ControllerAdvice
public class ExceptionController extends BaseController {

    /**
     * 处理业务异常
     */
    @ResponseBody
    @ExceptionHandler({BaseBusinessException.class})
    public R handleResourceNotFoundException(BaseBusinessException ex) {
        Long time = new Date().getTime();
        log.error(time + " 保存业务异常信息={}", ex.getMessage(), ex);
        return error(ex.getCode(), ex.getMessage(), time);
    }

    /**
     * 处理参数校验异常
     */
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return error(500, ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 处理必传参数为空异常
     */
    @ResponseBody
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public R handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return error(500, ex.getMessage());
    }

    /**
     * 403异常
     */
    @ResponseBody
    @ExceptionHandler({AccessDeniedException.class})
    public R handleAccessDeniedException() {
        return error(ResponseCodeEnum.ACCESSDENIED);
    }

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public R errorHandler(Exception ex) {
        Long time = new Date().getTime();
        log.error(time + " 保存全局异常信息={}", ex.getMessage(), ex);
        return error(ResponseCodeEnum.ERROR, time);
    }

}
