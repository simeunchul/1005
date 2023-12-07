package com.example.teamproject_main_editing.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserRepostiory extends JpaRepository<Member, Long> {
    Optional<Member> findByUserNo(Long userNo);
    //orderBy > 정렬해라
    //Desc  > 내림차순
    //asc > 오름차순

    Optional<Member> findByRegistNumber(Long rn);
    Optional<Member> findByUserId(String UserId);
    Optional<Member> findByUserPw(String UserPw);

    @Transactional
    Optional<Member> deleteByUserId(String UserId);

    Optional<Member> findByUserName(String UserName);

}
