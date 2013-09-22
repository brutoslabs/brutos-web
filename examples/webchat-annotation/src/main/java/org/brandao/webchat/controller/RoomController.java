package org.brandao.webchat.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.validator.ValidatorException;
import org.brandao.webchat.controller.entity.MessageDTO;
import org.brandao.webchat.controller.entity.UserDTO;
import org.brandao.webchat.model.*;

@RequestScoped
@Controller("/Room/{roomID:\\d+}")
@View("/layout/login.jsp")
@Actions({
    @Action(value="/messagePart",view=@View("/layout/messages.jsp")),
    @Action(value="/sendPart",   view=@View("/layout/send.jsp")),
    @Action(value="/login",      view=@View("/layout/login.jsp"))
})
public class RoomController {
    
    private User currentUser;
    
    private RoomService roomService;
    
    public RoomController(){
    }

    public RoomService getRoomService() {
        return roomService;
    }
    
    @Identify(bean="roomID")
    public void setRoomService(RoomService roomService){
        this.roomService = roomService;
    }
    
    @Action("/send")
    public void sendMessage(
            @Identify(useMapping=true)
            MessageDTO message) throws UserNotFoundException{
        roomService.sendMessage(
            message.rebuild(
                this.roomService,this.getCurrentUser()));
    }
    
    @Action("/enter")
    @View("/layout/room.jsp")
    @ThrowSafeList({
        @ThrowSafe(target=ValidatorException.class,   view="/layout/login.jsp"),
        @ThrowSafe(target=UserExistException.class,   view="/layout/login.jsp"),
        @ThrowSafe(target=MaxUsersException.class,    view="/layout/login.jsp"),
        @ThrowSafe(target=NullPointerException.class, view="/layout/login.jsp")
    })
    public void putUser(
            @Identify(useMapping=true)
            UserDTO userDTO) 
            throws UserExistException, MaxUsersException{

        if(userDTO == null)
            throw new NullPointerException();
        
        if(this.getCurrentUser() != null)
            this.getCurrentUser().exitRoom();
        
        User user = userDTO.rebuild();
        roomService.putUser(user);
        this.setCurrentUser(user);
    }
    
    @Action("/exit")
    public void removeUser(
            @Identify(bean="user",scope=ScopeType.SESSION)
            User user ) throws UserNotFoundException{
        if(user != null)
            user.exitRoom();
    }
    
    @Action("/message")
    @ResultView(rendered=true)
    public Serializable readMessage() 
            throws UserNotFoundException, InterruptedException{
        
        Message msg = roomService.getMessage(this.getCurrentUser());
        
        if( msg != null){
            MessageDTO msgDTO = new MessageDTO(msg);
            msgDTO.setForMe(
                msg.getDest().equals(this.currentUser));
            return msgDTO;
        }
        else
            return null;
    }
    
    @Action("/listUsers")
    @ResultView(rendered=true)
    public Serializable readUsers(){
        List<User> users = roomService.getUsers();
        List<UserDTO> usersDTO = new ArrayList<UserDTO>();
        
        for(User user: users)
            usersDTO.add(new UserDTO(user));
        
        return (Serializable)usersDTO;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Identify(bean="sessionUser", scope=ScopeType.SESSION)
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
