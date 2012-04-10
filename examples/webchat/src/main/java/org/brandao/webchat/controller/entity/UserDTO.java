package org.brandao.webchat.controller.entity;

import java.io.Serializable;
import org.brandao.webchat.model.RoomService;
import org.brandao.webchat.model.User;
import org.brandao.webchat.model.UserNotFoundException;

public class UserDTO implements Serializable{

    private String nick;
    
    public UserDTO(){
    }
    
    public UserDTO(User user){
        this.nick = user.getNick();
    }
    
    public User rebuild(RoomService roomService) throws UserNotFoundException{
        User obj = roomService.getUserByNick(this.nick);
        if(obj == null)
            throw new UserNotFoundException();
        else
            return obj;
    }
    
    public User rebuild(){
        User obj = new User();
        obj.setNick(this.nick);
        return obj;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
