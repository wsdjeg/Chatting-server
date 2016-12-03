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
                        String command[] = line.split(" ");
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
                        }else{
                            Security.sign(client_ip);
                            send("login failed!");
                            if (Security.isBlock(client_ip)){
                                send("your ip is blocked, please login after 60s!");
                                Logger.warn(client_ip
                                        + " login failed more than 3 times, blocked!");
                            }
                        }
                    }else if (line.indexOf("/signup ") == 0 ){
                        String command[] = line.split(" ");
                        if (command.length == 4 && Account.signin(command[1], command[2], command[3])) {
                            this.setName(command[1]);
                            logined = true;
                            Account.register(this);
                            this.current_user = UserManager.create(command[1]);
                            this.current_user.setClient(this);
                            send("signin successfully!");
                            Logger.info("Client(" + getName() + ") now logined as : " + command[1] + "!");
                        }else{
                            send("signin failed!");
                        }
                    }else if (line.indexOf("/names") == 0 && logined){
                        for (String l : Command.names()) {
                            send(Message.format(l));
                        }
                    }else if(line.indexOf("/password") == 0 && logined){
                        if (line.split(" ").length == 2 && Account.password(getName(), line.split(" ")[1])){
                            send(Message.format("your password has been changed!"));
                        }
                    }else if(line.indexOf("/join") == 0 && logined){
                        current_channel = line.split(" ")[1];
                        current_user.join(current_channel);
                    }
                }else if(logined){
                    if (current_channel != null && !current_channel.isEmpty()) {
                        GroupManager.getGroup(current_channel).send(current_user, line);
                    }
                }else{
                    send(Message.format("please login!"));
                }
            }
            for (ServerThread s : Account.getServerThreads()) {
                s.send(Message.format(getName() + " has left!"));
            }
            send("bye, Client!");
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
