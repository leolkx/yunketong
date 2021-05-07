package org.fzu.ybk.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class RandomCodeUtils {

    private static final char[] CODE_SEQ = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };

    private static final char[] NUMBER_ARRAY = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    private static Random random = new Random();

    private final Logger logger = LoggerFactory.getLogger(RandomCodeUtils.class);

//    public String randomNumber(int length) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            sb.append(String.valueOf(NUMBER_ARRAY[random.nextInt(NUMBER_ARRAY.length)]));
//        }
//        return sb.toString();
//    }

    public String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(String.valueOf(CODE_SEQ[random.nextInt(CODE_SEQ.length)]));
        }
        return sb.toString();
    }

    public String randomNumberString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(String.valueOf(NUMBER_ARRAY[random.nextInt(NUMBER_ARRAY.length)]));
        }
        return sb.toString();
    }



}
