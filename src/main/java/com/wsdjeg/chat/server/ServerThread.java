package com.wsdjeg.chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.wsdjeg.chat.server.Security;

public class ServerThread extends Thread{
    private Socket client;
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
                    if (line.indexOf("/login ") == 0) {
                        String command[] = line.split(" ");
                        if(command.length == 3
                                && Account.login(command[1], command[2])
                                && !Security.isBlock(client_ip)) {
                            Logger.info("Client(" + getName() + ") now logined as : " + command[1] + "!");
                            this.setName(command[1]);
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
                    }else if (line.indexOf("/signin ") == 0 ){
                        String command[] = line.split(" ");
                        if (command.length == 4 && Account.signin(command[1], command[2], command[3])) {
                            this.setName(command[1]);
                            logined = true;
                            Account.register(this);
                            send("signin successfully!");
                            Logger.info("Client(" + getName() + ") now logined as : " + command[1] + "!");
                        }else{
                            send("signin failed!");
                        }
                    }else if (line.indexOf("/names") == 0 && logined){
                        for (String l : Command.names()) {
                            send(Message.format(l));
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
