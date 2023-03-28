package com.example.vbank_cryptology.controller;

import com.example.vbank_cryptology.Service.Save;
import com.example.vbank_cryptology.crypto.*;
import com.example.vbank_cryptology.entity.user_account;
import com.example.vbank_cryptology.mapper.MainMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

@Controller
public class SaveController {
    @Resource
    Save save;
    @Resource
    MainMapper mapper;
    @Resource
    user_convert Convert;

    /*** 混合加密的公私钥对 ***/
    public String pk;
    public String sk;

    /*** 对称加密的密钥 ***/
    public String privateKey;

    /*** 存款者的银行卡号,以及存款金额 ***/
    public String cardNum;
    public int num;

    /****** 存钱业务 ******/
    @RequestMapping("/doSave0")
    @ResponseBody
    public String doSave0() throws Exception {
        RsaUtils.RsaKeyPair keyPair=RsaUtils.generateKeyPair();
        pk=keyPair.getPublicKey();
        sk=keyPair.getPrivateKey();
        return pk;
    }
    @RequestMapping("/doSave1")
    @ResponseBody
    public String doSave1(String cipher1,String mac,String cipher2) throws Exception {
        if(mac.equals(Hashcode.sha1(cipher1))){
            privateKey=RsaUtils.decryptByPrivateKey(sk,cipher2);
            cardNum= AESUtil.decrypt(cipher1,privateKey);
            System.out.println("银行卡号是:"+cardNum);
            return "yes";
        }else{
            return "error";
        }
    }
    @RequestMapping("/doSave2")
    @ResponseBody
    public String doSave2(String cipher1, String mac, String cipher2, HttpServletRequest request) throws Exception {
        if(mac.equals(Hashcode.sha1(cipher1))){
            privateKey=RsaUtils.decryptByPrivateKey(sk,cipher2);
            num=Integer.valueOf(AESUtil.decrypt(cipher1,privateKey));
            Cookie[] cookies=request.getCookies();
            String username="";
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("username"))username= cookie.getValue();
            }
            user_account user=Convert.convert(mapper.getAccount_name_card(username,AES.encryptAES(cardNum)));
            if (user==null){
                return "CardError";
            }
            save.saveMoney(num,user);
            System.out.println(username+" 存了 "+num+" 元钱");
            return "yes";
        }else{
            return "error";
        }
    }
    /*********/
}
