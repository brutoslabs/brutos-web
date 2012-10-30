package org.brandao.webchat.controller;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import org.brandao.webchat.controller.entity.MessageDTO;
import org.brandao.webchat.controller.entity.UserDTO;
import org.brandao.webchat.model.*;

@RequestScoped
public class RoomController {
    
    private User currentUser;
    
    private RoomService roomService;
    
    public RoomController(){
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public void setRoomService(RoomService roomService){
        this.roomService = roomService;
    }
    
    public void sendMessage(MessageDTO message) throws UserNotFoundException{
        roomService.sendMessage(
            message.rebuild(
                this.roomService,this.getCurrentUser()));
    }
    
    public void putUser(UserDTO userDTO) 
            throws UserExistException, MaxUsersException{

        if(userDTO == null)
            throw new NullPointerException();
        
        if(this.getCurrentUser() != null)
            this.getCurrentUser().exitRoom();
        
        User user = userDTO.rebuild();
        roomService.putUser(user);
        
        this.setCurrentUser(user);
        
    }
    
    public void removeUser(User user) throws UserNotFoundException{
        if(user != null)
            user.exitRoom();
    }
    
    public MessageDTO getMessage() 
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
    
    public List<UserDTO> getUsers(){
        List<User> users = roomService.getUsers();
        List<UserDTO> usersDTO = new ArrayList<UserDTO>();
        
        for(User user: users)
            usersDTO.add(new UserDTO(user));
        
        return usersDTO;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
