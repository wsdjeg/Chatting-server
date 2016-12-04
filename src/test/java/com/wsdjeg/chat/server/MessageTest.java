package com.wsdjeg.chat.server;

public class MessageTest {
    /* test format */
    public void testFormat() {
        String s = "hell\"ss\"ss";
        System.out.println(s.replace("\"", "\\" + "\""  ));

    }
    /* test getTime */
    public void testGetTime() {
        System.out.println(Message.getTime());
    }

}
