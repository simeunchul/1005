package com.example.teamproject_main_editing.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.teamproject_main_editing.domain.*;
import com.example.teamproject_main_editing.service.SulService;
import com.example.teamproject_main_editing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RequiredArgsConstructor
@Controller
public class BoardController {

	private final SulRepository sulRepository;
	private final UserRepostiory userRepostiory;
	private final SulService sulService;
	private final UserService userService;
	private final PostboardRepository postboardRepository;

	@GetMapping("/{boardkind}boardPage")
	public ModelAndView boardPage(@PathVariable("boardkind") String boardkind, Model model,  HttpServletRequest req) throws Exception {
		ModelAndView mav = new ModelAndView("/boardPage");

		Long total = sulRepository.countBy();
		int inttotal = total.intValue();
		System.out.println("등록된 게시글 수 = " + inttotal);

		//통합게시판
		if(boardkind.isEmpty()) {
			//페이징번호늘리기
			int [] numbers =sulService.pagingbutton();
			//데이트포멧 오늘과같으면 시간으로 오늘과 다르면 날짜로표기
			sulService.Dateformatchange();
			List<Sul> fivelikelist = sulRepository.findTop5ByOrderByLikeBtnDesc();
			List<Sul> fivelist = sulRepository.findTop5ByOrderByDateDesc();
			mav.addObject("boardname", "통합");
			mav.addObject("boardregistory","");
			mav.addObject("numbers", numbers);
			mav.addObject("list", fivelist);
			mav.addObject("allBoardLike", fivelikelist);

		}
		//소주게시판
		if(boardkind.equals("soju")){
			//페이징번호늘리기
			int [] numbers =sulService.kindpagingbutton("소주");
			//
			sulService.Dateformatchange();
			List<Sul> fivelikelist = sulRepository.findTop5BySulOrderByLikeBtnDesc("소주");
			List<Sul> fivelist = sulRepository.findTop5BySulOrderByDateDesc("소주");
			mav.addObject("boardname", "소주");
			mav.addObject("boardregistory","soju");
			mav.addObject("numbers", numbers);
			mav.addObject("list", fivelist);
			mav.addObject("allBoardLike", fivelikelist);
		}
		//맥주게시판
		if(boardkind.equals("beer")){
			//페이징번호늘리기
			int [] numbers =sulService.kindpagingbutton("맥주");
			//
			sulService.Dateformatchange();
			List<Sul> fivelikelist = sulRepository.findTop5BySulOrderByLikeBtnDesc("맥주");
			List<Sul> fivelist = sulRepository.findTop5BySulOrderByDateDesc("맥주");
			mav.addObject("boardname", "맥주");
			mav.addObject("boardregistory","beer");
			mav.addObject("numbers", numbers);
			mav.addObject("list", fivelist);
			mav.addObject("allBoardLike", fivelikelist);
		}
		//와인게시판
		if(boardkind.equals("wine")){
			//페이징번호늘리기
			int [] numbers =sulService.kindpagingbutton("와인");
			//
			sulService.Dateformatchange();
			List<Sul> fivelikelist = sulRepository.findTop5BySulOrderByLikeBtnDesc("와인");
			List<Sul> fivelist = sulRepository.findTop5BySulOrderByDateDesc("와인");
			mav.addObject("boardname", "와인");
			mav.addObject("boardregistory","wine");
			mav.addObject("numbers", numbers);
			mav.addObject("list", fivelist);
			mav.addObject("allBoardLike", fivelikelist);
		}
		//보드카게시판
		if(boardkind.equals("vodka")){
			//페이징번호늘리기
			int [] numbers =sulService.kindpagingbutton("보드카");
			//
			sulService.Dateformatchange();
			List<Sul> fivelikelist = sulRepository.findTop5BySulOrderByLikeBtnDesc("보드카");
			List<Sul> fivelist = sulRepository.findTop5BySulOrderByDateDesc("보드카");
			mav.addObject("boardname", "보드카");
			mav.addObject("boardregistory","vodka");
			mav.addObject("numbers", numbers);
			mav.addObject("list", fivelist);
			mav.addObject("allBoardLike", fivelikelist);
		}
		//위스키게시판
		if(boardkind.equals("whiskey")){
			//페이징번호늘리기
			int [] numbers =sulService.kindpagingbutton("위스키");
			//
			sulService.Dateformatchange();
			List<Sul> fivelikelist = sulRepository.findTop5BySulOrderByLikeBtnDesc("위스키");
			List<Sul> fivelist = sulRepository.findTop5BySulOrderByDateDesc("위스키");
			mav.addObject("boardname", "위스키");
			mav.addObject("boardregistory","whiskey");
			mav.addObject("numbers", numbers);
			mav.addObject("list", fivelist);
			mav.addObject("allBoardLike", fivelikelist);
		}
		if(boardkind.equals("free")){
			//페이징번호늘리기
			int [] numbers =sulService.kindpagingbutton("자유");
			//
			sulService.Dateformatchange();
			List<Sul> fivelikelist = sulRepository.findTop5BySulOrderByLikeBtnDesc("자유");
			List<Sul> fivelist = sulRepository.findTop5BySulOrderByDateDesc("자유");
			mav.addObject("boardname", "자유");
			mav.addObject("boardregistory","free");
			mav.addObject("numbers", numbers);
			mav.addObject("list", fivelist);
			mav.addObject("allBoardLike", fivelikelist);
		}


		return mav;
	}

	@GetMapping("{boardkind}boardPage{idx}")
	@ResponseBody
	public ModelAndView boardPageidx(@PathVariable("idx") int idx,@PathVariable("boardkind") String boardkind,Model model,HttpServletRequest req) {
		ModelAndView mav = new ModelAndView("/boardPage");

		ArrayList<Sul> likelist = sulRepository.findAllByOrderByLikeBtnDesc();

		//데이터 포멧변경
		sulService.Dateformatchange();
		//데이터 포멧 변경

		//페이징번호늘리기
		if(boardkind.isEmpty()) {
			List<Sul> alllist = sulRepository.findAllByOrderByBnoDesc();
			int[] numbers = sulService.pagingbutton();
			System.out.println(idx);
			int f = (idx) * 5;
			System.out.println(f);
			int g = (idx - 1) * 5;
			System.out.println(g);
			List<Sul> fivebnolist = new ArrayList<Sul>();
			List<Sul> fivelikelist = sulRepository.findTop5ByOrderByLikeBtnDesc();
			if (f < alllist.size()) {
				for (int i = g; i < f; i++) {
					Sul sul = alllist.get(i);
					fivebnolist.add(sul);
				}
			} else {
				for (int i = g; i < alllist.size(); i++) {
					Sul sul = alllist.get(i);
					fivebnolist.add(sul);
				}
			}
			mav.addObject("boardname", "통합");
			mav.addObject("boardregistory","");
			model.addAttribute("numbers", numbers);
			model.addAttribute("allBoardLike", fivelikelist);
			model.addAttribute("list", fivebnolist);
		}

		//소주페이징
		if(boardkind.equals("soju")){
			List<Sul> alllist = sulRepository.findAllBySulOrderByBnoDesc("소주");
			int[] numbers = sulService.kindpagingbutton("소주");
			System.out.println(idx);
			int f = (idx) * 5;
			System.out.println(f);
			int g = (idx - 1) * 5;
			System.out.println(g);
			List<Sul> fivebnolist = new ArrayList<Sul>();
			List<Sul> fivelikelist = sulRepository.findTop5BySulOrderByLikeBtnDesc("소주");
			if (f < alllist.size()) {
				for (int i = g; i < f; i++) {
					Sul sul = alllist.get(i);
					fivebnolist.add(sul);
				}
			} else {
				for (int i = g; i < alllist.size(); i++) {
					Sul sul = alllist.get(i);
					fivebnolist.add(sul);
				}
			}
			mav.addObject("boardname", "소주");
			mav.addObject("boardregistory","soju");
			model.addAttribute("numbers", numbers);
			model.addAttribute("allBoardLike", fivelikelist);
			model.addAttribute("list", fivebnolist);
		}

		//맥주페이징
		if(boardkind.equals("beer")){
			List<Sul> alllist = sulRepository.findAllBySulOrderByBnoDesc("맥주");
			int[] numbers = sulService.kindpagingbutton("맥주");
			System.out.println(idx);
			int f = (idx) * 5;
			System.out.println(f);
			int g = (idx - 1) * 5;
			System.out.println(g);
			List<Sul> fivebnolist = new ArrayList<Sul>();
			List<Sul> fivelikelist = sulRepository.findTop5BySulOrderByLikeBtnDesc("맥주");
			if (f < alllist.size()) {
				for (int i = g; i < f; i++) {
					Sul sul = alllist.get(i);
					fivebnolist.add(sul);
				}
			} else {
				for (int i = g; i < alllist.size(); i++) {
					Sul sul = alllist.get(i);
					fivebnolist.add(sul);
				}
			}
			mav.addObject("boardname", "맥주");
			mav.addObject("boardregistory","beer");
			model.addAttribute("numbers", numbers);
			model.addAttribute("allBoardLike", fivelikelist);
			model.addAttribute("list", fivebnolist);
		}

		//와인페이징
		if(boardkind.equals("wine")){
			List<Sul> alllist = sulRepository.findAllBySulOrderByBnoDesc("와인");
			int[] numbers = sulService.kindpagingbutton("와인");
			System.out.println(idx);
			int f = (idx) * 5;
			System.out.println(f);
			int g = (idx - 1) * 5;
			System.out.println(g);
			List<Sul> fivebnolist = new ArrayList<Sul>();
			List<Sul> fivelikelist = sulRepository.findTop5BySulOrderByLikeBtnDesc("와인");
			if (f < alllist.size()) {
				for (int i = g; i < f; i++) {
					Sul sul = alllist.get(i);
					fivebnolist.add(sul);
				}
			} else {
				for (int i = g; i < alllist.size(); i++) {
					Sul sul = alllist.get(i);
					fivebnolist.add(sul);
				}
			}
			mav.addObject("boardname", "와인");
			mav.addObject("boardregistory","wine");
			model.addAttribute("numbers", numbers);
			model.addAttribute("allBoardLike", fivelikelist);
			model.addAttribute("list", fivebnolist);
		}

		//보드카페이징
		if(boardkind.equals("vodka")){
			List<Sul> alllist = sulRepository.findAllBySulOrderByBnoDesc("보드카");
			int[] numbers = sulService.kindpagingbutton("보드카");
			System.out.println(idx);
			int f = (idx) * 5;
			System.out.println(f);
			int g = (idx - 1) * 5;
			System.out.println(g);
			List<Sul> fivebnolist = new ArrayList<Sul>();
			List<Sul> fivelikelist = sulRepository.findTop5BySulOrderByLikeBtnDesc("보드카");
			if (f < alllist.size()) {
				for (int i = g; i < f; i++) {
					Sul sul = alllist.get(i);
					fivebnolist.add(sul);
				}
			} else {
				for (int i = g; i < alllist.size(); i++) {
					Sul sul = alllist.get(i);
					fivebnolist.add(sul);
				}
			}
			mav.addObject("boardname", "보드카");
			mav.addObject("boardregistory","vodka");
			model.addAttribute("numbers", numbers);
			model.addAttribute("allBoardLike", fivelikelist);
			model.addAttribute("list", fivebnolist);
		}

		//위스키페이징
		if(boardkind.equals("whiskey")){
			List<Sul> alllist = sulRepository.findAllBySulOrderByBnoDesc("위스키");
			int[] numbers = sulService.kindpagingbutton("위스키");
			System.out.println(idx);
			int f = (idx) * 5;
			System.out.println(f);
			int g = (idx - 1) * 5;
			System.out.println(g);
			List<Sul> fivebnolist = new ArrayList<Sul>();
			List<Sul> fivelikelist = sulRepository.findTop5BySulOrderByLikeBtnDesc("위스키");
			if (f < alllist.size()) {
				for (int i = g; i < f; i++) {
					Sul sul = alllist.get(i);
					fivebnolist.add(sul);
				}
			} else {
				for (int i = g; i < alllist.size(); i++) {
					Sul sul = alllist.get(i);
					fivebnolist.add(sul);
				}
			}
			mav.addObject("boardname", "위스키");
			mav.addObject("boardregistory","whiskey");
			model.addAttribute("numbers", numbers);
			model.addAttribute("allBoardLike", fivelikelist);
			model.addAttribute("list", fivebnolist);
		}

		//자유페이징
		if(boardkind.equals("free")){
			List<Sul> alllist = sulRepository.findAllBySulOrderByBnoDesc("자유");
			int[] numbers = sulService.kindpagingbutton("자유");
			System.out.println(idx);
			int f = (idx) * 5;
			System.out.println(f);
			int g = (idx - 1) * 5;
			System.out.println(g);
			List<Sul> fivebnolist = new ArrayList<Sul>();
			List<Sul> fivelikelist = sulRepository.findTop5BySulOrderByLikeBtnDesc("자유");
			if (f < alllist.size()) {
				for (int i = g; i < f; i++) {
					Sul sul = alllist.get(i);
					fivebnolist.add(sul);
				}
			} else {
				for (int i = g; i < alllist.size(); i++) {
					Sul sul = alllist.get(i);
					fivebnolist.add(sul);
				}
			}
			mav.addObject("boardname", "자유");
			mav.addObject("boardregistory","free");
			model.addAttribute("numbers", numbers);
			model.addAttribute("allBoardLike", fivelikelist);
			model.addAttribute("list", fivebnolist);
		}
		return mav;
	}

	@GetMapping("singleBoardPage{idx}")
	@ResponseBody
	public ModelAndView boardPage(Model model
			, SulRequestDTO sulVo,  @PathVariable("idx") Long idx, HttpServletRequest req) throws Exception {
		System.out.println("싱글작동");
		ModelAndView mav = new ModelAndView("/singleBoardPage");
		HttpSession session = req.getSession();;
		Member member = (Member) session.getAttribute("sessionVo");
		ArrayList<Sul> alLike = sulRepository.findTop5ByOrderByLikeBtnDesc();
		model.addAttribute("allBoardLike",alLike);
		Long total = sulRepository.countBy();
		int inttotal = total.intValue();
		List<Sul> list = sulRepository.findTop12ByOrderByBnoDesc();
		mav.addObject("list", list);
		Optional<Sul> sulOptional = sulRepository.findByBno(idx);
		Sul sul = sulOptional.orElse(new Sul());
		ArrayList<Postboard> postboards = postboardRepository.findPostboardByBnoOrderByPnoDesc(idx);
		System.out.println(postboards);
		//조회수1증가
		sul.setClickCnt(sul.getClickCnt()+1);
		sulRepository.save(sul);
		// 목록에서 색깔로 위치표시하기위함
		model.addAttribute("commntlist",postboards);
		model.addAttribute("idx", idx);
		model.addAttribute("showBoard", sul);
		return mav;
	}

	@GetMapping("boardLoginPage")
	public String loginPage(Model model) {
		System.out.println("게시판에세 로그인하러감");
		model.addAttribute("checkPath", 990122);
		return "loginPage";
	}

	@RequestMapping(value = "/boardLogout")
	public String logoutAfter(HttpSession session, HttpServletRequest req) {
		session = req.getSession();
		session.invalidate();
		Member Tempmember = new Member();
		Tempmember.setUserId("");
		Tempmember.setRegistNumber(0L);
		Tempmember.setUserNo(0L);
		session = req.getSession();
		session.setAttribute("sessionVo",Tempmember);
		session.setAttribute("check",null);
		System.out.println("게시판에서 로그아웃 ㅅㄱ");
		return "redirect:/boardPage";
	}
	/*
	@GetMapping("myBoardPage")
	@ResponseBody
	public ModelAndView myBoardPage(HttpSession session, UserRequestDTO vo) throws Exception {
		ModelAndView mav = new ModelAndView("/myBoardPage");
		Member member = (Member) session.getAttribute("sessionVo");

		Long total = sulRepository.countByRegistNumber(member.getRegistNumber());

		System.out.println("등록된 게시글 수 = " + total);

		List<Sul> list = sulRepository.findByRegistNumber(member.getRegistNumber());
		mav.addObject("list", list);

		return mav;
	}*/
	@GetMapping("myBoardPage{writer}")
	@ResponseBody
	public ModelAndView myBoardPageid(HttpSession session, UserRequestDTO vo, @PathVariable String writer) throws Exception {
		System.out.println("마이보드컨트롤러");
		ModelAndView mav = new ModelAndView("/myBoardPage");
		Member member = (Member) session.getAttribute("sessionVo");
		ArrayList<Sul> sul = sulRepository.findAllByWriterOrderByBnoDesc(writer);

		int inttotal = sul.size();
		System.out.println("등록된 게시글 수 = " + inttotal);
		mav.addObject("list", sul);

		return mav;
	}
	/*
	@GetMapping("otherBoardPage")
	@ResponseBody
	public ModelAndView otherBoardPage(UserRequestDTO vo,
									   @RequestParam Long idx) throws Exception {
		ModelAndView mav = new ModelAndView("/otherBoardPage");

		System.out.println("남의 게시판 bno는? " + idx);
		Long regNum = sulService.findSameBno(idx);

		System.out.println(regNum);
		Optional<Member> member = userRepostiory.findByRegistNumber(regNum);

		Long total = sulRepository.countByRegistNumber(regNum);
		System.out.println("이사람의 총 게시글 수는?" + total);

		int inttotal = total.intValue();

		List<Sul> list = sulRepository.findFirst12ByRegistNumberOrderByBnoDesc(regNum);
		mav.addObject("list", list);

		return mav;
	}*/

	@GetMapping("otherBoardPage{writer}")
	@ResponseBody
	public ModelAndView otherBoardPageidx(UserRequestDTO vo,
									   @PathVariable String writer) throws Exception {
		ModelAndView mav = new ModelAndView("/otherBoardPage");

		System.out.println("아더보드컨트롤러");
		ArrayList<Sul> sul = sulRepository.findAllByWriterOrderByBnoDesc(writer);

		int inttotal = sul.size();
		System.out.println("등록된 게시글 수 = " + inttotal);
		mav.addObject("list", sul);

		return mav;
	}

	@RequestMapping(value = "/createBoard")
	public String createBoard() {
		System.out.println("글쓰러 가기");
		return "createBoard";
	}

	@GetMapping(value = "/insertBoard")
	public String insertBoard(HttpSession session, UserRequestDTO vo, HttpServletRequest req,
							  SulRequestDTO sulvo, @RequestParam String choiceSulType, @RequestParam String title,
							  @RequestParam String menu, @RequestParam String content, Model model) {
		System.out.println("게시글 등록하러 옴");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		HttpSession session1 = req.getSession();
		Member member = (Member) session1.getAttribute("sessionVo");
		sulvo.setSul(choiceSulType);
		sulvo.setWriter(member.getUserName());
		sulvo.setRegistNumber(member.getRegistNumber());
		sulvo.setMenu(menu);
		sulvo.setTitle(title);
		sulvo.setContent(content);
		sulvo.setDate(timestamp);
		sulvo.setClickCnt(0L);
		String customFormat="yyyy.MM.dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(customFormat);
		String customFormat2="HH:mm";
		SimpleDateFormat dateFormat2 = new SimpleDateFormat(customFormat2);
		String regdate = dateFormat.format(timestamp);
		String Hms = dateFormat2.format(timestamp);
		sulvo.setRegdate(regdate);
		session1.setAttribute("sultimestamp", Hms);
		System.out.println((String)model.getAttribute("sultimestamp"));
		Sul sul = new Sul(sulvo);
		sulRepository.save(sul);
		System.out.println("셋데이트 값 : "  + sulvo.getDate());
		System.out.println("술의 셋데이트 값 : "  + sul.getDate());

		return "redirect:/boardPage";
	}

	@RequestMapping(value = "/changeBoard")
	public String changeBoard(@RequestParam long editBoardBno, SulRequestDTO sul,
			Model model) {
		System.out.println("글 수정하러 가기");
		Optional<Sul> sul1 = sulRepository.findByBno(editBoardBno);
		Sul sul2 = sul1.orElse(new Sul());
		model.addAttribute("boardForEdit",sul2);
		return "changeBoard";
	}

	@GetMapping(value = "/updateBoard")
	public String updateBoard(HttpSession session, UserRequestDTO vo, HttpServletRequest req,
			SulRequestDTO sulvo, @RequestParam String choiceSulType, @RequestParam String title,
			@RequestParam String menu, @RequestParam String content, @RequestParam long hiddenBnoAway) {
		System.out.println("게시글 수정하러 옴");
		Member member = (Member) session.getAttribute("sessionVo");
		Optional<Sul> sulOptional = sulRepository.findByBno(hiddenBnoAway);
		Sul sul = sulOptional.orElse(new Sul());
		sulvo.setWriter(sul.getWriter());
		sulvo.setRegistNumber(sul.getRegistNumber());
		sulvo.setBno(sul.getBno());
		sulvo.setSul(choiceSulType);
		sulvo.setMenu(menu);
		sulvo.setTitle(title);
		sulvo.setContent(content);
		sulvo.setRegdate(sul.getRegdate());
		sulvo.setDate(sul.getDate());
		sulvo.setClickCnt(sul.getClickCnt());
		String customFormat="yyyy.MM.dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(customFormat);
		long now = System.currentTimeMillis();
		String yMd =dateFormat.format(new Date(now));
		sulvo.setEditdate(yMd);
		sul.update(sulvo);
		sulRepository.save(sul);



		return "redirect:/boardPage";
	}

	@RequestMapping(value = "/deleteBoard")
	public String deleteBoard(@RequestParam long deleteBoardBno, SulRequestDTO sul) {
		Optional<Sul> sulOptional = sulRepository.findByBno(deleteBoardBno);
		Sul sul1 = sulOptional.orElse(new Sul());
		sulRepository.delete(sul1);
		return "redirect:/boardPageAfter";
	}

	@GetMapping("boardPageAfter")
	public ModelAndView boardPageAfter(HttpServletResponse res, Model model) throws Exception {
		ModelAndView mav = new ModelAndView("/boardPage");
		ScriptUtils.alertJust(res, "글이 삭제 되었습니다...");
		ArrayList<Sul> alLike = sulRepository.findAllByOrderByLikeBtnDesc();
		model.addAttribute("allBoardLike",alLike);
		Long total = sulRepository.countBy();
		int inttotal = total.intValue();
		System.out.println("등록된 게시글 수 = " + inttotal);

		List<Sul> list = sulRepository.findTop12ByOrderByBnoDesc();
		mav.addObject("list", list);

		for (int i = 0; i < list.size(); i++) {
			list.get(i).getMenu();
		}

		return mav;
	}


	@ResponseBody
	@GetMapping("increaseCnt")
	public Long increaseCnt(@RequestParam long idx){

		return null;
	}
	@ResponseBody
	@PostMapping("likebtn")
	public Sul increaselike(@RequestParam long bno,@RequestParam long likeBtn, @RequestParam long sessionNo){
		System.out.println(sessionNo);
		System.out.println(bno);
		System.out.println(likeBtn);
		Optional<Member> memberOptional = userRepostiory.findByUserNo(sessionNo);
		Member member = memberOptional.orElse(new Member());
		ArrayList<Long> likeboard = userService.findlikeboard(sessionNo);
		Optional<Sul> sulOptional = sulRepository.findByBno(bno);
		Sul sul = sulOptional.orElse(new Sul());
		System.out.println("증감전 좋아요" + sul.getLikeBtn());
		if(sessionNo==0){
			sul.setBno(0);
			return sul;
		}
		if(!(likeboard.contains(bno))){
			System.out.println("좋아요 1증가");
			userService.addlikeboard(sessionNo,bno);
			sul.setLikeBtn(sul.getLikeBtn()+1);
			sulRepository.save(sul);
			System.out.println("세이브후 라이크버튼"+ sul.getLikeBtn());
			return sul;
		}
		if(likeboard.contains(bno)){
			System.out.println("좋아요 1감소");
			userService.removelikeboard(sessionNo,bno);
			sul.setLikeBtn(sul.getLikeBtn()-1);
			sulRepository.save(sul);
			return sul;
		}
		return sul;
	}
	//좋아요되어있는지 체크
	@ResponseBody
	@PostMapping("likebtncheck")
	public SulRequestDTO likebtncheck(@RequestParam long bno, @RequestParam long sessionNo){
		System.out.println("bno값" + bno);
		System.out.println("sessionNo값" + sessionNo);
		ArrayList<Long> likeboard = userService.findlikeboard(sessionNo);
		Optional<Sul> sulOptional = sulRepository.findByBno(bno);
		Sul sul = sulOptional.orElse(new Sul());
		long likebtn = sul.getLikeBtn();
		long check=10;
		//이미 좋아요함
		if(likeboard.contains(bno)){
			check = 0;
		}
		//좋아요 하지 않은 게시물
		if(!(likeboard.contains(bno))){
			check = 1;
		}
		SulRequestDTO sulRequestDTO = new SulRequestDTO();
		sulRequestDTO.setLikeBtn(likebtn);
		sulRequestDTO.setClickCnt(check);

		return sulRequestDTO;
	}

	@ResponseBody
	@PostMapping("insertComment")
	public int CreateComment(Model model, @RequestParam long bno, @RequestParam String writer, @RequestParam String content){
		System.out.println("댓글작성 작동!");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Postboard postboard = new Postboard();
		postboard.setBno(bno);
		postboard.setWriter(writer);
		postboard.setContent(content);
		postboard.setDate(timestamp);
		String customFormat="yyyy.MM.dd";
		SimpleDateFormat dateFormat = new SimpleDateFormat(customFormat);
		String regdate = dateFormat.format(timestamp);
		postboard.setRegdate(regdate);
		postboardRepository.save(postboard);
		System.out.println(postboard.getBno());
		ArrayList<Postboard> commentlist = postboardRepository.findPostboardByBnoOrderByPnoDesc(postboard.getBno());
		System.out.println(commentlist);
		model.addAttribute("commentlist",commentlist);
		return 0;
	}
}
