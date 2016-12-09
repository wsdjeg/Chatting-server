package com.wsdjeg.chat.server.bot;

public class BotFactory {
    public static Bot getBot(String botName){
        switch (botName) {
            case "MathBot":
                return new MathBot();
        }
        return null;
    }
}
