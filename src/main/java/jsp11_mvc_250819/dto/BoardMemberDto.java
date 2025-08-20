package jsp11_mvc_250819.dto;

public class BoardMemberDto {
	private int bnum;
	private String btitle;
	private String bcontent;
	private String memberid;
	private String memberemail; // BoardDto 에다가 이메일 추가
	private int bhit;
	private String bdate;
	
	
	public BoardMemberDto() {
		super();
	}
	
	public BoardMemberDto(int bnum, String btitle, String bcontent, String memberid, String memberemail, int bhit,
			String bdate) {
		super();
		this.bnum = bnum;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.memberid = memberid;
		this.memberemail = memberemail;
		this.bhit = bhit;
		this.bdate = bdate;
	}
	public int getBnum() {
		return bnum;
	}
	public void setBnum(int bnum) {
		this.bnum = bnum;
	}
	public String getBtitle() {
		return btitle;
	}
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}
	public String getBcontent() {
		return bcontent;
	}
	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getMemberemail() {
		return memberemail;
	}
	public void setMemberemail(String memberemail) {
		this.memberemail = memberemail;
	}
	public int getBhit() {
		return bhit;
	}
	public void setBhit(int bhit) {
		this.bhit = bhit;
	}
	public String getBdate() {
		return bdate;
	}
	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	
	
	
}
