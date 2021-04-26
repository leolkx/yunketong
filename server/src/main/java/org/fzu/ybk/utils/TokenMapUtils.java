package org.fzu.ybk.utils;

import org.fzu.ybk.GlobalConstant;
import org.fzu.ybk.exception.TokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenMapUtils {

    private static Map<Long, JSONObject> userMapToken = new HashMap<Long,JSONObject>();
    private static Map<String, Long> tokenMapUser = new HashMap<String, Long>();
    private static Map<String, Date> tokenExpiryTime = new HashMap<String, Date>();

    private final Logger logger = LoggerFactory.getLogger(TokenMapUtils.class);

    public Long getUserIdByToken(String token){
        return tokenMapUser.get(token);
    }

    public void verifyToken(String token, HttpServletRequest request) throws Exception{
        //1,tokenMapUser存在映射，
        if (! tokenMapUser.containsKey(token))
            throw new TokenException("无效的token(1)");

        Long userId = tokenMapUser.get(token);
        DeviceType deviceType = this.getDeviceType(request);

        // userMapToken不存在但是 tokenMapUser存在，已经存在bug了.
        if (! userMapToken.containsKey(userId)) {
            logger.error("tokenMapUser与userMapToken不匹配");
            tokenMapUser.remove(token);
            tokenExpiryTime.remove(token);
            throw new TokenException("无效的token(2)");
        }

        JSONObject jsonObject = userMapToken.get(userId);
        if (! jsonObject.has(deviceType.getName()) )
            throw new TokenException("无效的token(3)");

        //2,token与userMap下对应的device_Token一致(存在)
        if ( ! jsonObject.get(deviceType.getName()).equals(token) )
            throw new TokenException("无效的token(4)");

        //3,token未过期
        Date date = new Date();
        Date expiryTime = tokenExpiryTime.get(token);

        if (date.compareTo(expiryTime) >= 0){
            this.removeToken(token,request);
            throw new TokenException("无效的token(5) token已过期");
        }
//        System.out.println("5");
        //1,tokenMapUser存在映射，
        //2,token与userMap下对应的deviceToken一致
        //3,token未过期
        //才认为验证成功.

//            JSONObject jsonObject = userMapToken.get(userId);
//            DeviceType deviceType = this.getDeviceType(request);
//            if (! jsonObject.has(deviceType.getName())) return false;
//            return  jsonObject.get(deviceType.getName()).equals(request.getSession().getId());


    }

    public void updateToken(Long userId, String token, HttpServletRequest request){
        try{
            JSONObject jsonObject;
            DeviceType deviceType = this.getDeviceType(request);

            if (userMapToken.containsKey(userId)) {
                jsonObject = userMapToken.get(userId);

                //删除旧的tokenMapUser缓存
                if (jsonObject.has(deviceType.getName())){
                    String deletedToken = (String) jsonObject.get(deviceType.getName());
                    System.out.println(deletedToken);
                    tokenMapUser.remove(deletedToken);
                    tokenExpiryTime.remove(deletedToken);
                }
            }
            else{
                jsonObject = new JSONObject();
            }

            //更新userMapToken缓存
            jsonObject.put(deviceType.getName(),token);
            userMapToken.put(userId,jsonObject);

            //更新tokenMapUser缓存
            tokenMapUser.put(token,userId);
            Date date = new Date();
            date.setTime(date.getTime() + GlobalConstant.tokenExpiryTime);
            tokenExpiryTime.put(token,date);

        } catch (JSONException e) {
            logger.error(e.toString());
        }
    }

    public void removeToken(String token, HttpServletRequest request){
        try{
            if (! tokenMapUser.containsKey(token)) return;

            //从token缓存中得到userId
            Long userId = tokenMapUser.get(token);

            //删除tokenMapUser的缓存
            tokenMapUser.remove(token);
            tokenExpiryTime.remove(token);

            if (! userMapToken.containsKey(userId))
                return ;

            // 根据userId从缓存获取该id下的token表
            JSONObject jsonObject = userMapToken.get(userId);

            int devCount = 0;
            for(DeviceType deviceType1 : DeviceType.values()){
                if (jsonObject.has(deviceType1.getName())) {
                    devCount++;
                    //若userMaptoken中存在与待删除token相同的领牌
                    if ( jsonObject.get(deviceType1.getName()).equals(token) ){
                        jsonObject.remove(deviceType1.getName());
                        devCount -= 1;
                    }
                }
            }
            // 当前userId下无有效的token表，删除该userMap; 否则更新userMapToken
            if (devCount == 0) userMapToken.remove(userId);
            else userMapToken.put(userId,jsonObject);

        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    public DeviceType getDeviceType(HttpServletRequest request){
        LiteDeviceResolver deviceResolver = new LiteDeviceResolver();
        Device device = deviceResolver.resolveDevice(request);
        DeviceType deviceType;
        if (device.isMobile()){ deviceType = DeviceType.MOBILE; }
        else if (device.isTablet()){ deviceType = DeviceType.TABLET; }
        else if (device.isNormal()){  deviceType = DeviceType.WEB; }
        else{ deviceType = DeviceType.WEB; }
        return deviceType;
    }


}
