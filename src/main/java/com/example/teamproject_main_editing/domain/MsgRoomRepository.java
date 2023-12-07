package com.example.teamproject_main_editing.domain;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class MsgRoomRepository {

    private Map<String,MsgRoom> msgRoomMap;
    private HashMap<Integer,String> roomIdMap;

    @PostConstruct
    private void init(){
        msgRoomMap = new LinkedHashMap<>();
        roomIdMap = new HashMap<Integer,String>();
    }

    public List<MsgRoom> findAllRoom(){
        List<MsgRoom> msgRooms = new ArrayList(msgRoomMap.values());
        return msgRooms;
    }
    public MsgRoom findRoomById(Long roomId){
        return msgRoomMap.get(roomId);
    }

    public List<MsgRoom> findregistory(String regitory){
        List<MsgRoom> msgRooms = findAllRoom();
        for(int i=0;i<msgRooms.size();i++){
            MsgRoom msgRoom = msgRooms.get(i);
            System.out.println(i+ "번째" + msgRoom.getRoomId());

        }
        System.out.println(msgRooms.size());
        List<MsgRoom> registroymsgRooms = new ArrayList<>();
        for(int i=0;i<msgRooms.size();i++){
            MsgRoom msgRoom = msgRooms.get(i);
            if(msgRoom.getRegistory()==null){
                System.out.println("널");
            }
            else {
                if (msgRoom.getRegistory().equals(regitory)) {
                    registroymsgRooms.add(msgRoom);
                    System.out.println("룸 들어옴");
                }
            }
        }
        return registroymsgRooms;
    }
    public MsgRoom createMsgRoom(String roomId){
        MsgRoom msgRoom = MsgRoom.create();
        msgRoomMap.put(roomId, msgRoom);
        return msgRoom;
    }

    public MsgRoom saveroomid(MsgRoom msgRoom){
        roomIdMap.put(1, msgRoom.getRoomId());
        return msgRoom;
    }

    public List<MsgRoom> deleteall(){
        List<MsgRoom> msgRooms = new ArrayList<>(msgRoomMap.values());
        msgRooms.clear();
        msgRoomMap.clear();
        return msgRooms;
    }

    public List<MsgRoom> deletezero(){
        List<MsgRoom> msgRooms = new ArrayList<>(msgRoomMap.values());
        msgRooms.remove(0);
        return msgRooms;
    }
    public List<MsgRoom> deleteone(String roomId){
        System.out.println("심은철");
        List<MsgRoom> msgRooms = findAllRoom();
        System.out.println(msgRooms.size());
        for(int i=0;i<msgRooms.size();i++){
            MsgRoom msgRoom = msgRooms.get(i);
            System.out.println(msgRoom.getRoomId());
            if(msgRoom.getRoomId().equals(roomId)){
                System.out.println(msgRoom.getRoomId());
                msgRooms.remove(msgRoom);
                msgRoomMap.remove(roomId);
            }
        }
        System.out.println(msgRooms.size());
        return msgRooms;
    }

    public HashMap<Integer,String> findmap(){
        return roomIdMap;
    }
}
