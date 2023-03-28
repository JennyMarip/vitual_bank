package com.example.vbank_cryptology.controller;

import com.example.vbank_cryptology.crypto.AES;
import com.example.vbank_cryptology.crypto.AESUtil;
import com.example.vbank_cryptology.crypto.Hashcode;
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
public class CreateAccountController {
    /*** 混合加密用的公私钥对 ***/
    public String pk;
    public String sk;

    /*** 对称加密用的对称密钥 ***/
    public String privateKey;
    @Resource
    MainMapper mapper;
    @RequestMapping("/toCreateAccount")
    public String toCreateAccount(){
        return "CreateAccount";
    }
    @RequestMapping("/CreateAccount0")
    @ResponseBody
    public String CreateAccount0() throws NoSuchAlgorithmException {
        RsaUtils.RsaKeyPair keyPair= RsaUtils.generateKeyPair();
        pk=keyPair.getPublicKey();
        sk=keyPair.getPrivateKey();
        return pk;
    }
    @RequestMapping("/CreateAccount1")
    @ResponseBody
    public String CreateAccount1(String cipher1,String mac,String cipher2,String username, String hashcode, HttpServletRequest request) throws Exception {
        if(mac.equals(Hashcode.sha1(cipher1))) {
            privateKey=RsaUtils.decryptByPrivateKey(sk,cipher2);
            String cardNum= AESUtil.decrypt(cipher1,privateKey);
            String username0 = null;
            String hashcode0 = null;
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) username0 = cookie.getValue();
                if (cookie.getName().equals("hashcode")) hashcode0 = cookie.getValue();
            }
            if (!username.equals(username0)) {
                return "nameError";
            } else if (!hashcode.equals(hashcode0)) {
                return "passError";
            } else {
                user_account user = mapper.getAccount_card(AES.encryptAES(cardNum));
                System.out.println(user);
                if (user != null) {
                    return "cardError";
                } else {
                    mapper.creatAccount(username, AES.encryptAES("0"), AES.encryptAES(cardNum), hashcode);
                    System.out.println(username+" 创建了账户, 银行卡号是 "+cardNum);
                    return "yes";
                }
            }
        }else{
            return "error";
        }
    }
}
