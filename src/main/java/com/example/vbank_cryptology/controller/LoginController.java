package com.example.vbank_cryptology.controller;

import com.example.vbank_cryptology.crypto.Gen;
import com.example.vbank_cryptology.crypto.Hashcode;
import com.example.vbank_cryptology.entity.user;
import com.example.vbank_cryptology.mapper.MainMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    /*** 八字节质询 ***/
    public String inquiry;
    /*** 口令散列值 ***/
    public String hashcode;
    @Resource
    MainMapper mapper;
    /****** 用户登录认证 ******/
    @RequestMapping("/authentication0")
    @ResponseBody
    public String Auth0(String username,HttpServletResponse response) throws Exception {
        System.out.println(username+" 想要进行登录");
        user user=mapper.getUserByName(username);
        if(user!=null){
            hashcode=user.getHashcode();
            inquiry=Gen.generate();
            Cookie cookie1=new Cookie("username",username);
            Cookie cookie2=new Cookie("hashcode",hashcode);
            response.addCookie(cookie1);
            response.addCookie(cookie2);
            return inquiry;
        }else{
            return "error";
        }
    }
    @RequestMapping("/authentication1")
    @ResponseBody
    public String Auth1(String result) throws Exception {
        String expect=Hashcode.sha1(hashcode+inquiry);
        if(result.equals(expect)){
            System.out.println("登录成功");
            return "yes";
        }else{
            return "no";
        }
    }

    /*********/
}
