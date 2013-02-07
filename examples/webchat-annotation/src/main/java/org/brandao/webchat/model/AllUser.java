package org.brandao.webchat.model;

public class AllUser extends User{
    
    @Override
    public String getNick(){
        return "Todos";
    }
    
    @Override
    public boolean isActive(){
        return true;
    }
    
    @Override
    public void sendMessage(Message msg){
    }
}
