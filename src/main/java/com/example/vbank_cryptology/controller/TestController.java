package com.example.vbank_cryptology.controller;

import com.example.vbank_cryptology.crypto.AESUtil;
import com.example.vbank_cryptology.crypto.Hashcode;
import com.example.vbank_cryptology.crypto.HybridDecrypt;
import com.example.vbank_cryptology.crypto.RsaUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestController {

    public  String privateKey= "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIGNeblYs5reAUZq43H63ycnSR/lZxxHH2oPW8v9tfYZFrPc3QkTFzaLLSg6kMyfY2NukwI8Fg9TVbY4JQWHeZis5Yq7EEY9vvL51fMcZS3Y5ygIBvytaUwjKBCj9XZssq2RmKWckmd6yMxhBBYWdvnmfG7XyEetKj0UzZhANbxhAgMBAAECgYBKC+SwzUQKli1AZEOPmAYHyOqAsouWnAgWhKTBGUfxWzGgp/ImB6VS5YVv4tl0Ov2BjWe09Ubeh5ELz1dJq78RdL69IBqJdvaQ55SW+ZUrugUueoKYjY5pRRzANBlxYLpVGHebSTTAhd/1I6Vl44hGiV0mzrdGUz+HJj/E6kT4aQJBAM113qSOwdLaTWPBVQiwQ+xvTceG+oflSGJcRPG659joPcMLbGhWsiGT7slBh+UHJPINV2VjwF0zch5J4qEKNtsCQQCha5YP3YKaYLfOOJptID6iQUGvRuu+Gxao/DN2dUtvjqM8idXXU8Rds0CqdHu77Si+/d+Agwekx2oixGwqjMhzAkAroiKcU+z5uH7C9qX2aGikZ6be/t1pytmqeenyZD0kpX2oVF67cd32n5IQj6gqjW/dL9Qlph+OK4TKxeopRSANAkEAnddqAVGE6BgzI3/N4W9qT5an+BDNbDNo0Qzr9aV0gj1j+Up+w/OxTD5/uS314Cljcn8lEbEOxF4KtryDWIgZaQJAOcfeOfLw5kErDLHXqrzcRCpk2UiBVDZ3VFVb+hRA2gnY7X7VLT8zEmlsEbU15iB1EnojRbnRL801KI/0TEFt/Q==";
    public  String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBjXm5WLOa3gFGauNx+t8nJ0kf5WccRx9qD1vL/bX2GRaz3N0JExc2iy0oOpDMn2NjbpMCPBYPU1W2OCUFh3mYrOWKuxBGPb7y+dXzHGUt2OcoCAb8rWlMIygQo/V2bLKtkZilnJJnesjMYQQWFnb55nxu18hHrSo9FM2YQDW8YQIDAQAB";
    @RequestMapping("/a")
    public String a(){
        return "a";
    }
    @PostMapping("/test")
    @ResponseBody
    public String testRsa(String cipher1,String mac,String cipher2) throws Exception {
        if(HybridDecrypt.verify(cipher1,mac)){
            HybridDecrypt.setPrivateKey(privateKey);
            String plainText=HybridDecrypt.decrypt(cipher1,cipher2);
            System.out.println(plainText);
            return "yes";
        }else{
            return "no";
        }
    }
}

