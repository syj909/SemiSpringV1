package syj.spring.mvc.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import syj.spring.mvc.service.BoardService;
import syj.spring.mvc.vo.BoardVO;

@Controller
public class BoardController {

	// bean 클래스로 정의한 경우 @Autowired 생략 가능
	@Autowired
	private BoardService bsrv;

	// 로그 유형 : trace info debug warn error
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/* 페이징 처리 */
	/* 페이징당 게시물 수 perpage : 25 */
	/* 총페이지 수: ceil(getTotalPage / perPage) */
	
	/* 페이지별 읽어올 게시글 범위*/
	/* 1page : 1번째~25번째 게시글 읽어옴 */
	/* 2page : 26번째~50번째 게시글 읽어옴 */
	/* npage : (n-1) * 25 + 1번째 ~ 25 * n번째 게시글 읽어옴 */
	@GetMapping("/list")
	public String list(Model m, String cpg) {
		int perPage = 25;
		int snum = (Integer.parseInt(cpg)- 1) * perPage;
		
		m.addAttribute("bdlist", bsrv.readBoard(snum));
		
		return "board/list";
	}
	
	@GetMapping("/view")
	public ModelAndView view(ModelAndView mv, String bno) {
		mv.setViewName("board/view");
		mv.addObject("bd", bsrv.readOneBoard(bno));
		return mv;
	}
	
	@GetMapping("/write")
	public String write(Model m, HttpSession sess) {
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
