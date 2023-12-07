package com.example.teamproject_main_editing.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    public enum MessageType{
        ENTER, COMM, OUT
    }
    private MessageType messageType;
    private String roomId;
    private String sender;
    private String message;
}
