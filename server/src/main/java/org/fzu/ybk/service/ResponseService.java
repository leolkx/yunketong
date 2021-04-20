package org.fzu.ybk.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.boot.configurationprocessor.json.JSONException;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ResponseService {

    private final Logger logger = LoggerFactory.getLogger(ResponseService.class);

    public String responseFactory(String state,String msg, Object result){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("state",state);
            jsonObject.put("msg",msg);
//            jsonObject.put("result",JSON.toJSON(result));
            jsonObject.put("result",result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String responseFactory(String state,String msg){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("state",state);
            jsonObject.put("msg",msg);
//            jsonObject.put("result",result);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
