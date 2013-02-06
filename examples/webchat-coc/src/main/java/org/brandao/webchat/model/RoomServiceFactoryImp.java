package org.brandao.webchat.model;

import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoomServiceFactoryImp implements RoomServiceFactory{
    
    private Map<String,RoomService> rooms;
    
    public RoomServiceFactoryImp(){
        this.rooms = new HashMap<String,RoomService>();
    }
    
    @Override
    public synchronized RoomService getRoomService(String id){
        
        RoomService room;
        if( rooms.containsKey(id) )
            room = rooms.get(id);
        else{
            room = new RoomService(id);
            rooms.put(id,room);
        }
        
        return room;
    }
    
}
