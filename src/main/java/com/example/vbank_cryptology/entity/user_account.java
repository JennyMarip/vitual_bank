package com.example.vbank_cryptology.entity;

import lombok.Data;

@Data
public class user_account {
    public int id;
    public String username;
    public String password;
    public String balance;
    public String cardNum;
    public String hashcode;
}
