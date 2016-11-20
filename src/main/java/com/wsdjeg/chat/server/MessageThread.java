package com.wsdjeg.chat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageThread extends Thread{
        public MessageThread(Socket s) throws IOException{

        }
        @Override
        public void run() {
            while (true) {
                if(!Message.pull().equals(null)){
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
