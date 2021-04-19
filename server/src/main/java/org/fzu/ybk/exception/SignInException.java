package org.fzu.ybk.exception;

public class SignInException extends Exception {
    public SignInException(){
        super();
    }

    public SignInException(String msg){
        super(msg);
    }
}
