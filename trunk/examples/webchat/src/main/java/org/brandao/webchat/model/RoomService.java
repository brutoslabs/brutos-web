
package org.brandao.webchat.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class RoomService {
    
    private final Map<String,User> users;
    private User allUser;
    
    public RoomService(){
        this.users = new HashMap<String, User>();
        try{
            this.allUser = new AllUser();
            this.putUser(this.allUser);
        }
        catch(Exception e){}
    }
    
    public void sendMessage(Message message){
        
        if(this.allUser == message.getDest() || message.isRoomMessage() ){
            List<User> localUsers = this.getUsers();
            for(User user: localUsers){
                Message msg = new Message();
                msg.setOrigin(message.getOrigin());
                msg.setDest(message.isRoomMessage()? user : this.allUser);
                msg.setMessage(message.getMessage());
                user.sendMessage(msg);
            }
        }
        else{
            message.getDest().sendMessage(message);
            if(message.getOrigin() != null && 
               !message.getOrigin().equals(message.getDest()))
                message.getOrigin().sendMessage(message);
        }
    }
    
    private void sendRoomMessage(String text){
        Message msg = new Message();
        msg.setOrigin(null);
        msg.setDest(null);
        msg.setMessage(text);
        this.sendMessage(msg);
    }
    
    public void putUser(User user) 
            throws UserExistException, MaxUsersException{
        
        synchronized(this.users){
            String key = user.getNick().toLowerCase();
            if(users.containsKey(key))
                throw new UserExistException();

            if(users.size()>20)
                throw new MaxUsersException();

            users.put(key, user);

            RoomService oldRoom = user.getRoom();

            if(oldRoom != null)
                oldRoom.removeUser(user);

            user.setRoom(this);
            
            String text = String.format(
                    "%s acabou de entrar na sala", user.getNick());
            
            this.sendRoomMessage(text);
        }        
    }
    
    public void removeUser(User user){
        synchronized(this.users){
            String key = user.getNick().toLowerCase();
            if(users.containsKey(key)){

                this.users.remove(key);

                String text = String.format(
                        "%s acabou de sair da sala", user.getNick());

                this.sendRoomMessage(text);   
            }
        }
    }
    
    public User getUserByNick(String nick){
        return this.users.get(nick == null? null : nick.toLowerCase());
    }
    
    public Message getMessage(User user) throws InterruptedException{
        BlockingQueue<Message> msgs = user.getMessages();
        return msgs.poll(5, TimeUnit.SECONDS);
    }
    
    public List<User> getUsers(){
        synchronized(this.users){
            return new ArrayList<User>(this.users.values());
        }
    }
}
