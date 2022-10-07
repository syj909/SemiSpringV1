package syj.spring.mvc.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import syj.spring.mvc.vo.MemberVO;
import syj.spring.mvc.vo.Zipcode;

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
	private SqlSession sqlSession;
	
	@Override
	public int insertMember(MemberVO mvo) {
		
		return sqlSession.insert("member.insertMember", mvo);
	}

	@Override
	public MemberVO selectOneMember(String uid) {
		return sqlSession.selectOne("member.selectOneMember", uid);
	}

	@Override
	public int selectOneMember(MemberVO mvo) {
		return sqlSession.selectOne("member.selectCountMember", mvo);
	}

	@Override
	public int selectCountUserid(String uid) {
		return sqlSession.selectOne("member.selectCountUserid", uid);
	}

	@Override
	public List<Zipcode> selectZipcode(String dong) {
		List<Zipcode> zipcodeList = sqlSession.selectList("member.selectZipcode", dong);
		return zipcodeList;
	}
}
