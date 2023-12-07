package com.example.teamproject_main_editing.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long userNo;

    @Column
    private String userName;

    @Column
    private Long registNumber;

    @Column
    private String userId;

    @Column
    private String userPw;


    public Member(String userName, Long redistNumber, String userId, String userPw){
        this.userName = userName;
        this.registNumber = redistNumber;
        this.userId = userId;
        this.userPw =userPw;
    }

    public Member(UserRequestDTO userRequestDTO){
        this.userName = userRequestDTO.getUserName();
        this.registNumber = userRequestDTO.getRegistNumber();
        this.userId = userRequestDTO.getUserId();
        this.userPw = userRequestDTO.getUserPw();
    }

    public void update(UserRequestDTO userRequestDTO){
        this.userName = userRequestDTO.getUserName();
        this.registNumber = userRequestDTO.getRegistNumber();
        this.userId =userRequestDTO.getUserId();
        this.userPw = userRequestDTO.getUserPw();
    }
}
