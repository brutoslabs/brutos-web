package org.brandao.webchat.model;

import java.util.HashMap;
import java.util.Map;

public class RoomServiceFactoryImp implements RoomServiceFactory{
    
    private static final Map<String,RoomService> rooms =
             new HashMap<String,RoomService>();
    
    public RoomServiceFactoryImp(){
    }
    
    @Override
    public synchronized RoomService getRoomService(String id){
        RoomService room;
        if( rooms.containsKey(id) )
            room = rooms.get(id);
        else{
            room = new RoomService();
            rooms.put(id,room);
        }
        
        return room;
    }
    
}
