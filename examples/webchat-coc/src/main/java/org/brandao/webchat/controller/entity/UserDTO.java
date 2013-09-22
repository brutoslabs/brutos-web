package org.brandao.webchat.controller.entity;

import java.io.Serializable;
import org.brandao.brutos.annotation.Bean;
import org.brandao.brutos.annotation.Constructor;
import org.brandao.brutos.validator.ValidatorException;
import org.brandao.webchat.model.RoomService;
import org.brandao.webchat.model.User;
import org.brandao.webchat.model.UserNotFoundException;

@Bean
public class UserDTO implements Serializable{

    private String nick;
    
    @Constructor
    public UserDTO(){
    }
    
    public UserDTO(User user){
        this.nick = user.getNick();
    }
    
    private void validate(){
        if(this.nick == null || this.nick.length() < 3 || 
           this.nick.length() > 30 || !this.nick.matches("[^\\<\\>\\s]*"))
            throw new ValidatorException("msgError");
    }
    
    public User rebuild(RoomService roomService) throws UserNotFoundException{
        
        this.validate();
        
        User obj = roomService.getUserByNick(this.nick);
        if(obj == null)
            throw new UserNotFoundException();
        else
            return obj;
    }
    
    public User rebuild(){
        
        this.validate();
        
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
