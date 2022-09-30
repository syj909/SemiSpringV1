package syj.spring.mvc.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import syj.spring.mvc.vo.BoardVO;
import syj.spring.mvc.vo.MemberVO;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO{

	/*
	 * @Autowired private JdbcTemplate jdbcTemplate;
	 * 
	 * @Override public int insertMember(MemberVO mvo) { String sql =
	 * "insert into member" + "(userid, passwd, name, email) " +
	 * "values(?, ?, ?, ?)"; Object[] params = new Object[] { mvo.getUserid(),
	 * mvo.getPasswd(), mvo.getEmail(), mvo.getEmail() };
	 * 
	 * return jdbcTemplate.update(sql, params); }
	 */
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleInsert;
	
	public MemberDAOImpl (DataSource datasource) {
		simpleInsert = new SimpleJdbcInsert(datasource).withTableName("member").usingColumns("userid","passwd","name", "email");
	}
	
	@Override
	public int insertMember(MemberVO mvo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(mvo);
		
		return simpleInsert.execute(params);
	}

}
