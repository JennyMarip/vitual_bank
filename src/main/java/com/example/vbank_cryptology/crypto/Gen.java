package com.example.vbank_cryptology.crypto;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
/*** 生成随机的八字节 ***/
public class Gen {
    public static String generate(){
        byte[] bytes=new byte[8];
        new SecureRandom().nextBytes(bytes);
        StringBuilder result0=new StringBuilder();
        for(byte item:bytes){
            result0.append(String.format("%02x", item));
        }
        return result0.toString();
    }
}
