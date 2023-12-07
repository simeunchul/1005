package com.example.teamproject_main_editing.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Postboard {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    long pno;
    @Column
    long bno;
    @Column
    String writer;
    @Column
    String content;
    @Column
    Date date;
    @Column
    String regdate;

    public Postboard(String writer, String content,long bno, Date date,String regdate){
        this.bno = bno;
        this.writer = writer;
        this.content = content;
        this.regdate = regdate;
        this.date =date;
    }

    public Postboard(PostboardRequestDTO postboardRequestDTO){
        this.bno = postboardRequestDTO.getBno();
        this.writer = postboardRequestDTO.getWriter();
        this.content = postboardRequestDTO.getContent();
        this.regdate = postboardRequestDTO.getRegdate();
        this.date = postboardRequestDTO.getDate();
    }

    public void update(PostboardRequestDTO postboardRequestDTO){
        this.date = postboardRequestDTO.getDate();
        this.bno=postboardRequestDTO.getBno();
        this.writer = postboardRequestDTO.getWriter();
        this.content = postboardRequestDTO.getContent();
        this.regdate = postboardRequestDTO.getRegdate();
    }
}
