package syj.spring.mvc.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import syj.spring.mvc.vo.BoardVO;
import syj.spring.mvc.vo.MemberVO;

@Repository("bdao")
public class BoardDAOImpl implements BoardDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleInsert;
	private NamedParameterJdbcTemplate jdbcNamedTemplate;
	private RowMapper<BoardVO> boardMapper = BeanPropertyRowMapper.newInstance(BoardVO.class);
	
	@Autowired
	SqlSession sqlSession;
	
	public BoardDAOImpl (DataSource datasource) {
		simpleInsert = new SimpleJdbcInsert(datasource).withTableName("board").usingColumns("title","userid","contents");
		
		jdbcNamedTemplate = new NamedParameterJdbcTemplate(datasource);
	}
	
	@Override
	public int insertBoard(BoardVO bvo) {
		return sqlSession.insert("board.insertBoard", bvo);
	}

	// 동적 질의문
	// 조건에 따라 실행할 질의문의 형태가 바뀌는 것
	// 제목으로 검색 : select * from board where title = ?
	// 작성자로 검색 : select * from board where userid = ?
	// 본문으로 검색 : select * from board where contents = ?
	// => select * from board where ? = ? (실행x)
	// 테이블명, 컬럼명은 매개변수화할 수 없음.
	@Override
	public List<BoardVO> selectBoard(int snum, String fkey, String fval) {
		Map<String, Object> params = new HashMap<>();
		params.put("snum", snum);
		params.put("fkey", fkey);
		params.put("fval", "%" + fval + "%");
		
		return sqlSession.selectList("board.selectBoard", params);
	}

	@Override
	public BoardVO selectOneBoard(String bno) {
		// 조회수 증가
		sqlSession.update("board.increaseBoardViews", bno);
		// 게시글 보기
		return sqlSession.selectOne("board.selectOneBoard", bno);
	}

	@Override
	public int endpgn(String fkey, String fval) {
		Map<String, Object> params = new HashMap<>();
		params.put("fkey", fkey);
		params.put("fval", "%" + fval + "%");
		
		return sqlSession.selectOne("board.endpgn", params);
	}

	@Override
	public int deleteBoard(String bno) {
		return sqlSession.delete("board.deleteBoard", bno);
	}

	@Override
	public int updateBoard(BoardVO bvo) {
		return sqlSession.update("board.updateBoard", bvo);
	}

}
