package org.fzu.ybk.controller;


import org.fzu.ybk.service.admin.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.LiteDeviceResolver;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import sun.jvm.hotspot.debugger.Page;

import javax.servlet.http.HttpServletRequest;


//@Controller
//@ResponseBody
@RestController
@CrossOrigin
public class TestController {


    @Autowired
    private RoleService roleService;



//    @RequestMapping("/getUserList")
//    public List<User> getUserList(){
//        return userService.getUserList();
//    }


//    @RequestMapping("/getRoleList")
//    public List<Role> getRoleList(Model model){
//        return roleService.getRoleList();
//    }

    @RequestMapping("device")
    public String index(HttpServletRequest request){
//        String info = "用户: " + request.getSession().getId() + "， 欲访问: "+request.getRequestURI();

        LiteDeviceResolver deviceResolver = new LiteDeviceResolver();
        Device device = deviceResolver.resolveDevice(request);

        String device_name ;
        String platform = device.getDevicePlatform().toString();

        if (device.isMobile()){ device_name = "手机"; }
        else if (device.isTablet()){ device_name = "平板电脑"; }
        else if (device.isNormal()){  device_name = "PC端"; }
        else{ device_name = "未知的终端"; }

        return "访问设备:" + device_name + ", 访问平台:" + platform;
    }


}