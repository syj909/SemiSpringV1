package syj.spring.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import syj.spring.mvc.dao.BoardDAO;
import syj.spring.mvc.vo.BoardVO;

@Service("bsrv")
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDAO bdao;
	
	@Override
	public boolean newBoard(BoardVO bvo) {
		boolean isInsert = false;
		
		// 글작성이 성공했으면 true를 리턴
		if(bdao.insertBoard(bvo) > 0) isInsert = true;
		return false;
	}

	@Override
	public List<BoardVO> readBoard(int snum) {

		
		return bdao.selectBoard(snum);
	}

	@Override
	public BoardVO readOneBoard(String bno) {
		return bdao.selectOneBoard(bno);
	}

	@Override
	public int endpgn() {
		return bdao.endpgn();
	}

}
