package org.brandao.webchat.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.brandao.brutos.annotation.ResultView;
import org.brandao.brutos.validator.ValidatorException;
import org.brandao.webchat.controller.entity.MessageDTO;
import org.brandao.webchat.controller.entity.UserDTO;
import org.brandao.webchat.model.*;

@RequestScoped
public class RoomController {
    
    private User currentUser;
    
    private RoomService roomService;
    
    public RoomController(){
    }
    
    @Inject
    public RoomController(@Named(value="sessionUser") User user){
        this.currentUser = user;
    }

    public void messagePartAction(){
    }

    public void sendPartAction(){
    }

    public void loginAction(){
    }
    
    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService){
        this.roomService = roomService;
    }
    
    public void sendAction(
            MessageDTO message) throws UserNotFoundException{
        roomService.sendMessage(
            message.rebuild(
                this.roomService,this.getCurrentUser()));
    }
    
    public void enterAction(
            UserDTO userDTO) 
            throws ValidatorException, UserExistException, 
                   MaxUsersException, NullPointerException{

        if(userDTO == null)
            throw new NullPointerException();
        
        if(this.getCurrentUser() != null)
            this.getCurrentUser().exitRoom();
        
        User user = userDTO.rebuild();
        roomService.putUser(user);
    }
    
    public void exitAction() throws UserNotFoundException{
        if(this.currentUser != null)
            this.currentUser.exitRoom();
    }
    
    @ResultView(rendered=true)
    public Serializable messageAction() 
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
    
    @ResultView(rendered=true)
    public Serializable listUsersAction(){
        List<User> users = roomService.getUsers();
        List<UserDTO> usersDTO = new ArrayList<UserDTO>();
        
        for(User user: users)
            usersDTO.add(new UserDTO(user));
        
        return (Serializable)usersDTO;
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
