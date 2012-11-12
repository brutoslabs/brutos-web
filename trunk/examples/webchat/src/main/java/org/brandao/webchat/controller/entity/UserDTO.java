package org.brandao.webchat.controller.entity;

import java.io.Serializable;
import org.brandao.brutos.annotation.Bean;
import org.brandao.brutos.annotation.Constructor;
import org.brandao.brutos.annotation.Restriction;
import org.brandao.brutos.annotation.Restrictions;
import org.brandao.brutos.annotation.RestrictionsRules;
import org.brandao.webchat.model.RoomService;
import org.brandao.webchat.model.User;
import org.brandao.webchat.model.UserNotFoundException;

@Bean
public class UserDTO implements Serializable{

    @Restrictions(
        rules={
            @Restriction(rule=RestrictionsRules.MINLENGTH, value="3"),
            @Restriction(rule=RestrictionsRules.MAXLENGTH, value="30"),
            @Restriction(rule=RestrictionsRules.MATCHES, value="[^\\<\\>\\s]*")
        },
        message="userError"
    )
    private String nick;
    
    @Constructor
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
