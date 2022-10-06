package syj.spring.mvc.utils;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("grcp")
public class RecaptchaUtils {
	public boolean checkCaptcha(String grcp) throws IOException, ParseException {
		String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
		String params = "?secret=6Lc081oiAAAAAJ5R-8IdlpUW9hqYvDzpoKbVjMoq&response" + grcp;
		String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:105.0) Gecko/20100101 Firefox/105.0";
		
		Logger logger = LoggerFactory.getLogger(getClass());
		// httpclient 객체 생성
		CloseableHttpClient req = HttpClients.createDefault();
		
		// 요청할 URL을 get 메서드
		HttpGet get = new HttpGet(VERIFY_URL + params);
		
		// 요청 header 정의
		get.addHeader("User-Agent", USER_AGENT);
		get.addHeader("Content-type", "application/json");
		
		// 설정된 정보로 실제 URL 요청 실행
		CloseableHttpResponse res = req.execute(get);
		
		// 실행여부 확인 (응답코드 : 200-정상실행, 404,505 - 실행실패)
		//logger.info(res.getCode());

		// 응답결과 확인
		
		String result = EntityUtils.toString(res.getEntity());
		logger.info(result);
		
		// 결과문자열을 JSON객체로 변환
		// success 키의 값을 알아냄 : json객체명.getxxx(키)
		JSONObject json = new JSONObject(result);
		boolean success = json.getBoolean("success");
		//httpclient 객체 닫기
		req.close();
		
		return success;
	}
}
