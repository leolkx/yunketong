package org.fzu.ybk.exception;

public class VerificationCodeException extends Exception {
    public VerificationCodeException(){
        super();
    }

    public VerificationCodeException(String msg){
        super(msg);
    }
}
