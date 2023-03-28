package com.example.vbank_cryptology.controller;

import com.example.vbank_cryptology.crypto.AES;
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
public class SignInController {
    public String username;
    public String hashcode;
    @Resource
    MainMapper mapper;
    /****** 处理注册 ******/
    @RequestMapping("/signIn")
    public String signIn(){
        return "signIn";
    }
    @RequestMapping("/doSignIn0")
    @ResponseBody
    public String doSignIn(String username, String hashcode,HttpServletResponse response) throws Exception {
        user user1=mapper.getUserByName(username);
        user user2=mapper.getUserByHash(hashcode);
        Cookie cookie1=new Cookie("username",username);
        Cookie cookie2=new Cookie("hashcode",hashcode);
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        if(user1!=null){
            return "nameError";
        }else if(user2!=null){
            return "passError";
        }else{
            this.username=username;
            this.hashcode=hashcode;
            return "yes";
        }
    }
    @RequestMapping("/doSignIn1")
    public String doSignIn1(){
        mapper.creatUser(username,hashcode);
        System.out.println("创建用户 "+username);
        return "redirect:welcome-to-vBank";
    }
    /*********/
}
