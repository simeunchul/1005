package com.example.teamproject_main_editing.controller;

import com.example.teamproject_main_editing.domain.Message;
import com.example.teamproject_main_editing.domain.MsgRoom;
import com.example.teamproject_main_editing.domain.MsgRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MsgController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final MsgRoomRepository msgRoomRepository;
    private final MsgRoomController msgRoomController;
    @GetMapping("/comm")
    public void index(Model model){
        List<MsgRoom> msgRooms = msgRoomRepository.findAllRoom();
        ArrayList<Integer> check = new ArrayList<Integer>();
        if(msgRooms.size()==0){
            model.addAttribute("introomId",0);
            System.out.println("사이즈제로");
            System.out.println("introomid : 0");
        }
        else {
            for(int i = 0; i < msgRooms.size(); i++) {
                MsgRoom msgRoom = msgRooms.get(i);
                if (msgRoom.getRoomId() == null) {
                    check.add(0);
                } else {
                    System.out.println(msgRoom.getRoomId());
                    check.add(1);
                }
            }
            if (check.contains(1)) {
                model.addAttribute("introomId", 1);
                System.out.println("introomid : 1");
            } else {
                model.addAttribute("introomId", 0);
                System.out.println("introomid : 0");
            }
        }
        if(msgRoomRepository.findmap().get(1)==null){
            model.addAttribute("roomId",0L);
            System.out.println("roomId 널");
        }
        else {
            model.addAttribute("roomId",msgRoomRepository.findmap().get(1));
            System.out.println("널아님 값은 : " + msgRoomRepository.findmap().get(1));
        }
        System.out.println("roomid : "+model.getAttribute("roomId"));
    }
    @MessageMapping("/comm/message")
    public void message(Message message) {
        if (Message.MessageType.ENTER.equals(message.getMessageType()))
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        if (Message.MessageType.OUT.equals(message.getMessageType()))
            message.setMessage("상대방이 퇴장하셨습니다.");
        messagingTemplate.convertAndSend("/sub/comm/room/" + message.getRoomId(), message);
    }

}
