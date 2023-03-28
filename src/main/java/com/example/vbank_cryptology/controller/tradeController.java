package com.example.vbank_cryptology.controller;

import com.example.vbank_cryptology.Service.Save;
import com.example.vbank_cryptology.Service.withDraw;
import com.example.vbank_cryptology.crypto.*;
import com.example.vbank_cryptology.entity.user_account;
import com.example.vbank_cryptology.mapper.MainMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin
public class tradeController {
    @Resource
    user_convert convert;
    @Resource
    MainMapper mapper;
    @Resource
    Save save;
    @Resource
    withDraw withdraw;
    @RequestMapping ("/confirm")
    @CrossOrigin
    @ResponseBody
    public String confirm(String totalCost,
                          String userNumber,
                          String userPassword,
                          String payee,
                          String HOI,
                          String signHOP,
                          String encryptKey) throws Exception {
        String HPI=Hashcode.sha1(totalCost+userNumber+userPassword+payee);
        if(RsaUtils.decryptByPrivateKey(keyPair.sk,signHOP).equals(Hashcode.sha1(HOI+HPI))) {
            String privateKey = RsaUtils.decryptByPrivateKey(keyPair.sk, encryptKey);
            String cost = AESUtil.decrypt(totalCost, privateKey);
            System.out.println("总金额是：" + cost);
            String cardNum_client = AESUtil.decrypt(userNumber, privateKey);
            String password_client = AESUtil.decrypt(userPassword, privateKey);
            System.out.println("顾客卡号:" + cardNum_client);
            System.out.println("顾客密码:" + password_client);
            String cardNum_shop = AESUtil.decrypt(payee, privateKey);
            System.out.println("商家卡号：" + cardNum_shop);
            user_account cli=convert.convert(mapper.getAccount_card(AES.encryptAES(cardNum_client)));
            user_account shop=convert.convert(mapper.getAccount_card(AES.encryptAES(cardNum_shop)));
            if(cli==null||shop==null){
                System.out.println("顾客或商家银行卡号错误");
                return "cardNum_error";
            }
            int num=Integer.valueOf(cost);
            int oldNum=Integer.valueOf(AES.decryptAES(mapper.getBalance_cardNum(AES.encryptAES(cardNum_client))));
            System.out.println("顾客余额:"+oldNum);
            if(num>oldNum){
                System.out.println("余额不足");
                return "balance_error";
            }
            save.saveMoney(num,shop);
            withdraw.withdraw(num,cli);
            System.out.println(cli.username+" ");
            return "yes";
        }else{
            System.out.println("no");
            return "no";
        }
    }
    @RequestMapping("/confirm2")
    @CrossOrigin
    public String confirm2(){
        System.out.println(123);
        return "ack";
    }

}
