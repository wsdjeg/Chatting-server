package com.wsdjeg.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.wsdjeg.chat.server.Security;

public class ServerThread extends Thread{
    private Socket client;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private boolean logined;
    public ServerThread(Socket s) throws IOException {
        client = s;
        bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        printWriter = new PrintWriter(client.getOutputStream(),true);
        Logger.log(Logger.INFO,"client(" + getName() + ") come in...");
        start();
    }
    public void run(){
        try {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("bye")){
                    break;
                }else if(line.indexOf("/") == 0){
                    if (line.indexOf("/login ") == 0) {
                        String command[] = line.split(" ");
                        if(command.length == 3
                                && Account.login(command[1], command[2])
                                && !Security.isBlock(client.getInetAddress().getHostAddress())) {
                            Logger.log(Logger.INFO, "Client " + getName() + " now logined as " + command[1]);
                            this.setName(command[1]);
                            logined = true;
                            Account.register(this);
                            send(Message.format("you are logined as " + command[1]));
                        }else{
                            Security.sign(client.getInetAddress().getHostAddress());
                            Logger.log(Logger.WARNNING, client.getInetAddress().getHostAddress()
                                    + " login failed more than 3 times, blocked!");
                            send("login failed!");
                        }
                    }else if (line.indexOf("/signin ") == 0 ){
                        String command[] = line.split(" ");
                        if (command.length == 4 && Account.signin(command[1], command[2], command[3])) {
                            this.setName(command[1]);
                            logined = true;
                            Account.register(this);
                            send("signin successfully!");
                            Logger.log(Logger.INFO, "Client " + getName() + " now logined as " + command[1]);
                        }else{
                            send("signin failed!");
                        }
                    }
                }else if(logined){
                    for (ServerThread s : Account.getServerThreads()) {
                        s.send(Message.format(getName(), line));
                    }
                }else{
                    send(Message.format("please login!"));
                }
            }
            for (ServerThread s : Account.getServerThreads()) {
                s.send(Message.format(getName() + " has left!"));
            }
            send("bye, Client!");
            log("Client(" + getName() + ") exit!");
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
    public void log(String log){
        System.out.println(log);
    }
    public void send(String msg){
        printWriter.println(msg);
    }
}
