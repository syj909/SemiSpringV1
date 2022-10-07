package syj.spring.mvc.dao;

import java.util.List;

import syj.spring.mvc.vo.MemberVO;
import syj.spring.mvc.vo.Zipcode;

public interface MemberDAO {

	int insertMember(MemberVO mvo);

	MemberVO selectOneMember(String uid);

	int selectOneMember(MemberVO mvo);

	int selectCountUserid(String uid);

	List<Zipcode> selectZipcode(String dong);
}
