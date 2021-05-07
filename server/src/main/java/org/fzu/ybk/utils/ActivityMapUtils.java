package org.fzu.ybk.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/5/12 19:58
 */
@Component
public class ActivityMapUtils {

    private static Map<String, JSONObject> SessionMap = new HashMap<String,JSONObject>();
    private final Logger logger = LoggerFactory.getLogger(ActivityMapUtils.class);


}
