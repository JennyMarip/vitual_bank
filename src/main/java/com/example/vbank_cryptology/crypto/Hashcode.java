package com.example.vbank_cryptology.crypto;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashcode {
    public static String sha1(String text) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        byte[] bytes = messageDigest.digest(text.getBytes());
        return Hex.encodeHexString(bytes);
    }
}
