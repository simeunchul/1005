package com.example.teamproject_main_editing.controller;

import com.example.teamproject_main_editing.domain.*;
import com.example.teamproject_main_editing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class UserController {

	final UserRepostiory userRepostiory;
	final UserService userService;
	final SulRepository sulRepository;
	final MsgRoomRepository msgRoomRepository;

	@GetMapping(value = { "/", "index" })
	public String home(Model model, HttpServletRequest req) {
		HttpSession session =req.getSession();
		//진짜코드
			Object vo = session.getAttribute("sessionVo");
			if(vo!=null){
				session.setAttribute("sessionVo",vo);
			}
			else{
				Member Tempmember = new Member();
				Tempmember.setUserId("");
				Tempmember.setUserNo(0L);
				Tempmember.setRegistNumber(0L);
				session.setAttribute("sessionVo",Tempmember);
				session.setAttribute("check",null);
			}
			return "index";
		}

		/*임시코드
		UserRequestDTO vo = new UserRequestDTO();
		vo.setUserId("실험용");
		vo.setUserName("실험용");
		session.setAttribute("check",true);
		*/

	

	@GetMapping("findIdPage")
	public String findIdPage() {
		return "findIdPage";
	}
	
	@GetMapping("findPwPage")
	public String findPwPage() {
		return "findPwPage";
	}
	
	@GetMapping("loginPage")
	public String loginPage(Model model) {
		model.addAttribute("checkPath", 0);
		return "loginPage";
	}
	
	@GetMapping("registPage")
	public String registPage() {
		return "registPage";
	}
	
	@GetMapping("myInfoPage")
	public String myInfoPage(HttpServletRequest req, HttpSession session) {
		session = req.getSession(false);
		Member vo = (Member) session.getAttribute("sessionVo");
		Long writeCnt = sulRepository.countByRegistNumber(vo.getRegistNumber());
		int intwriteCnt = writeCnt.intValue();
		session.setAttribute("cnt", intwriteCnt);
		return "myInfoPage";
	}
	
	@GetMapping("myInfoEdit")
	public String myInfoEdit() {
		return "myInfoEdit";
	}
	
	@GetMapping("deleteInfoPage")
	public String deleteInfoPage() {
		return "deleteInfoPage";
	}
	
	@GetMapping("choiceSulPage")
	public String choiceSulPage(HttpServletRequest req, Model model) {
		HttpSession session =req.getSession();
		Member vo = (Member) session.getAttribute("sessionVo");
		if(vo!=null){
			session.setAttribute("sessionVo",vo);
		}
		else{
			UserRequestDTO tempDTO = new UserRequestDTO();
			tempDTO.setUserId("");
 			session.setAttribute("sessionVo",tempDTO);

		}
		List<MsgRoom> msgRooms = msgRoomRepository.findAllRoom();
		ArrayList<Integer> check = new ArrayList<Integer>();
		if(msgRooms.size()==0){
			model.addAttribute("introomId",0);
			System.out.println("사이즈제로");
			System.out.println("introomid : 0");
		}
		else {
			for(int i = 0; i < msgRooms.size(); i++) {
				MsgRoom msgRoom = msgRooms.get(i);
				if (msgRoom.getRoomId() == null) {
					check.add(0);
				} else {
					System.out.println(msgRoom.getRoomId());
					check.add(1);
				}
			}
			if (check.contains(1)) {
				model.addAttribute("introomId", 1);
				System.out.println("introomid : 1");
			} else {
				model.addAttribute("introomId", 0);
				System.out.println("introomid : 0");
			}
		}
		if(msgRoomRepository.findmap().get(1)==null){
			model.addAttribute("roomId",0L);
			System.out.println("roomId 널");
		}
		else {
			model.addAttribute("roomId",msgRoomRepository.findmap().get(1));
			System.out.println("널아님 값은 : " + msgRoomRepository.findmap().get(1));
		}
		System.out.println("roomid : "+model.getAttribute("roomId"));
		return "choiceSulPage";
	}



	//회원가입
	@PostMapping("/regist")
	public String registAfter(@RequestParam String userName, @RequestParam String userId, @RequestParam String userPw,
							  @RequestParam("rn1") String rnString1, @RequestParam("rn2") String rnString2, HttpServletRequest req) {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		String rnString = rnString1 + rnString2;
		long rnN = Long.parseLong(rnString);
		userRequestDTO.setUserName(userName);
		userRequestDTO.setUserId(userId);
		userRequestDTO.setUserPw(userPw);
		userRequestDTO.setRegistNumber(rnN);
		Member member = new Member(userRequestDTO);
		userRepostiory.save(member);

		return "index";
	}

	@ResponseBody
	@PostMapping("/loginCheck")
	public int loginCheck(HttpServletRequest req, @RequestParam String userId, @RequestParam String userPw) {
		System.out.println("로그인체크 컨트롤러");
		int check = 0;
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserId(userId);
		System.out.println(userId);
		userRequestDTO.setUserPw(userPw);
		System.out.println(userPw);
		Optional<Member> memberOptional =userRepostiory.findByUserId(userId);
		Member member = memberOptional.orElse(new Member());
		if(member.getUserId()==null){
			System.out.println("아이디 없음");
			 return check;
		}
		if(!(member.getUserPw().equals(userPw))){
			System.out.println("비밀번호 틀림");
			 return check;
		}
		else{
			System.out.println("로그인 성공");
			HttpSession session = req.getSession();
			session.setAttribute("sessionVo",member);
			session.setAttribute("check",true);
			ArrayList<Sul> Sullist = sulRepository.findByRegistNumber(member.getRegistNumber());
			int cnt = Sullist.size();
			session.setAttribute("cnt",cnt);
			String genderString = Long.toString(member.getRegistNumber());
			System.out.println(genderString);
			String gendercheck = genderString.substring(6,7);
			System.out.println(gendercheck);
			if(gendercheck.equals("1")){
				session.setAttribute("gender","남");
			}
			if(gendercheck.equals("2")){
				session.setAttribute("gender","여");
			}
			return 1;
		}
	}

	@PostMapping("/login")
	public String loginAfter(@RequestParam String userId, @RequestParam String userPw, HttpServletRequest req,
	@RequestParam int pathChoiceNum) {
		System.out.println("로그인 컨트롤러");
		UserRequestDTO failvo = new UserRequestDTO();
		UserRequestDTO vo = new UserRequestDTO();
		vo.setUserId(userId);
		vo.setUserPw(userPw);
		failvo.setUserId("");
		failvo.setUserPw("");
		Optional<Member> login = userRepostiory.findByUserId(userId);
		Member member = login.orElse(new Member(failvo));
		vo.setUserName(member.getUserName());
		if (login.isPresent()) {
			if (login.get().getUserPw().equals(userPw)) {
				HttpSession session = req.getSession();
				vo.setRegistNumber(member.getRegistNumber());
				session.setAttribute("sessionVo", member);
				session.setAttribute("check", true);
				ArrayList<Sul> Sullist = sulRepository.findByRegistNumber(vo.getRegistNumber());
				int cnt = Sullist.size();
				session.setAttribute("cnt",cnt);
				String genderString = Long.toString(member.getRegistNumber());
				System.out.println(genderString);
				String gendercheck = genderString.substring(6,7);
				System.out.println(gendercheck);
				if(gendercheck.equals("1")){
					session.setAttribute("gender","남");
				}
				if(gendercheck.equals("2")){
					session.setAttribute("gender","여");
				}
			} else {
				return "loginPage";
			}
		} else {
			return "loginPage";
		}
		if (pathChoiceNum == 0) {
			return "index";
		} else {
			return "redirect:boardPage";
		}
	}

	@RequestMapping(value = "/logout")
	public String logoutAfter(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.invalidate();
		System.out.println("로그아웃 ㅅㄱ");
		return "index";
	}

	@GetMapping("/deleteInfo")
	public String deleteAfter(HttpServletRequest req, ExpiresFilter.XHttpServletResponse res) {
		HttpSession session = req.getSession();
		Member member = (Member) session.getAttribute("sessionVo");
		System.out.println(member.getUserId());
		int check = userService.deletservice(member.getUserId());
		if (check == 1 ) {
			System.out.println("회원탈퇴함");
			session.invalidate();
			ScriptUtils.alert(res, "회원탈퇴됨", "index");
		} else {
			System.out.println("회원탈퇴안됨 ㄷㄷ");
		}
		return "index";
	}

	@ResponseBody
	@PostMapping("/pwCheck")
	public int pwCheck(HttpServletRequest req, @RequestParam String reqPw) {
		HttpSession session = req.getSession();
		Member vo = (Member) session.getAttribute("sessionVo");
		String nowPw = vo.getUserPw();
		if (nowPw.equals(reqPw)) {
			return 0;
		} else {
			return 1;
		}
	}

	@PostMapping("/userInfoEdit")
	public String infoEdit(HttpServletRequest req, @RequestParam String userPw) {
		HttpSession session = req.getSession();
		Member vo = (Member) session.getAttribute("sessionVo");
		vo.setUserPw(userPw);
		userRepostiory.save(vo);
		System.out.println("비밀변호 변경됨..");
		return "index";
	}

	@PostMapping("/findId")
	public String findId(@RequestParam String userName, @RequestParam("rn1") String rnString1,
						 @RequestParam("rn2") String rnString2, Model model) {
		UserRequestDTO vo = new UserRequestDTO();
		String rnString = rnString1 + rnString2;
		long rnN = Long.parseLong(rnString);

		vo.setUserName(userName);
		vo.setRegistNumber(rnN);

		String findID = userService.findIdByNameRn(vo,userName);
		if (findID != null) {
			System.out.println("해당정보로 나온 아이디있음");
			model.addAttribute("inputName", userName);
			model.addAttribute("inputRn1", rnString1);
			model.addAttribute("findId", findID);
			return "findSuccessId";
		} else {
			System.out.println("그런 계정없음");
			model.addAttribute("inputName", userName);
			model.addAttribute("inputRn1", rnString1);
			return "findFailId";
		}
	}

	@PostMapping("/findPw")
	public String findPw(@RequestParam String userName, @RequestParam String userId, @RequestParam("rn1") String rnString1,
						 @RequestParam("rn2") String rnString2, Model model) {
		UserRequestDTO vo = new UserRequestDTO();
		String rnString = rnString1 + rnString2;
		long rnN = Long.parseLong(rnString);

		vo.setUserId(userId);
		vo.setUserName(userName);
		vo.setRegistNumber(rnN);

		String findPW = userService.findPwByNameRn(vo,userId);
		if (findPW != null) {
			System.out.println("해당정보로 나온 비밀번호있음");
			model.addAttribute("inputName", userName);
			model.addAttribute("inputRn1", rnString1);
			model.addAttribute("inputId", userId);
			model.addAttribute("findPw", findPW);
			return "findSuccessPw";
		} else {
			System.out.println("그런 계정없음");
			model.addAttribute("inputName", userName);
			model.addAttribute("inputRn1", rnString1);
			model.addAttribute("inputId", userId);
			return "findFailPw";
		}
	}

	@ResponseBody
	@PostMapping("/idcheck")
	public int idCheck(@RequestBody UserRequestDTO userRequestDTO) {
		System.out.println(userRequestDTO.getUserId());
		int countDupId = userService.validuatecheck(userRequestDTO.getUserId());
		return countDupId;
	}

	@ResponseBody
	@PostMapping("/rnCheck")
	public boolean rnCheck(HttpServletRequest req) {
		Long rn = Long.parseLong(req.getParameter("checkRegistNumber"));
		System.out.println(rn);
		Optional<Member> member= userRepostiory.findByRegistNumber(rn);
		System.out.println(member);
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setUserNo(-1);
		Member member1 = member.orElse(new Member(userRequestDTO));
		System.out.println(member1.getUserNo());
		if(member1.getUserNo()==null||member1.getUserNo()==0){

			System.out.println("if작동");
			return false;
		}
		else{
			System.out.println("else작동");
			return true;
		}
	}

	@ResponseBody
	@PostMapping("/sojuFood")
	public List<String> sojuFood() {
		System.out.println("소주 안주 찾으러 옴");
		/*List<Soju> menuList = sojuRepository.findAll();//임시로 이렇게 해둠 바꿀거임
		List<String> sojumenu = new ArrayList<String>();
		for (int i = 0; i < menuList.size(); i++) {
			sojumenu.add(menuList.get(i).getMenu());
			System.out.println(menuList.get(i).getMenu());
		}*/
		return null;
	}
	
	@ResponseBody
	@PostMapping("/beerFood")
	public List<String> beerFood(HttpServletRequest req) {
		/*System.out.println("맥주 안주 찾으러 옴");
		List<Beer> menuList = beerRepository.findAll();//임시로 이렇게 해둠 바꿀거임
		List<String> beermenu = new ArrayList<String>();
		for (int i = 0; i < menuList.size(); i++) {
			beermenu.add(menuList.get(i).getMenu());
			System.out.println(menuList.get(i).getMenu());
		}*/
		return null;
	}
	
	@ResponseBody
	@PostMapping("/wineFood")
	public List<String> wineFood(HttpServletRequest req) {
		/*System.out.println("와인 안주 찾으러 옴");
		List<Wine> menuList = wineRepository.findAll();//임시로 이렇게 해둠 바꿀거임
		List<String> winemenu = new ArrayList<String>();
		for (int i = 0; i < menuList.size(); i++) {
			winemenu.add(menuList.get(i).getMenu());
			System.out.println(menuList.get(i).getMenu());
		}*/
		return null;
	}
	
	@ResponseBody
	@PostMapping("/vodkaFood")
	public List<String> vodkaFood(HttpServletRequest req) {
		/*System.out.println("보드카 안주 찾으러 옴");
		List<Vodka> menuList = vodkaRepository.findAll();//임시로 이렇게 해둠 바꿀거임
		List<String> vodkamenu = new ArrayList<String>();
		for (int i = 0; i < menuList.size(); i++) {
			vodkamenu.add(menuList.get(i).getMenu());
			System.out.println(menuList.get(i).getMenu());
		}*/
		return null;
	}
	
	@ResponseBody
	@PostMapping("/whiskeyFood")
	public List<String> whiskeyFood(HttpServletRequest req) {
		/*System.out.println("위스키 안주 찾으러 옴");
		List<Whiskey> menuList = whiskeyRepository.findAll();//임시로 이렇게 해둠 바꿀거임
		List<String> whiskeymenu = new ArrayList<String>();
		for (int i = 0; i < menuList.size(); i++) {
			whiskeymenu.add(menuList.get(i).getMenu());
			System.out.println(menuList.get(i).getMenu());
		}*/
		return null;
	}
	@ResponseBody
	@PostMapping("/deleteInfo")
	public int deletemember(@RequestParam String sessionId, HttpServletRequest req){
		System.out.println(sessionId);
		Optional<Member> memberOptional = userRepostiory.findByUserId(sessionId);
		Member member =memberOptional.orElse(new Member());
		userRepostiory.delete(member);
		HttpSession session = req.getSession();
		session.invalidate();
		int check = 1;
		return check;
	}
	
	/*@GetMapping("/board")
	public String board() {
		
		return "index";
	}*/
}