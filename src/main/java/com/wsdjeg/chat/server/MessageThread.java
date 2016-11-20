package com.wsdjeg.chat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageThread extends Thread{
        public MessageThread(Socket s) throws IOException{
            start();
        }
        @Override
        public void run() {
            while (true) {
                if(!Message.pull().isEmpty()){
                    for (ServerThread s : Message.getServerThreads()) {
                        try {
                            s.send(Message.pull());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Message.remove();
                }
            }
        }
    }
