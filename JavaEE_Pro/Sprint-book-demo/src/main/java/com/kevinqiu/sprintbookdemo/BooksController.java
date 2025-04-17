package com.kevinqiu.sprintbookdemo;

import jakarta.servlet.http.HttpSession;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BooksController {
    @RequestMapping("login")
    // HttpSession 会在方法调用时会自动创建
    // HttpServletRequest 会在调用 getSession 时才创建，使用方式一样，只是时机不同
    // 但是 HttpServletRequest 能够获取更多连接的类，自由度更大
    public Integer login(String userName, String password, HttpSession session){
        // 校验用户名与密码是否为空
        if (!StringUtils.hasLength(userName) ||
            !StringUtils.hasLength(password)) {
            return -1;
        }
        // 校验用户名与密码是否正确
        if (!"admin".equals(userName) || !"admin".equals(password)) {
            return 0;
        }
        // 校验完毕，设置 session，返回 cookieID 与校验结果
        session.setAttribute("userName", "admin");
        return 1;

    }



    @RequestMapping("/test")
    // 加了@RequestBody 表示当前方法参数是依靠 json 格式数据进行赋值的
    // 如果传递的不是 json 数据会报错 400
    public Student getStudent(@RequestBody Student student){
        return student;
    }
}
