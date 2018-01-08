package com.tongchuang.customException;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 17:55 2018/1/7
 */
public class MessageOperationException extends RuntimeException{
    public MessageOperationException(String message){
        super(message);
    }
    public MessageOperationException(){
        super();
    }
}
