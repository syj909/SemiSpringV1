package syj.spring.mvc.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
	
	public BoardDAOImpl (DataSource datasource) {
		simpleInsert = new SimpleJdbcInsert(datasource).withTableName("board").usingColumns("title","userid","contents");
		
		jdbcNamedTemplate = new NamedParameterJdbcTemplate(datasource);
	}
	
	@Override
	public int insertBoard(BoardVO bvo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(bvo);
		
		return simpleInsert.execute(params);
	}

	@Override
	public List<BoardVO> selectBoard(int snum) {
		String sql = "select bno, title, userid, regdate, views from board" + " order by bno desc limit :snum, 25";
		
		Map<String, Object> params = new HashMap<>();
		params.put("snum", snum);
		
		return jdbcNamedTemplate.query(sql, params, boardMapper);
	}

	@Override
	public BoardVO selectOneBoard(String bno) {
		// 본문 글에 대한 조회수 증가시키기
		String sql = "update board set views = views + 1 where bno = ?";
		
		Object[] param = {bno};
		jdbcTemplate.update(sql, param);
		// 본문글 가져오기
		sql = "select * from board where bno = ?";
		
//		RowMapper<BoardVO> boardMapper = (rs, num) -> {
//			BoardVO b = new BoardVO();
//
//			b.setTitle(rs.getString("title"));
//			b.setUserid(rs.getString("userid"));
//			b.setContents(rs.getString("contents"));
//			b.setRegdate(rs.getString("regdate"));
//			
//			return b;	
//		};
		
		return jdbcTemplate.queryForObject(sql, param, boardMapper);
	}

	@Override
	public int endpgn() {
		String sql = "select ceil(count(bno)/25) cnt from board";
		
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

}
