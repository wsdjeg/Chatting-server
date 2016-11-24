package com.wsdjeg.chat.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Security {
    public static Map<String,Integer> blockIps = new HashMap<String,Integer>();
    public static Map<String,Long> blockTime = new HashMap<String,Long>();
    public static boolean isBlock(String ip){
        return ((System.currentTimeMillis() - blockTime.get(ip)) < 60000) && blockIps.get(ip) > 3;
    }

    public static void sign(String ip){
        if (blockIps.containsKey(ip)) {
            blockIps.replace(ip, blockIps.get(ip) + 1);
            blockTime.replace(ip, System.currentTimeMillis());
        }else{
            blockIps.put(ip, 1);
            blockTime.put(ip, System.currentTimeMillis());
        }
    }
}
