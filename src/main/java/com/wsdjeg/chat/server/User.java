package com.wsdjeg.chat.server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
/**
 * User public API:
 * 1. `/join` : join or create a group
 */
public class User {
    private String userName;
    private List<String> unReadMsg = new ArrayList<>();
    private ServerThread client;
    private List<User> friends = new ArrayList<>();
    private List<Integer> groupIds = new ArrayList<>();
    public User(String userName){
        this.userName = userName;
    }

    public void join(String gName){
        int id = GroupManager.getGroupId(gName);
        if (id != 0) {
            groupIds.add(id);
            GroupManager.getGroup(gName).addMember(this);
        }else{
            // no such group, create a new one.
            Group ng = GroupManager.newGroup(gName);
            ng.addMember(this);
            groupIds.add(ng.getId());
        }
    }

    public void left(){
        for (int id : groupIds) {
            GroupManager.getGroup(id)
                .removeMember(this)
                .send(getUserName() + " has left!");
        }
    }

    public void addFriend(User name){
        if(!friends.contains(name)) {
            friends.add(name);
        }
    }

    public void removeFriend(User name){
        if (friends.contains(name)) {
            friends.remove(name);
        }
    }

    public String[] getUnReadMsg(){
        String[] msgs = new String[unReadMsg.size()];
        return unReadMsg.toArray(msgs);
    }

    public void clearUnReadMsg(){
        unReadMsg.clear();
    }

    public List<User> getFriends(){
        return friends;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void send(String msg){
            client.send(msg);
    }

    /**
     * snnder : who send the message to current user;
     */
    public void send(User sender, String msg){
        if (client == null || !client.isAlive()) {
            unReadMsg.add(Message.format(sender.getUserName(), msg));
        }else{
            client.send(Message.format(sender.getUserName(), msg));
        }
    }

    public boolean isFriend(User user){
        return friends.contains(user);
    }

    public boolean hasSameGroup(User user){
        for (int id  : groupIds) {
            if(GroupManager.getGroup(id).hasMember(user)){
                return true;
            }
        }
        return false;
    }

    public void setClient(ServerThread client) {
        this.client = client;
    }

    public ServerThread getClient() {
        return client;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User object = (User) o;

        return !(userName != null ? !userName.equals(object.userName) : object.userName != null);
    }
}
