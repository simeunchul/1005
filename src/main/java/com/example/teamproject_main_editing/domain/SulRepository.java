package com.example.teamproject_main_editing.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SulRepository extends JpaRepository<Sul,Long> {

    ArrayList<Sul> findAllByOrderByLikeBtnDesc();
    ArrayList<Sul> findTop5ByOrderByLikeBtnDesc();
    ArrayList<Sul> findAllByOrderByBnoDesc();
    Optional<Sul> findByBno(long bno);
    Long countBy();
    ArrayList<Sul> findByRegistNumber(long reg);

    ArrayList<Sul> findTop5ByOrderByBnoDesc();
    ArrayList<Sul> findAllByWriterOrderByBnoDesc(String Writer);
    ArrayList<Sul> findTop5ByOrderByDateDesc();

    ArrayList<Sul> findTop5BySulOrderByBnoDesc(String sul);

    ArrayList<Sul> findTop5BySulOrderByDateDesc(String Sul);

    ArrayList<Sul> findTop5BySulOrderByLikeBtnDesc(String Sul);

    List<Sul> findTop12ByOrderByBnoDesc();
    ArrayList<Sul> findByRegistNumberOrderByLikeBtnDesc(Long reg);
    Long countByRegistNumber(Long reg);
    List<Sul> findFirst12ByRegistNumberOrderByBnoDesc(long reg);
   //String findSameBno(long bno);

    ArrayList<Sul> findBySulOrderByLikeBtnDesc(String sul);
    ArrayList<Sul> findBySulOrderByBnoDesc(String sul);
    ArrayList<Sul> findAllBySulOrderByBnoDesc(String sul);
    Long countBySul(String sul);
    List<Sul> findTop12BySulOrderByBnoDesc(String sul);
    /*List<SulRequestDTO> selectSojuLimit(Crite ria cri);

    List<SulRequestDTO> selectBeerLimit(Criteria cri);

    List<SulRequestDTO> selectWineLimit(Criteria cri);

    List<SulRequestDTO> selectVodkaLimit(Criteria cri);

    List<SulRequestDTO> selectWhiskeyLimit(Criteria cri);

    List<SulRequestDTO> selectFreeLimit(Criteria cri);*/
}
