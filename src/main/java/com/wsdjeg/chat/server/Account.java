package com.wsdjeg.chat.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {

    private static Map<String, String> accts = new HashMap<String ,String >();
    private static List<String> names = new ArrayList<String>();
    static {
        accts.put("root", "1234");
    }
    private Account(){

    }

    public static boolean login(String username, String password){
        if (accts.keySet().contains(username)
                && accts.get(username).equals(password)
                && !names.contains(username)) {
            names.add(username);
            return true;
        }
        return false;
    }
    private static List<ServerThread> serverThreads = new ArrayList<>();
    public static void register(ServerThread s){
        if (!serverThreads.contains(s)) {
            serverThreads.add(s);
        }
    }
    public static void loginOut(ServerThread s){
        if (serverThreads.contains(s)){
            serverThreads.remove(s);
        }
        if (names.contains(s.getName())){
            names.remove(s.getName());
        }
        User u = UserManager.getUser(s.getName());
        if ( u != null) {
            u.left();
        }
    }
    public static List<ServerThread> getServerThreads(){
        return serverThreads;
    }

    public static boolean signin(String name, String pw, String pwcf){
        if (!pw.equals(pwcf)) {
            return false;
        }
        if (accts.values().contains(name)){
            return false;
        }

        accts.put(name, pw);
        return true;
    }

    public static boolean password(String user,String password){
        if ( accts.keySet().contains(user)){
            accts.put(user, password);
            return true;
        }
        return false;
    }

}
