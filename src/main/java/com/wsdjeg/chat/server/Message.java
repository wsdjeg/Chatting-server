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
    public static String format(String name, String msg){
        String str = "[" + getTime() +  "] < " + name + " > " + msg;
        return str;
    }
    public static String format(String warn){
        String str = "[" + getTime() + "] " + warn;
        return str;
    }
    public static String getTime(){
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(ts);
    }
}
