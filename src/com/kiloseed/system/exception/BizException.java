package com.kiloseed.system.exception;

/**
 * Created by mac on 15/7/23.
 */
public class BizException extends  RuntimeException {
    public BizException(){
        super();
    }
    public BizException(String msg){
        super(msg);
    }
}
