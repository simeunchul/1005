package com.example.teamproject_main_editing.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Sul {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    long bno;
    @Column
    String sul;
    @Column
    String writer;
    @Column
    long registNumber;
    @Column
    String menu;
    @Column
    String title;
    @Column
    String content;
    @Column
    long clickCnt;
    @Column
    long likeBtn;
    @Column
    long likeBtnCheck;
    @Column
    String regdate;
    @Column
    String editdate;
    @Column
    Date date;


    public Sul(String sul, String writer, long registNumber, String menu, String title, String content,
               long clickCnt, long likeBtn, long likeBtnCheck, String regdate, String editdate,Date date){
        this.sul =sul;
        this.writer = writer;
        this.registNumber =registNumber;
        this.menu = menu;
        this.title = title;
        this.content =content;
        this.clickCnt =clickCnt;
        this.likeBtn = likeBtn;
        this.likeBtnCheck =likeBtnCheck;
        this.regdate = regdate;
        this.editdate =editdate;
        this.date = date;
    }

    public Sul(SulRequestDTO sulRequestDTO){
        this.sul = sulRequestDTO.getSul();
        this.writer = sulRequestDTO.getWriter();
        this.registNumber =sulRequestDTO.getRegistNumber();
        this.menu =sulRequestDTO.getMenu();
        this.title =sulRequestDTO.getTitle();
        this.content =sulRequestDTO.getContent();
        this.clickCnt = sulRequestDTO.getClickCnt();
        this.likeBtn = sulRequestDTO.getLikeBtn();
        this.likeBtnCheck =sulRequestDTO.getLikeBtnCheck();
        this.regdate = sulRequestDTO.getRegdate();
        this.editdate = sulRequestDTO.getEditdate();
        this.date = sulRequestDTO.getDate();
    }

    public void update(SulRequestDTO sulRequestDTO){
        this.sul = sulRequestDTO.getSul();
        this.writer = sulRequestDTO.getWriter();
        this.registNumber =sulRequestDTO.getRegistNumber();
        this.menu =sulRequestDTO.getMenu();
        this.title =sulRequestDTO.getTitle();
        this.content =sulRequestDTO.getContent();
        this.clickCnt = sulRequestDTO.getClickCnt();
        this.likeBtn = sulRequestDTO.getLikeBtn();
        this.likeBtnCheck =sulRequestDTO.getLikeBtnCheck();
        this.regdate = sulRequestDTO.getRegdate();
        this.editdate = sulRequestDTO.getEditdate();
        this.date = sulRequestDTO.getDate();
    }

}
