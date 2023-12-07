package com.example.teamproject_main_editing.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class UserRequestDTO {
    private long userNo;
    private String userName;
    private long registNumber;
    private String userId;
    private String userPw;
}
