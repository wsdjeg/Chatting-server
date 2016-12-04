package com.wsdjeg.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.wsdjeg.chat.server.Security;

public class ServerThread extends Thread{
    private Socket client;
    private User current_user;
    private String current_channel;
    private String client_ip;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private boolean logined;
    public ServerThread(Socket s) throws IOException {
        client = s;
        client_ip = s.getInetAddress().getHostAddress();
        bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        printWriter = new PrintWriter(client.getOutputStream(),true);
        Logger.info("Client(" + getName() + ") come in...");
        start();
    }
    public void run(){
        try {
            String line;

            while (true) {
                line = bufferedReader.readLine();
                if (line == null || line.equals("bye")){
                    Account.loginOut(this);
                    break;
                }else if(Command.isCommand(line)){
                    if (line.indexOf("/help") == 0) {
                        for (String l : Command.help()) {
                            send(l);
                        }
                    }else if (line.indexOf("/login ") == 0) {
                        String command[] = line.split(Command.SPLIT);
                        if(command.length == 3
                                && Account.login(command[1], command[2])
                                && !Security.isBlock(client_ip)) {
                            Logger.info("Client(" + getName() + ") now logined as : " + command[1] + "!");
                            this.setName(command[1]);
                            this.current_user = UserManager.getUser(command[1]);
                            this.current_user.setClient(this);
                            logined = true;
                            Account.register(this);
                            Security.remove(client_ip);
                            send(Message.format("you are logined as " + command[1]));
                            for (String message : current_user.getUnReadMsg()) {
                                send(message);
                            }
                            current_user.clearUnReadMsg();
                        }else{
                            Security.sign(client_ip);
                            send(Message.format("login failed!"));
                            if (Security.isBlock(client_ip)){
                                send(Message.format("your ip is blocked, please login after 60s!"));
                                Logger.warn(client_ip
                                        + " login failed more than 3 times, blocked!");
                            }
                        }
                    }else if (line.indexOf("/signup ") == 0 ){
                        String command[] = line.split(Command.SPLIT);
                        if (command.length == 4 && Account.signin(command[1], command[2], command[3])) {
                            this.setName(command[1]);
                            logined = true;
                            Account.register(this);
                            this.current_user = UserManager.create(command[1]);
                            this.current_user.setClient(this);
                            send(Message.format("signin successfully!"));
                            Logger.info("Client(" + getName() + ") now logined as : " + command[1] + "!");
                        }else{
                            send(Message.format("signin failed!"));
                        }
                    }else if (line.indexOf("/names") == 0 && logined){
                        if (current_channel != null){
                            for (String l : Command.names(current_channel)) {
                                send(Message.format(l));
                            }
                        }
                    }else if(line.indexOf("/password ") == 0 && logined){
                        if (line.split(Command.SPLIT).length == 2 && Account.password(getName(), line.split(Command.SPLIT)[1])){
                            send(Message.format("your password has been changed!"));
                        }
                    }else if(line.indexOf("/join ") == 0 && logined){
                        current_channel = line.split(Command.SPLIT)[1];
                        current_user.join(current_channel);
                        send(Message.format("join channel :" + current_channel));
                    }else if (line.indexOf("/msg ") == 0 && logined) {
                        String cli[] = Command.parser(line);
                        if (cli != null){
                            User u = UserManager.getUser(cli[1]);
                            if (u != null) {
                                if (current_user.isFriend(u) || current_user.hasSameGroup(u)) {
                                    u.send(current_user, cli[2]);
                                }else{
                                    send(Message.format("you and " + cli[1] + " are not friend or in same group!"));
                                }
                            }else{
                                send(Message.format("No such user: " + cli[1]));
                            }
                        }else{
                            send(Message.format("Wrong input, please use /msg userName message!"));
                        }
                    }else if(line.indexOf("/addfriend ") == 0&& logined){
                        User u = UserManager.getUser(line.split(Command.SPLIT)[1]);
                        if (u != null) {
                            current_user.addFriend(u);
                        }
                    }else if(line.indexOf("/removefriend ") == 0 && logined){
                        User u = UserManager.getUser(line.split(Command.SPLIT)[1]);
                        if (u != null) {
                            current_user.removeFriend(u);
                        }
                    }
                }else if(logined){
                    if (current_channel != null && !current_channel.isEmpty()) {
                        GroupManager.getGroup(current_channel).send(current_user, line);
                    }
                }else{
                    send(Message.format("please login!"));
                }
            }
            //send("bye, Client!");
            Logger.info("Client(" + getName() + ") exit!");
        } catch (IOException e) {
        }finally{
            Account.loginOut(this);
            printWriter.close();
            try {
                bufferedReader.close();
                client.close();
            } catch (IOException e) {
            }
        }
    }
    public void send(String msg){
        printWriter.println(msg);
    }
}
