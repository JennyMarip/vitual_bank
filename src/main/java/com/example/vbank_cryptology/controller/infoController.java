package com.example.vbank_cryptology.controller;

import com.example.vbank_cryptology.crypto.AES;
import com.example.vbank_cryptology.crypto.AESUtil;
import com.example.vbank_cryptology.crypto.RsaUtils;
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
public class infoController {
    /*** 公私密钥对 ***/
    public String pk;
    public String sk;

    /*** 对称加密的密钥 ***/
    public String privateKey;

    @Resource
    MainMapper mapper;
    @RequestMapping("/handler0")
    @ResponseBody
    public String handler0() throws NoSuchAlgorithmException {
        RsaUtils.RsaKeyPair keyPair=RsaUtils.generateKeyPair();
        pk=keyPair.getPublicKey();
        sk=keyPair.getPrivateKey();
        return pk;
    }
    @RequestMapping("/handler1")
    @ResponseBody
    public user_account handler1(String cipher, HttpServletRequest request) throws Exception {
        if(cipher!=null&&sk!=null) {
            privateKey = RsaUtils.decryptByPrivateKey(sk,cipher);
            Cookie[] cookies = request.getCookies();
            String username = "";
            String balance = "";
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) username = cookie.getValue();
            }
            balance = AES.decryptAES(mapper.getBalance(username));
            System.out.println(username+" 查询了自己的当前信息，他的余额是"+balance);
            balance = AESUtil.encrypt(balance, privateKey);
            user_account user = new user_account();
            user.setBalance(balance);
            user.setUsername(username);
            return user;
        }else{
            return null;
        }
    }
}
