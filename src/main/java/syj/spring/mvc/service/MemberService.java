package syj.spring.mvc.service;

import syj.spring.mvc.vo.MemberVO;

public interface MemberService {

	boolean newMember(MemberVO mvo);

	MemberVO readOneMember(String uid);

	boolean checkLogin(MemberVO mvo);

}
