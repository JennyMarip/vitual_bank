package com.example.vbank_cryptology.controller;

import com.example.vbank_cryptology.Service.Save;
import com.example.vbank_cryptology.Service.withDraw;
import com.example.vbank_cryptology.crypto.*;
import com.example.vbank_cryptology.entity.*;
import com.example.vbank_cryptology.mapper.MainMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

@Controller
public class MainController {//注意这个controller类一定要在主类之下！
    /*** 此次登录对称加密的密钥 ***/
    public String privateKey;

    /*** 登录时使用的公私密钥对 ***/
    public String pk;
    public String sk;

    /*** 八字节质询 ***/
    String inquiry;
    @Resource
    MainMapper mapper;
    @Resource
    Save save;
    @Resource
    withDraw withdraw;
    @Resource
    user_convert Convert;

    /****** 初始登录界面 ******/
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    /******  ******/

    /****** 登陆后的初始界面 ******/
    @RequestMapping("/welcome-to-vBank")
    public String welcome(HttpServletRequest request){
        user user=null;
        Cookie[] cookies=request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("username"))user=mapper.getUserByName(cookie.getValue());
        }
        if(user!=null){
            return "pre_index";
        }else{
            return "redirect:login";
        }
    }
    /****** ******** ******/

    /****** 获取用户当前余额 ******/
    @RequestMapping("/getBalance")
    @ResponseBody
    public Num getBalance(HttpServletRequest request) throws Exception {
        String username=null;
        Cookie[] cookies= request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("username"))username=cookie.getValue();
        }
        int balance=Integer.valueOf(AES.decryptAES(mapper.getBalance(username)));
        Num num=new Num(balance);
        return num;
    }
    /*********/

}
