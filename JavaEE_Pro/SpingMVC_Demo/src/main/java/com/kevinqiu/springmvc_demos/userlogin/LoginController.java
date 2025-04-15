package com.kevinqiu.springmvc_demos.userlogin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean login(String userName, String password, HttpSession session){
        // 使用 StringUtils 类来校验参数

        System.out.println(userName);
        System.out.println(password);

        // 校验参数是否为 null
        if (!StringUtils.hasLength(userName) || !StringUtils.hasLength(password)){
            return false;
        }

        // 校验密码与用户名是否正确
        if (!"KevinQiu".equals(userName) || !"KevinQiu".equals(password)){
            return false;
        }

        // 正确则创建 session
        session.setAttribute("userName", userName);
        return true;
    }

    @RequestMapping(value = "/getLoginUser",method = RequestMethod.GET)
    public String getLoginUser(HttpServletRequest request){
        // 接收 session，但是禁用创建 session
        HttpSession session = request.getSession(false);
        if (session==null) {
            return "";
        }

        return (String) session.getAttribute("userName");
    }

}
