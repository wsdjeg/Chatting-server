package com.wsdjeg.chat.server;

import java.util.ArrayList;
import java.util.List;

import com.wsdjeg.chat.Server;

public class Command {
    public static final String SPLIT = "\\s+";
    private static List<String> commands = new ArrayList<String>();
    static {
        commands.add("/login");
        commands.add("/logout");
        commands.add("/signup");
        commands.add("/names");
        commands.add("/password");
        commands.add("/join");
        commands.add("/addfriend");
        commands.add("/removefriend");
        commands.add("/msg");
        commands.add("/help");
        commands.add("/list");
    }
    private Command(){

    }

    public static boolean isCommand(String str){
        return commands.contains(str.split("\\s+")[0]);
    }

    public static String[] parser(String input){
        List<String> cli = new ArrayList<>();
        String inputs[] = input.split("\\s+");
        switch (inputs[0]) {
            case "/msg":
                if (inputs.length >= 3){
                    cli.add(inputs[0]);
                    cli.add(inputs[1]);
                    cli.add(input.replaceFirst("/msg\\s+", "").replaceFirst("\\S+\\s+", ""));
                    String[] result = new String[cli.size()];
                    return cli.toArray(result);
                }else{
                    return null;
                }
        }
        return null;
    }

    public static String[] names(String ch){
        ArrayList<String> rst = new ArrayList<String>();
        String line = "";
        for (User s : GroupManager.getGroup(ch).getMembers()) {
            line += "[" + s.getUserName() + "] ";
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
        help.add("   /names : list the use in current channel");
        help.add("   /msg USER MSG : send a message to your friends or user in same group.");


        String[] array = new String[help.size()];
        return help.toArray(array);
    }

    public static String[] list(){
        List<String> gs = new ArrayList<>();
        for (Group g: GroupManager.getGroups()) {
            gs.add(g.getName());
        }
        String[] result = new String[gs.size()];
        return gs.toArray(result);
    }
}
