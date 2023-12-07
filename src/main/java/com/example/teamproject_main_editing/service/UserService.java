package com.example.teamproject_main_editing.service;

import com.example.teamproject_main_editing.domain.Member;
import com.example.teamproject_main_editing.domain.UserRepostiory;
import com.example.teamproject_main_editing.domain.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepostiory userRepostiory;

    public int validuatecheck(String userId){
        int check;
        Optional<Member> userOptional=userRepostiory.findByUserId(userId);
        if(userOptional.isPresent()){
            check=1;
        }
        else {
            check=0;
        }
        return check;
    }

    public int deletservice(String userId){
        int check;
        Optional<Member> userOptional = userRepostiory.deleteByUserId(userId);
        if(userOptional.isPresent()){
            check=1;
        }
        else {
            check=0;
        }
        return check;
    }

    public void updateservice(UserRequestDTO userRequestDTO, String userId){
       Member member = userRepostiory.findByUserId(userId).orElseThrow(
               () -> new IllegalArgumentException("아이디가 존재하지 않습니다")
       );
       Long userRn = member.getRegistNumber();
       userRequestDTO.setRegistNumber(userRn);
       userRepostiory.deleteByUserId(userId);
       member.update(userRequestDTO);
       userRepostiory.save(member);
    }

    public String findIdByNameRn(UserRequestDTO userRequestDTO, String userName){
        UserRequestDTO vo = new UserRequestDTO();
        vo.setUserName("11");
        vo.setRegistNumber(11);
        Member member = userRepostiory.findByUserName(userName).orElse(new Member(vo));
        ;
        if(member.getRegistNumber().equals(userRequestDTO.getRegistNumber())){
            return member.getUserId();
        }
        else{
            return null;
        }
    }

    public String findPwByNameRn(UserRequestDTO userRequestDTO, String userId){
        UserRequestDTO vo = new UserRequestDTO();
        vo.setUserName("11");
        vo.setRegistNumber(11);
        Member member = userRepostiory.findByUserId(userId).orElse(new Member(vo));

        if(member.getUserName().equals(userRequestDTO.getUserName())){
            if(member.getRegistNumber().equals(userRequestDTO.getRegistNumber())){
                return member.getUserPw();
            }
            else{
                return null;
            }
        }
        else{
            return null;
        }

    }

    //private ArrayList<Long> likeboard;
    private Map<Long,ArrayList<Long>> likeboardList;

    @PostConstruct
    private void init(){
        //likeboard = new ArrayList<>();
        likeboardList = new HashMap<>();
    }
    public void addlikeboard(long userNo, long bno){
        if(likeboardList.get(userNo)==null||likeboardList.get(userNo).isEmpty()){
            System.out.println("add if");
            ArrayList<Long> likeboard = new ArrayList<>();
            likeboard.add(bno);
            likeboardList.put(userNo,likeboard);
            System.out.println(likeboard.contains(bno));
        }
        else if(!(likeboardList.get(userNo).isEmpty())){
            System.out.println("add else");
            likeboardList.get(userNo).add(bno);
        }
    }
    public void removelikeboard(long userNo, long bno){
        likeboardList.get(userNo).remove(bno);
    }
    public ArrayList<Long> findlikeboard(long userNo){
        System.out.println("find userNo : "+userNo);
        if(likeboardList.get(userNo)==null){
            System.out.println("find if");
            ArrayList<Long> likeboard = new ArrayList<>();
            return likeboard;
        }
        else {
            System.out.println("find else");
            return likeboardList.get(userNo);
        }
    }

}
