package com.athubcloud.pojo;

public class BussinessException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BussinessException(String msg) {
        super(msg);
    }

    public BussinessException(Throwable throwable) {
        super(throwable);
    }

    public BussinessException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
