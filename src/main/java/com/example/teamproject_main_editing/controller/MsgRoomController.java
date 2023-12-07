package com.example.teamproject_main_editing.controller;

import com.example.teamproject_main_editing.domain.MsgRoom;
import com.example.teamproject_main_editing.domain.MsgRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comm")
public class MsgRoomController {

    private final MsgRoomRepository msgRoomRepository;

    @GetMapping("/rooms")
    @ResponseBody
    public List<MsgRoom> rooms(){
        return msgRoomRepository.findAllRoom();
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomEnter(Model model, @PathVariable String roomId){
        model.addAttribute("roomId",roomId);
        return "comm/roomdetail";
    }
    /*@GetMapping("/room/{roomId}")
    public MsgRoom roomInfo(@PathVariable String roomId){
        return msgService.findById(roomId);
    }*/
    @PostMapping("/room")
    @ResponseBody
    public MsgRoom createRoom(@RequestParam("roomId") String roomId, @RequestParam("registory") String registory,@RequestParam("gender") String gender ,Model model){

        MsgRoom roomIdroom = new MsgRoom();
        System.out.println(registory);
        if(roomId==null||roomId.equals("0")){
            MsgRoom msgRoom = msgRoomRepository.createMsgRoom(roomId);
            msgRoom.setRoomId("0");
            msgRoom.setRegistory(registory);
            msgRoom.setGender(gender);
            roomIdroom.setRoomId("1");
            msgRoomRepository.saveroomid(roomIdroom);
            model.addAttribute("roomId","1");
            model.addAttribute("introomId",1);
            System.out.println("if작동");
            System.out.println("create작동");
            return msgRoom;
        }
        else {
            MsgRoom msgRoom = msgRoomRepository.createMsgRoom(roomId);
            msgRoom.setRoomId(roomId);
            msgRoom.setRegistory(registory);
            msgRoom.setGender(gender);
            int i = Integer.parseInt(msgRoom.getRoomId());
            i++;
            roomId = String.valueOf(i);
            roomIdroom.setRoomId(roomId);
            msgRoomRepository.saveroomid(roomIdroom);
            model.addAttribute("roomId", roomId);
            model.addAttribute("introomId", i);
            System.out.println("else작동");
            System.out.println(roomId);
            System.out.println("create작동");
            return msgRoom;
        }


    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public MsgRoom roomInfo(@PathVariable Long roomId){
        System.out.println("roomInfo 작동");
        return msgRoomRepository.findRoomById(roomId);
    }

    @PostMapping("/roomdelete")
    @ResponseBody
    public List<MsgRoom> roomdelete(@RequestParam("roomId") String roomId,Model model,@RequestParam("realroomId") String realroomId){
        //model.addAttribute("introomId",0);
        System.out.println(roomId);
        List<MsgRoom> msgRooms = msgRoomRepository.deleteone(roomId);
        int i = Integer.parseInt(realroomId);
        i++;
        realroomId = String.valueOf(i);
        MsgRoom roomIdroom = new MsgRoom();
        roomIdroom.setRoomId(realroomId);
        msgRoomRepository.saveroomid(roomIdroom);
        model.addAttribute("roomId", realroomId);
        model.addAttribute("introomId", i);
        System.out.println("delete작동");
        return msgRooms;
    }
    @PostMapping("/registoryrooms")
    @ResponseBody
    public List<MsgRoom> findregistoryRoom(@RequestParam("registory") String registory){
        System.out.println(registory);
        return msgRoomRepository.findregistory(registory);
    }

}
