package org.brandao.webchat.model;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.annotation.PostConstruct;

public class User implements Serializable{
    
    private String nick;
    private RoomService room;
    private BlockingQueue<Message> messages;
    private long lastRead;
    
    public User(){
    }
    
    public String getNick() {
        return nick;
    }

    @PostConstruct
    public void init(){
        this.messages = new LinkedBlockingQueue<Message>();
        this.lastRead = System.currentTimeMillis();
    }
    
    public void setNick(String nick) {
        this.nick = nick;
    }
    
    public void sendMessage(Message message){
        if(!this.isActive() && this.room != null)
            this.room.removeUser(this);
        else
            this.messages.add(message);
    }

    public RoomService getRoom() {
        return room;
    }

    public void setRoom(RoomService room) {
        this.room = room;
    }

    public BlockingQueue<Message> getMessages() {
        long current = System.currentTimeMillis();
        this.lastRead = current;
        return messages;
    }

    public void clearMessages(){
        this.messages.clear();
    }
    
    public void exitRoom(){
        if(this.room!=null)
            this.room.removeUser(this);
    }
    
    public boolean isActive(){
        long current = System.currentTimeMillis();
        return current - this.lastRead < 10*1000;
    }
    
    @Override
    public boolean equals(Object x){
        return x instanceof User? 
            ((User)x).getNick().equalsIgnoreCase(this.nick) :
            false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.nick != null ? this.nick.hashCode() : 0);
        return hash;
    }
    
}
