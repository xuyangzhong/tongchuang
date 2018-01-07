package com.tongchuang.customException;

/**
 * @Description:
 * @Author: Yeliang
 * @Date: Create in 17:55 2018/1/7
 */
public class NewsOperationException extends RuntimeException{
    public NewsOperationException(String message){
        super(message);
    }
    public NewsOperationException(){
        super();
    }
}
