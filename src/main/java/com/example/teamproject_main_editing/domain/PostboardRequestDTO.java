package com.example.teamproject_main_editing.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostboardRequestDTO {
    long pno;
    long bno;
    String writer;
    String content;
    Date date;
    String regdate;
}
