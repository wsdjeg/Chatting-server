package com.wsdjeg.chat.server;

import java.net.Socket;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wsdjeg.chat.server.util.JsonBuilder;

public class Message {
    public static String format(String name, String msg){
        Map<String,String> m = new HashMap<>();
        m.put("time", getTime());
        m.put("type", "user_message");
        m.put("sendder", name);
        m.put("context", msg);
        return JsonBuilder.decode(m);
    }
    public static String format(String msg){
        Map<String,String> m = new HashMap<>();
        m.put("time", getTime());
        m.put("type", "info_message");
        m.put("context", msg);
        return JsonBuilder.decode(m);
    }
    public static String format(String gName, String uName, String msg){
        Map<String,String> m = new HashMap<>();
        m.put("time", getTime());
        m.put("type", "group_message");
        m.put("sendder", uName);
        m.put("context", msg);
        m.put("group_name", gName);
        return JsonBuilder.decode(m);
    }
    public static String getTime(){
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(ts);
    }
}
