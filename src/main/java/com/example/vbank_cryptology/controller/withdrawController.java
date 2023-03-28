package com.example.vbank_cryptology.controller;

import com.example.vbank_cryptology.Service.withDraw;
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
public class withdrawController {
    /*** 混合加密用的公私密钥对 ***/
    public String pk;
    public String sk;

    /*** 对称加密用的密钥 ***/
    public String privateKey;

    /*** 受理的银行卡号 和取款金额***/
    public String cardNum;
    public int num;

    @Resource
    user_convert Convert;
    @Resource
    MainMapper mapper;
    @Resource
    withDraw withDraw;

    /*** 取款业务 ***/
    @RequestMapping("/doWithdraw0")
    @ResponseBody
    public String doWithdraw0() throws NoSuchAlgorithmException {
        RsaUtils.RsaKeyPair keyPair=RsaUtils.generateKeyPair();
        pk=keyPair.getPublicKey();
        sk=keyPair.getPrivateKey();
        return pk;
    }
    @RequestMapping("/doWithdraw1")
    @ResponseBody
    public String doWithdraw1(String cipher1,String mac,String cipher2) throws Exception {
        if(mac.equals(Hashcode.sha1(cipher1))){
            privateKey=RsaUtils.decryptByPrivateKey(sk,cipher2);
            cardNum= AESUtil.decrypt(cipher1,privateKey);
            return "yes";
        }else{
            return "error";
        }
    }
    @RequestMapping("/doWithdraw2")
    @ResponseBody
    public String doWithdraw2(String cipher1, String mac, String cipher2, HttpServletRequest request) throws Exception {
        if(mac.equals(Hashcode.sha1(cipher1))){
            privateKey=RsaUtils.decryptByPrivateKey(sk,cipher2);
            num=Integer.valueOf(AESUtil.decrypt(cipher1,privateKey));
            Cookie[] cookies=request.getCookies();
            String username=null;
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("username"))username= cookie.getValue();
            }
            user_account user=Convert.convert(mapper.getAccount_name_card(username, AES.encryptAES(cardNum)));
            if(user!=null){
                int oldNum=Integer.valueOf(AES.decryptAES(mapper.getBalance(username)));
                if(num>oldNum){
                    return "numError";
                }else{
                    withDraw.withdraw(num,user);
                    System.out.println(username+" 取出了 "+num+" 元钱");
                    return "yes";
                }
            }else{
                return "cardNumError";
            }
        }else{
            return "error";
        }
    }
}
