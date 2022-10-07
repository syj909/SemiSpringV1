package syj.spring.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import syj.spring.mvc.dao.MemberDAO;
import syj.spring.mvc.vo.MemberVO;

@Service("msrv")
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO mdao;
	
	@Override
	public boolean newMember(MemberVO mvo) {
		boolean isInsert = false;
		
		// 회원가입이 성공했으면 true를 리턴.
		if(mdao.insertMember(mvo) > 0) isInsert = true;
		
		return isInsert;
	}

	@Override
	public MemberVO readOneMember(String uid) {
		// TODO Auto-generated method stub
		return mdao.selectOneMember(uid);
	}

	@Override
	public boolean checkLogin(MemberVO mvo) {
		boolean isLogin = false;
		
		if(mdao.selectOneMember(mvo) > 0) {
			isLogin = true;
		}
		
		return isLogin;
	}

	@Override
	public String checkuid(String uid) {
		
		return mdao.selectCountUserid(uid) + "";
	}

	@Override
	public String findZipcode(String dong) throws JsonProcessingException {
		// 조회결과 출력방법 1 : csv (쉼표로 구분)
        // sido, gugun, dong, bunji
        // 서울, 강남구, 논현동, 123번지

        // 조회결과 출력방법 2 : xml
        // <zip><sido>서울</sido> <gugun>강남구</gugun>
        //      <dong>논현동</dong> <bunji>123번지</bunji></zip>

        // 조회결과 출력방법 3 : json (추천)
        // {'sido':'서울', 'gugun':'강남구',
        //  'dong':'논현동', 'bunji':'123번지'},
        // {'sido':'서울', 'gugun':'강남구',
        //  'dong':'논현동', 'bunji':'123번지'},
        // {'sido':'서울', 'gugun':'강남구',
        //  'dong':'논현동', 'bunji':'123번지'},

        // StringBuilder sb = new StringBuilder();
        // sb.append("{'sido':").append("'서울',")
        // .append("'gugun':").append("'강남구',")
        // .append("'dong':").append("'논현동',")
        // .append("'bunji':").append("'123번지',");
        // .append("}");
		
		// 조회결과를 json형태로 만들려면 상당히 복잡함.
		// ObjectMapper 라는 라이브러리를 이용하면
		// 손쉽게 JSON 형식의 데이터를 생성할 수 있음.
		
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		
		json = mapper.writeValueAsString(mdao.selectZipcode(dong + "%"));
		return json;
	}
	
}
