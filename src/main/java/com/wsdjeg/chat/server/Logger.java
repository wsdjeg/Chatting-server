package com.wsdjeg.chat.server;

import com.wsdjeg.chat.server.Message;

public class Logger {
    private Logger(){
    }

    public static int LEVEL = 3;

    public final static int ERROR = 1;
    public final static int WARNNING = 2;
    public final static int INFO = 3;
    private static String[] types = {"ERROR", "WARN", "INFO"};

    public static void setLevel(int level){
        LEVEL = level;
    }

    public static void log(int type, String msg){
        if (LEVEL >= type && (type == 1 || type == 2 || type == 3)) {
            System.out.println("[" + Message.getTime() + "]" + " [" + types[type - 1] + "] " + msg);
        }
    }
}
