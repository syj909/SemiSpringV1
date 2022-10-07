package syj.spring.mvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import syj.spring.mvc.service.MemberService;
import syj.spring.mvc.vo.MemberVO;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService msrv;

	// 로그 유형 : trace info debug warn error
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/join")
	public String join(HttpSession sess) {
		String returnPage = "join/join";
		
		if(sess.getAttribute("m") != null) {
			returnPage = "redirect:/myinfo";
		} 
		return returnPage;
	}
	
	@PostMapping("/join")
	public String joinok(MemberVO mvo) {
		logger.info("joinok 호출! {}", mvo);
		
		// 회원정보 저장
		if(msrv.newMember(mvo)) logger.info("회원가입 성공");
		
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "join/login";
	}
	
	@PostMapping("/login") // 로그인처리
	public String loginok(MemberVO mvo, HttpSession sess) {
		String returnPage = "join/lgnfail";
		
		if(msrv.checkLogin(mvo)) {
			sess.setAttribute("m", mvo);
			returnPage = "redirect:/myinfo";	
		}
		
		
		return returnPage;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession sess) {
		
		sess.invalidate();
		
		return "redirect:/";
	}
	
	// 로그인 상태가 아니면 -> redirect:/login
	// 로그인 상태라면 -> join/myinfo
	@GetMapping("/myinfo")
	public String myinfo(Model m, HttpSession sess) {
		String returnPage = "join/myinfo";
	
		if(sess.getAttribute("m") != null) {
			MemberVO mvo = (MemberVO) sess.getAttribute("m");
			m.addAttribute("mbr", msrv.readOneMember(mvo.getUserid()));
		} else {
			returnPage = "redirect:/login";
		}
		return returnPage;
	}
	
	// 아이디 중복검사 - REST api 이용
	// 요청 URL : /checkuid?uid=검사할아이디
	// 결과 = 0: 아이디 사용가능
	// 결과 = 1: 아이디 사용불가
	@ResponseBody
	@GetMapping("/checkuid")
	public String checkuid(String uid) {
		String result = "잘못된 방식으로 호출했습니다.";
		if(uid!=null || !uid.equals("")) {
			result = msrv.checkuid(uid);
		}
		return result;
	}
	
	// 우편번호 검색
	// 요청 URL : /findzip?dong=조회할_동이름
	// 요청결과 : JSON 객체
	@ResponseBody
	@GetMapping("/findzip")
	public void findzip(String dong, HttpServletResponse res) throws IOException {
		// 응답유형을 json으로 설정
		res.setContentType("application/json; charset=UTF-8");
		
		// 응답결과를 뷰없이 브라우저로 바로 출력
		res.getWriter().print(msrv.findZipcode(dong));
	}
	
}
