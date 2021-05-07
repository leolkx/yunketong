package org.fzu.ybk.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/4/25 13:48
 */
@Component
public class DateFormater {

    public String getDate(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
        return dateStr;
    }
}
