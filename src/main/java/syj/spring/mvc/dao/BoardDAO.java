package syj.spring.mvc.dao;

import java.util.List;

import syj.spring.mvc.vo.BoardVO;

public interface BoardDAO {

	int endpgn();

	int insertBoard(BoardVO bvo);

	List<BoardVO> selectBoard(int snum);

	BoardVO selectOneBoard(String bno);

}
