package com.wsdjeg.chat.server;

public class MessageTest {
    /* test format */
    public void testFormat() {
        System.out.println(Message.format("你好我,neo\\\""));
    }
    /* test getTime */
    public void testGetTime() {
        System.out.println(Message.getTime());
    }

}
