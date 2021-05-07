package org.fzu.ybk.exception;

public class MailVerificationException extends Exception{
    public MailVerificationException(){
        super();
    }

    public MailVerificationException(String msg){
        super(msg);
    }
}
