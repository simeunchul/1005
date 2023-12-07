package com.example.teamproject_main_editing.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface PostboardRepository extends JpaRepository<Postboard,Long> {

    ArrayList<Postboard> findPostboardByOrderByPnoDesc();
    ArrayList<Postboard> findPostboardByBnoOrderByPnoDesc(Long bno);
}
