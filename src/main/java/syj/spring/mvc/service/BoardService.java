package syj.spring.mvc.service;

import java.util.List;

import syj.spring.mvc.vo.BoardVO;

public interface BoardService {

	boolean newBoard(BoardVO bvo);

	List<BoardVO> readBoard(int snum);

	BoardVO readOneBoard(String bno);

}
