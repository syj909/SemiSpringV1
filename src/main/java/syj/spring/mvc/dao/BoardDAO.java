package syj.spring.mvc.dao;

import java.util.List;

import syj.spring.mvc.vo.BoardVO;

public interface BoardDAO {

	int endpgn(String fkey, String fval);

	int insertBoard(BoardVO bvo);

	List<BoardVO> selectBoard(int snum, String fkey, String fval);

	BoardVO selectOneBoard(String bno);

}
