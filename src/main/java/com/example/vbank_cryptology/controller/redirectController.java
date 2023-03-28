package com.example.vbank_cryptology.controller;

import com.example.vbank_cryptology.entity.user_account;
import com.example.vbank_cryptology.mapper.MainMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class redirectController {
    @Resource
    MainMapper mapper;
    @RequestMapping("/personal")
    public String personal(){
        return "personal";
    }
    @RequestMapping("/toSave")
    public String save(){
        return "save";
    }
    @RequestMapping("/toWithdraw")
    public String toWithdraw(){
        return "withdraw";
    }
    @RequestMapping("/toTrans")
    public String toTrans(){
        return "trans";
    }
    @RequestMapping("/Service")
    public String Service(){
        return "index";
    }
    @RequestMapping("/toService")
    @ResponseBody
    public String toService(HttpServletRequest request){
        String username=null;
        String hashcode=null;
        Cookie[] cookies=request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("username"))username= cookie.getValue();
            if(cookie.getName().equals("hashcode"))hashcode= cookie.getValue();
        }
        user_account account=mapper.getAccount_name_hash(username,hashcode);
        if(account!=null){
            return "yes";
        }else{
            return "no";
        }
    }
}
