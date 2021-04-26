package org.fzu.ybk.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/5/19 0:55
 */
@Component
public class StringVerifier {

    public Boolean verifyUserName(String username){

        String pattern = "^[a-zA-Z][a-zA-Z0-9_]{2,17}$";
        return Pattern.matches(pattern,username);

//        int res = username.indexOf(' ');
//        if (res != -1)
//            return Boolean.FALSE;
//        if (username.length() < 3)
//            return Boolean.FALSE;
//
//        return Boolean.TRUE;
    }
}
