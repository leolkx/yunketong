package org.fzu.ybk.service;

import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/4/16 20:53
 */

@Service
public class OnlineActivityService {

//    private static Map<Long, JSONObject> classActivityMap = new HashMap<String,JSONObject>();
//    private final Logger logger = LoggerFactory.getLogger(OnlineActivityService.class);
//
//    public void createClassActivity(String userName, HttpServletRequest request){
//        try{
//
//        } catch (Exception e) {
//            logger.error(e.toString());
//        }
//    }
//
//    public void attendClassActivity(String userName, HttpServletRequest request){
//        try{
//
//        } catch (Exception e) {
//            logger.error(e.toString());
//        }
//    }
//
//    public void attendClassActivity(String userName, HttpServletRequest request){
//        try{
//
//        } catch (Exception e) {
//            logger.error(e.toString());
//        }
//    }
//
//
//    public void updateSession(String userName, HttpServletRequest request){
//        try{
//            JSONObject jsonObject;
//            if (SessionMap.containsKey(userName)) { jsonObject = SessionMap.get(userName); }
//            else{ jsonObject = new JSONObject(); }
//
//            jsonObject.put(deviceType.getName(),request.getSession().getId());
//            SessionMap.put(userName,jsonObject);
//
//        } catch (JSONException e) {
////            e.printStackTrace();
//            logger.error(e.toString());
//        }
//    }
//
//    public void removeSession(String userName, HttpServletRequest request){
//        try{
//            if (! SessionMap.containsKey(userName)) return;
//            JSONObject jsonObject = SessionMap.get(userName);
//
//            DeviceType deviceType = this.getDeviceType(request);
//
//            if (jsonObject.has(deviceType.getName())) {
//                jsonObject.remove(deviceType.getName());
//                int devCount = 0;
//                for(DeviceType deviceType1 : DeviceType.values()){
////                    System.out.println(deviceType1.getName());
//                    if (jsonObject.has(deviceType1.getName())) devCount++;
//                }
//                if (devCount == 0) SessionMap.remove(userName);
//            }
//        } catch (Exception e) {
////            e.printStackTrace();
//            logger.error(e.toString());
//        }
//    }
}
