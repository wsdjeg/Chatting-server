package com.wsdjeg.chat.server;

import java.util.ArrayList;
import java.util.List;

import com.wsdjeg.chat.Server;

public class Command {
    private static List<String> commands = new ArrayList<String>();
    static {
        commands.add("/login");
        commands.add("/logout");
        commands.add("/signup");
        commands.add("/names");
        commands.add("/password");
        commands.add("/join");
        commands.add("/msg");
        commands.add("/help");
    }
    private Command(){

    }

    public static boolean isCommand(String str){
        return commands.contains(str.split(" ")[0]);
    }

    public static String[] names(){
        ArrayList<String> rst = new ArrayList<String>();
        String line = "";
        for (ServerThread s : Account.getServerThreads()) {
            line += "[" + s.getName() + "] ";
            if (line.length() > 50) {
                rst.add(line);
                line = "";
            }
        }
        if (!line.equals("")) {
            rst.add(line);
        }
        String[] array = new String[rst.size()];
        return rst.toArray(array);
    }
    /**
     * Chatting Server: V0.1.0
     * command :
     *    /help : show help message.
     *    /login USERNAME PASSWORD : login with your chatting account.
     *    /signup USERNAME PASSWORD PASSWORD : create a new chatting account.
     *    /password NEWPASSWORD : change the password of current user.
     *    /join GROUP : join a chatting group
     *
     *
     */
    public static String[] help(){
        List<String> help = new ArrayList<String>();

        help.add("Chatting Server: V" + Server.version);
        help.add("commands :");
        help.add("   /help : show help message.");
        help.add("   /login USERNAME PASSWORD : login with your chatting account.");
        help.add("   /signup USERNAME PASSWORD PASSWORD : create a new chatting account.");
        help.add("   /password NEWPASSWORD : change the password of current user.");
        help.add("   /join GROUP : join a chatting group");


        String[] array = new String[help.size()];
        return help.toArray(array);
    }
}
