package com.sharp.utils.validate.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 参数校验异常
 * Created by ZhangGang on 2016/9/5.
 */
public class ParamsException extends RuntimeException {

    private static final long serialVersionUID = 276486514583932180L;

    private static Logger Log = LoggerFactory.getLogger(ParamsException.class);

    public ParamsException(String msg) {
        super(msg);
        Log.warn(msg);
    }

}
