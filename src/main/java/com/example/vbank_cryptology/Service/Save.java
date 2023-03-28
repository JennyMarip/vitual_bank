package com.example.vbank_cryptology.Service;

import com.example.vbank_cryptology.crypto.AES;
import com.example.vbank_cryptology.entity.user_account;
import com.example.vbank_cryptology.mapper.MainMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Save {
    @Resource
    MainMapper mapper;
    public void saveMoney(int num, user_account user) throws Exception {
        int oldNum=Integer.valueOf(AES.decryptAES(mapper.getBalance(user.getUsername())));
        int newNum=oldNum+num;
        mapper.set(user.getUsername(), AES.encryptAES(String.valueOf(newNum)));
    }
}
