package org.brandao.webchat.model;

public class Message {
    
    private User origin;
    
    private User dest;

    private String message;
    
    public User getOrigin() {
        return origin;
    }

    public void setOrigin(User origin) {
        this.origin = origin;
    }

    public User getDest() {
        return dest;
    }

    public void setDest(User dest) {
        this.dest = dest;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean isUserMessage(){
        return this.origin != null;
    }
    
    public boolean isRoomMessage(){
        return this.origin == null;
    }
    
}
