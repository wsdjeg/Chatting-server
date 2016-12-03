package com.wsdjeg.chat.server;

import java.util.ArrayList;
import java.util.List;

public  class Group {
    private String name;
    private int id;
    private List<User> members = new ArrayList<>();
    public Group(String name){
        this.name = name;
        this.id = GroupManager.generateId();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void addMember(User user){
        if (!members.contains(user)) {
            members.add(user);
        }
    }

    public void removeMember(User user){
        if (members.contains(user)) {
            members.remove(user);
        }
    }

    public void send(User sender, String msg){
        String line = Message.format(getName(), sender.getUserName(), msg);
        for (User m : members) {
            m.send(line);
        }
    }
}
