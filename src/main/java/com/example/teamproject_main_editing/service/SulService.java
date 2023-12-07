package com.example.teamproject_main_editing.service;

import com.example.teamproject_main_editing.domain.Sul;
import com.example.teamproject_main_editing.domain.SulRepository;
import com.example.teamproject_main_editing.domain.SulRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Component
public class SulService {
    private final SulRepository sulRepository;

    public Long findSameBno(Long bno){
        Optional<Sul> sul = sulRepository.findByBno(bno);
        Sul sul1 = sul.orElse(new Sul());
        return sul1.getRegistNumber();
    }

    public void Dateformatchange(){
        String customFormat="yyyy.MM.dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(customFormat);
        LocalDateTime now0 = LocalDateTime.now();
        long now = System.currentTimeMillis();
        String yMd =dateFormat.format(new Date(now));
        System.out.println(yMd);
        String customFormat2="HH:mm";
        SimpleDateFormat dateFormat2 = new SimpleDateFormat(customFormat2);
        String Hms = dateFormat2.format(new Date(now));
        System.out.println(Hms);
        System.out.println(now0);
        String today = dateFormat.format(new Date());
        System.out.println("today :" + today);
        List<Sul> list = sulRepository.findAllByOrderByBnoDesc();
        for(int i=0;i<list.size();i++){
            Sul sul = list.get(i);
            System.out.println(sul.getRegdate());
            //yms != today
            if(!(sul.getRegdate().equals(today))){
                System.out.println("boardPage if 작동");
                System.out.println(sul.getRegdate());
            }
            //yms =today
            else{
                String datechange = dateFormat2.format(sul.getDate());
                sul.setRegdate(datechange);
                System.out.println(sul.getRegdate());
            }
        }
    }

    public int[] pagingbutton() {
        ArrayList<Sul> alllist = sulRepository.findAllByOrderByBnoDesc();
        int k = alllist.size();
        int n = (k - 1) / 5 + 1;
        System.out.println("n값 : " + n);
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = i + 1;
        }
        return numbers;
    }
    public int[] kindpagingbutton(String Sul) {
        ArrayList<Sul> alllist = sulRepository.findAllBySulOrderByBnoDesc(Sul);
        int k = alllist.size();
        int n = (k - 1) / 5 + 1;
        System.out.println("n값 : " + n);
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = i + 1;
        }
        return numbers;
    }
}
