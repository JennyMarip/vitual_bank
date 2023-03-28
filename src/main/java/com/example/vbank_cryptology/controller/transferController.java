package com.example.vbank_cryptology.controller;

import com.example.vbank_cryptology.Service.Save;
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
public class transferController {
    /*** 混合加密用的公私密钥对 ***/
    public String pk;
    public String sk;

    /*** 对称加密用的对称密钥 ***/
    public String privateKey;

    /*** 受理的银行卡号，目标账号，转账金额 ***/
    String cardNum;
    String target;
    int num;

    /*** 关联的两个账户 ***/
    user_account src;
    user_account des;

    @Resource
    user_convert Convert;
    @Resource
    MainMapper mapper;
    @Resource
    withDraw withDraw;
    @Resource
    Save save;

    @RequestMapping("/doTrans0")
    @ResponseBody
    public String doTrans0() throws NoSuchAlgorithmException {
        RsaUtils.RsaKeyPair keyPair=RsaUtils.generateKeyPair();
        pk=keyPair.getPublicKey();
        sk=keyPair.getPrivateKey();
        return pk;
    }
    @RequestMapping("/doTrans1")
    @ResponseBody
    public String doTrans1(String cipher1, String mac, String cipher2, HttpServletRequest request) throws Exception {
        if(mac.equals(Hashcode.sha1(cipher1))){
            privateKey=RsaUtils.decryptByPrivateKey(sk,cipher2);
            cardNum= AESUtil.decrypt(cipher1,privateKey);
            Cookie[] cookies=request.getCookies();
            String username=null;
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("username"))username= cookie.getValue();
            }
            src=Convert.convert(mapper.getAccount_name_card(username, AES.encryptAES(cardNum)));
            if(src==null){
                return "cardNumError";
            }else{
                return "yes";
            }
        }else{
            return "error";
        }
    }
    @RequestMapping("/doTrans2")
    @ResponseBody
    public String doTrans2(String cipher1,String mac,String cipher2) throws Exception {
        if(mac.equals(Hashcode.sha1(cipher1))){
            privateKey=RsaUtils.decryptByPrivateKey(sk,cipher2);
            target=AESUtil.decrypt(cipher1,privateKey);
            des=Convert.convert(mapper.getAccountByName(target));
            if(des!=null){
                return "yes";
            }else{
                return "targetError";
            }
        }else{
            return "error";
        }
    }
    @RequestMapping("/doTrans3")
    @ResponseBody
    public String doTrans3(String cipher1,String mac,String cipher2) throws Exception {
        if(mac.equals(Hashcode.sha1(cipher1))){
            privateKey=RsaUtils.decryptByPrivateKey(sk,cipher2);
            num=Integer.valueOf(AESUtil.decrypt(cipher1,privateKey));
            int oldNum=Integer.valueOf(AES.decryptAES(mapper.getBalance(src.username)));
            if(num>oldNum){
                return "numError";
            }else{
                withDraw.withdraw(num,src);
                save.saveMoney(num,des);
                System.out.println(src.username+" 向 "+des.username+" 转账 "+num+" 元钱");
                return "yes";
            }
        }else{
            return "error";
        }
    }
}


