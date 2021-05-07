package org.fzu.ybk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.fzu.ybk.mapper.RichTextMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class RichTextService {

    @Autowired
    RichTextMapper richTextMapper;
    private final Logger logger = LoggerFactory.getLogger(RichTextService.class);

    public Long createRichText(String richText){
        richTextMapper.insertText(richText);
        return richTextMapper.getLastId();
    }

    public String getRichText(Long richTextId){
        return richTextMapper.getText(richTextId);
    }

    public void updateRichText(Long richTextId, String richText){
        richTextMapper.updateText(richTextId,richText);
    }

    public void updateRichText(Long richTextId, Object newObj){
        String oldInfo = this.getRichText(richTextId);
        JSONObject JsonObject = JSON.parseObject(oldInfo);
        JSONObject newJsonObject = (JSONObject) JSON.toJSON(newObj);

        for(Map.Entry<String, Object> entry: newJsonObject.entrySet()){
            if (entry.getValue() != null)
                JsonObject.put(entry.getKey(),entry.getValue());
        }
        this.updateRichText(richTextId,JSON.toJSONString(JsonObject));
    }

    public void deleteRichText(Long richTextId){
        richTextMapper.deleteText(richTextId);
    }


    public JSONArray objectListPlusRichText(List<?> obj, String attrName){
        JSONArray jsonArray = (JSONArray) JSON.toJSON(obj);
        int i = 0;
        for (Object o: jsonArray){
            JSONObject jsonObject = (JSONObject) o;
            Long richTextId = (Long) jsonObject.get("richTextId");
            String richText = richTextMapper.getText(richTextId);
            jsonObject.put(attrName,JSON.parseObject(richText));
            i++;
        }
        return jsonArray;
    }


    public JSONObject objectPlusRichText(Object obj, String attrName){
        JSONObject jsonObject = (JSONObject) JSON.toJSON(obj);
        Long richTextId = (Long) jsonObject.get("richTextId");
        String richText = richTextMapper.getText(richTextId);
        jsonObject.put(attrName,JSON.parseObject(richText));
        return jsonObject;
    }
}
