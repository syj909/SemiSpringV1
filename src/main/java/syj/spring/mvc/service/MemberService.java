package syj.spring.mvc.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import syj.spring.mvc.vo.MemberVO;

public interface MemberService {

	boolean newMember(MemberVO mvo);

	MemberVO readOneMember(String uid);

	boolean checkLogin(MemberVO mvo);

	String checkuid(String uid);

	String findZipcode(String dong) throws JsonProcessingException;

}
