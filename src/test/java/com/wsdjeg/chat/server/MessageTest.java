package com.wsdjeg.chat.server;

public class MessageTest {
    /* test format */
    public void testFormat() {
        System.out.println(Message.format("root", "helloworld!"));
    }
    /* test getTime */
    public void testGetTime() {
        System.out.println(Message.getTime());
    }

}
