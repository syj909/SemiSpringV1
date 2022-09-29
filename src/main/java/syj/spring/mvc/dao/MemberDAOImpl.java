package syj.spring.mvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import syj.spring.mvc.vo.MemberVO;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO{

	@Autowired
	private JdbcTemplate jdbcTemlpate;
	
	@Override
	public int insertMember(MemberVO mvo) {
		String sql = "insert into member" 
					+ "(userid, passwd, name, email) " 
					+ "values(?, ?, ?, ?)";
		Object[] params = new Object[] {
				mvo.getUserid(), mvo.getPasswd(), mvo.getEmail(), mvo.getEmail()
		};
		
		return jdbcTemlpate.update(sql, params);
	}

}
