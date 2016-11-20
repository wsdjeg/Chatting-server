package com.wsdjeg.chat.server;

import java.util.HashMap;
import java.util.Map;

public class Account {

    private static Map<String, String> accts = new HashMap<String ,String >();
    static {
        accts.put("root", "1234");
        accts.put("wsdjeg", "1234");
    }
    private Account(){

    }

    public static boolean login(String username, String password){
        if (accts.keySet().contains(username) && accts.get(username).equals(password)) {
            return true;
        }
        return false;
    }

}
