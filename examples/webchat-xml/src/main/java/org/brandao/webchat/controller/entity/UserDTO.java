package org.brandao.webchat.controller.entity;

import java.io.Serializable;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.WebApplicationContext;
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
        WebApplicationContext context = 
                ContextLoader.getCurrentWebApplicationContext();
        User obj = (User) context.getBean(User.class);
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
