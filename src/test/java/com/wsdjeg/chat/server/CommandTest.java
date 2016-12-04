package com.wsdjeg.chat.server;

public class CommandTest {
    /* test help */
    public void testHelp() {
        //TODO
    }
    /* test names */
    public void testNames() {
        //TODO
    }
    /* test parser */
    public void testParser() {
        for (String s :Command.parser("/msg  nihao sss aaa")){
            System.out.println(s);
        }
    }
    /* test isCommand */
    public void testIsCommand() {
        System.out.println(Command.isCommand("/login  wsdjeg 1234 "));
    }

}
