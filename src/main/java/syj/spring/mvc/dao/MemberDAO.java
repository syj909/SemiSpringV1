package syj.spring.mvc.dao;

import syj.spring.mvc.vo.MemberVO;

public interface MemberDAO {

	int insertMember(MemberVO mvo);

	MemberVO selectOneMember(String uid);

	int selectOneMember(MemberVO mvo);
}
