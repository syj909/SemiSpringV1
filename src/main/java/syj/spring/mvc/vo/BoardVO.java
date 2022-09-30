package syj.spring.mvc.vo;

public class BoardVO {
	private String bno;
	private String title;
	private String userid;
	private String regdate;
	private int views;
	private String contents;
	
	public BoardVO() {
	}

	public String getBno() {
		return bno;
	}

	public void setBno(String bno) {
		this.bno = bno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		String write = "BoardVO [bno=%s, title=%s, userid=%s, regdate=%s, views=%s, contents=%s]";
		
		String result = String.format(write, bno, title, userid, regdate, views, contents);
		
		
		return result;
	}
	
	
}
