package com.example.teamproject_main_editing.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
public class MsgRoom {


    private String id;

    private String roomId;

    private String registory;

    private String gender;


    public static MsgRoom create(){

        MsgRoom msgRoom = new MsgRoom();
        msgRoom.id = UUID.randomUUID().toString();
        return msgRoom;
    }
}
