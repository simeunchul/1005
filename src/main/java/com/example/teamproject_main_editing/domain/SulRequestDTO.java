package com.example.teamproject_main_editing.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class SulRequestDTO {
    long bno;
    String sul;
    String writer;
    long registNumber;
    String menu;
    String title;
    String content;
    long clickCnt;
    long likeBtn;
    long likeBtnCheck;
    String regdate;
    String editdate;
    Date date;
}
