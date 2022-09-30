package syj.spring.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import syj.spring.mvc.service.BoardService;
import syj.spring.mvc.vo.BoardVO;

@Controller
public class BoardController {

	// bean 클래스로 정의한 경우 @Autowired 생략 가능
	@Autowired
	private BoardService bsrv;

	// 로그 유형 : trace info debug warn error
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/list")
	public String list() {
		return "board/list";
	}
	
	@GetMapping("/view")
	public String view() {
		return "board/view";
	}
	
	@GetMapping("/write")
	public String write() {
		return "board/write";
	}
	
	@PostMapping("/write")
	public String writeok(BoardVO bvo) {
		logger.info("writeok 호출! {}", bvo);
		
		// 회원정보 저장
		if(bsrv.newBoard(bvo)) logger.info("글작성 성공");
		
		return "redirect:/list";
	}
}
