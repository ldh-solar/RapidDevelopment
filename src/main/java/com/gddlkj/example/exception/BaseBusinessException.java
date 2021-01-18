package com.gddlkj.example.exception;

import com.gddlkj.example.model.constants.ResponseCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author blank
 * @since 2019-1-2 16:29:00
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BaseBusinessException extends RuntimeException{

    private Integer code;

    public BaseBusinessException(ResponseCodeEnum responseCodeEnum) {
        this(responseCodeEnum.getMessage(), responseCodeEnum.getCode());
    }

    private BaseBusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }


}
