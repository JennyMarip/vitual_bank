package com.example.vbank_cryptology.crypto;

import com.example.vbank_cryptology.entity.user_account;
import org.springframework.stereotype.Component;

@Component
public class user_convert {
    public user_account convert(user_account user) throws Exception {
        if(user==null)return null;
        user_account res=new user_account();
        res.balance=AES.decryptAES(user.balance);
        res.cardNum=AES.decryptAES(user.cardNum);
        res.username=user.username;
        res.id= user.id;
        res.hashcode= user.hashcode;
        return res;
    }
}
