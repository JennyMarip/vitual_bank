package com.example.vbank_cryptology.Service;

import com.example.vbank_cryptology.crypto.AES;
import com.example.vbank_cryptology.entity.user_account;
import com.example.vbank_cryptology.mapper.MainMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class withDraw {
    @Resource
    MainMapper mapper;
    public void withdraw(int num, user_account user) throws Exception {
        int oldNum=0;
        if(user!=null) {
            oldNum = Integer.valueOf(AES.decryptAES(mapper.getBalance(user.getUsername())));
        }
        int newNum=oldNum-num;
        if(user!=null) {
            mapper.set(user.getUsername(), AES.encryptAES(String.valueOf(newNum)));
        }
    }
}
