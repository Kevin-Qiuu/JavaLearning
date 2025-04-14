package com.kevinqiu.demo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController// 为了使 SpringBoot 明确有哪些类的方法需要路径映射
@RequestMapping("/request")
public class RequestController {
    @RequestMapping("/world")
    public String hello() {
        String ret = "<h1>Hello, world</h1><br>";
        String ret_2 = "<input type=\"button\" value=\"Hello world\">";
        System.out.println(ret + ret_2);
        return ret + ret_2;
    }

    @RequestMapping("/getName")
    public String getName(@RequestParam(required = false) String name) {
        if (name == null)
            name = "KevinQiu";
        return "<h1>" + name + "</h1>";
    }

    @RequestMapping("/login")
    public String loginAccount(String userName, String passwd, Integer age) {
        // 方法中有多个参数的，既可以通过 url 传参也可以通过Form 表单传参
        Person person = new Person(userName, age, passwd);
        return person.toString();
    }

    @RequestMapping("/search")
    public String search(@RequestParam("q") String query) {
        return "query: " + query;
    }

    @RequestMapping("/makePerson")
    public String makePerson(@RequestBody(required = false) Person person) {
        // @RequestBody：这个注解用于接收并处理 JSON 数据，可以通过 JSON 传参
        return "<h1>" + person + "</h1>";
    }


    // 使用 url 传递参数，不使用键值对
    @RequestMapping("/article/{ArticleId}/{ArticleType}")
    public String getArticleInfo(@PathVariable("ArticleId") String ArticleId,
                                 @PathVariable("ArticleType") String ArticleType) {
        return "ArticleId: " + ArticleId + " ArticleType: " + ArticleType;
    }


    // 获取用户上传文件
    // @RequestPart  MultipartFile
    @RequestMapping("getFile")
    public String getFile(@RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        file.transferTo(new File("/Users/kevinqiu/Downloads/Java_save_data/"+originalFilename));
        return "Get file successfully and save in " + "~/download/java_save_data" + originalFilename;
    }

    // 获取用户 cookie
    @RequestMapping("getCookie")
    public String getCookie(HttpServletRequest request){
        // todo
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            System.out.println(cookie.getName() + ":" + cookie.getValue());
        }
        return "Show the data in the controller...";
    }


    // 设置客户端的 Cookie，如果客户端没有 CookieId 则服务器会创建一个 Session，然后发送响应为客户端创建 Cookie
    // 每个用户都会有一个自己当前的 cookie 作为通信令牌，在服务器端有一个 session 存放着用户全部信息
    // cookie 与 session 之间使用过 sessionID 进行一一对应
    @RequestMapping("/setSession")
    public String setSession(HttpServletRequest request) {
        HttpSession session = request.getSession(); // 如果没有则向客户端发送对应的 cookieId
        // 创建用户对应 session
        session.setAttribute("name", "KevinQiu");
        session.setAttribute("passwd", "12345678");
        session.setAttribute("age", "23");
        return "set SessionId successfully!";
    }

    // 根据用户的 cookieId 匹配对应的 session
    @RequestMapping("/getSessionInfo")
    public String getSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        // SpringBoot 会自动获取用户的 CookieId 然后与服务器中的 session 匹配
        String name = (String) session.getAttribute("name");
        String passwd = (String) session.getAttribute("passwd");
        String age = (String) session.getAttribute("age");
        return "name: " + name + " passwd: " + passwd + " age: " + age;
    }

    // 获取头信息中的 host，使用 HttpServletRequest
    @RequestMapping("getHost")
    public String getHost(HttpServletRequest request){
        String host = request.getHeader("Host");
        return "Current host: " + host;
    }

    // 设置头信息中的 Content-type，使其返回给前端的值为 json




}
