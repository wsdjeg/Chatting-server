package com.wsdjeg.chat.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Message {
    private Message(){

    }
    private static List<String> messages = new ArrayList<>();

    private static List<Socket> sockets = new ArrayList<>();

    private static List<ServerThread> serverThreads = new ArrayList<>();

    public static void register(ServerThread s){
        if (!serverThreads.contains(s)) {
            serverThreads.add(s);
        }
    }

    public static void register(Socket s){
        if (!sockets.contains(s)){
            sockets.add(s);
        }
    }

    public static List<ServerThread> getServerThreads(){
        return serverThreads;
    }
    public static List<Socket> getSockets(){
        return sockets;
    }

    public static void push(String msg){
        messages.add(msg);
    }

    public static String pull(){
        return messages.get(0);
    }
    public static void remove(){
        messages.remove(0);
    }

}
