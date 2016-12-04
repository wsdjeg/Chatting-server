package com.wsdjeg.chat.server;

import java.net.Socket;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Message {
    //private Message(){
    //}
    // message from a user
    // example: ["12:12","root", "helloworld!"]
    public static String format(String name, String msg){
        name = name.replaceAll("\"", "\\" + "\"");
        msg = msg.replaceAll("\"","\\" + "\"");
        String str = "[\"" + getTime() + "\",\"" + name + "\",\"" + msg + "\"]";
        return str;
    }
    public static String format(String warn){
        warn = warn.replaceAll("\"", "\\" + "\"");
        String str = "[\"" + getTime() + "\",\"" + warn + "\"]";
        return str;
    }
    // send a message to a group
    // [12:12] < #neovim > < root > hello world!
    public static String format(String gName, String uName, String msg){
        gName = gName.replaceAll("\"", "\\" + "\"");
        uName = uName.replaceAll("\"", "\\" + "\"");
        msg = msg.replaceAll("\"", "\\" + "\"");
        return "[\"" + getTime() + "\",\"" + gName + "\",\"" + uName + "\",\"" + msg + "\"]";
    }
    public static String getTime(){
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(ts);
    }
}
