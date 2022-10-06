package syj.spring.mvc.service;

import java.util.List;

import syj.spring.mvc.vo.BoardVO;

public interface BoardService {

	boolean newBoard(BoardVO bvo);

	List<BoardVO> readBoard(int snum, String fkey, String fval);

	BoardVO readOneBoard(String bno);

	int endpgn(String fkey, String fval);

	boolean deleteBoard(String bno);

	boolean modifyBoard(BoardVO bvo);

}
