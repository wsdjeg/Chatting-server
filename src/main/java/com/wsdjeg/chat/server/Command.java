package com.wsdjeg.chat.server;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private static List<String> commands = new ArrayList<String>();
    static {
        commands.add("/login");
        commands.add("/signin");
        commands.add("/names");
        commands.add("/password");
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
}
