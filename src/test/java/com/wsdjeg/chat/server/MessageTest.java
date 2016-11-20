package com.wsdjeg.chat.server;

import java.net.Socket;

public class MessageTest {
    /* test push */
    public void testPush() {
        Message.push("message 1");
        System.out.println(Message.pull());
    }
    /* test pull */
    public void testPull() {
        System.out.println(Message.pull().isEmpty());
    }
    /* test getSockets */
    public void testGetSockets() {
        //TODO
    }
    /* test register */
    public void testRegister() {
        Socket s = new Socket();
        Message.register(s);
        Message.register(s);
        System.out.println(Message.getSockets().size());
    }

}

