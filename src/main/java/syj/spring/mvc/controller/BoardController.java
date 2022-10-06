package syj.spring.mvc.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import syj.spring.mvc.service.BoardService;
import syj.spring.mvc.utils.RecaptchaUtils;
import syj.spring.mvc.vo.BoardVO;

@Controller
public class BoardController {

	// bean 클래스로 정의한 경우 @Autowired 생략 가능
	// DI받을 변수가 둘 이상이므로 생성자로 재정의
//	@Autowired
//	private BoardService bsrv;
//	@Autowired
//	private RecaptchaUtils grcp;
	
	private BoardService bsrv;
	private RecaptchaUtils grcp;
	
	@Autowired
	public BoardController(BoardService bsrv, RecaptchaUtils grcp) {
		this.bsrv = bsrv;
		this.grcp = grcp;
	}
	
	// 로그 유형 : trace info debug warn error
	protected Logger logger = LoggerFactory.getLogger(getClass());
	

	/* 페이징 처리 */
	/* 페이징당 게시물 수 perpage : 25 */
	/* 총페이지 수: ceil(getTotalPage / perPage) */
	
	/* 페이지별 읽어올 게시글 범위*/
	/* 1page : 1번째~25번째 게시글 읽어옴 */
	/* 2page : 26번째~50번째 게시글 읽어옴 */
	/* npage : (n-1) * 25 + 1번째 ~ 25 * n번째 게시글 읽어옴 */
	
	/* 총페이지 수가 27일 때 */
	/* cpg = 1 : 1 2 3 4 5 6 7 8 9 10 */
	/* cpg = 5 : 1 2 3 4 5 6 7 8 9 10 */
	/* cpg = 9 : 1 2 3 4 5 6 7 8 9 10 */
	/* cpg = 10 : 1 2 3 4 5 6 7 8 9 10 */
	/* cpg = 11 : 11 12 13 14 15 16 17 18 19 20 */
	/* cpg = 17 : 11 12 13 14 15 16 17 18 19 20 */
	/* cpg = 23 : 21 22 23 24 25 26 27 */
	/* ((Integer.parseInt(cpg) - 1 ) / 10) * + 1; */ 
	@GetMapping("/list")
	public String list(Model m, String cpg, String fkey, String fval) {
		int perPage = 25;
		
		if (cpg==null || cpg.equals("")) cpg = "1";
		if (fkey==null) fkey = "";
		if (fval==null) fkey = "";
		int cpage = Integer.parseInt(cpg);
		int snum = (cpage- 1) * perPage;
		int stpgn = ((cpage - 1 ) / 10) * 10 + 1;

		m.addAttribute("bdlist", bsrv.readBoard(snum, fkey, fval));
		m.addAttribute("endpgn", bsrv.endpgn(fkey, fval));
		m.addAttribute("stpgn", stpgn);
		m.addAttribute("fqry", "&fkey=" + fkey + "&fval=" + fval );
		//m.addAttribute("cpg", Integer.parseInt(cpage));
		
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
	
	// captcha 작동원리
	// captcha 사용시 클라이언트가 생성한 키와
	// 서버에 설정해 둔 비밀키등을
	// google의 siteverify에서 비교해서
	// 인증에 성공하면 list로 redirect 하고
	// 그렇지 않으면 다시 write로 return
	// 질의를 위한 질의문자열을 작성
	// ?secret=비밀키&response=클라이언트응답키
	@PostMapping("/write")
	public String writeok(BoardVO bvo, String gcaptcha, RedirectAttributes rda) throws ParseException, IOException {
		String returnPage = "redirect:/write";
		//logger.info(gcaptcha);
		
		if(grcp.checkCaptcha(gcaptcha)) {
			bsrv.newBoard(bvo);
			returnPage = "redirect:/list?cpg=1";
		} else {
			rda.addFlashAttribute("bvo", bvo);
			rda.addFlashAttribute("msg", "자동가입방지 확인 실패했어요!");
		}
		
		return returnPage;
	}
	
	@GetMapping("/delete")
	public String delete(HttpSession sess, String bno) {
		String returnPage = "redirect:/list?cpg=1";
		if(sess.getAttribute("m")==null) {
			returnPage = "redirect:/login";
		}
		bsrv.deleteBoard(bno);
		
		return returnPage;
	}
	
	@GetMapping("/update")
	public String modify(HttpSession sess, String bno, Model m) {
		String returnPage = "board/update";
		if(sess.getAttribute("m")==null) {
			returnPage = "redirect:/login";
		}
		m.addAttribute("bd", bsrv.readOneBoard(bno));
		
		return returnPage;
	}
	
	@PostMapping("/update")
	public String modifyok(HttpSession sess, BoardVO bvo) {
		String returnPage = "redirect:/view?bno=" + bvo.getBno();
		if(sess.getAttribute("m")==null) {
			returnPage = "redirect:/login";
		}
		bsrv.modifyBoard(bvo);
		
		return returnPage;
	}
}
