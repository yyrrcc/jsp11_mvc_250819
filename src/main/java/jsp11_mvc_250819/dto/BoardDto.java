package jsp11_mvc_250819.dto;

public class BoardDto {
	private int bnum;
	private String btitle;
	private String bcontent;
	private String memberid;
	private int bhit;
	private String bdate;
	
	// MemberDto 클래스로 선언한 객체를 멤버변수로 영입
	private MemberDto memberDto;
	// 실제 글 개수 가져오는 변수
	private int bno;
	
	
	public BoardDto() {
		super();
	}
	
	// 기존 변수만 들어 있는 걸로 생성자 생성
	public BoardDto(int bnum, String btitle, String bcontent, String memberid, int bhit, String bdate) {
		super();
		this.bnum = bnum;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.memberid = memberid;
		this.bhit = bhit;
		this.bdate = bdate;
	}
	
	// bno 추가 된 생성자
	public BoardDto(int bnum, String btitle, String bcontent, String memberid, int bhit, String bdate,
			MemberDto memberDto, int bno) {
		super();
		this.bnum = bnum;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.memberid = memberid;
		this.bhit = bhit;
		this.bdate = bdate;
		this.memberDto = memberDto;
		this.bno = bno;
	}

	// MemberDto만 추가된 생성자
	public BoardDto(int bnum, String btitle, String bcontent, String memberid, int bhit, String bdate,
			MemberDto memberDto) {
		super();
		this.bnum = bnum;
		this.btitle = btitle;
		this.bcontent = bcontent;
		this.memberid = memberid;
		this.bhit = bhit;
		this.bdate = bdate;
		this.memberDto = memberDto;
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

	public MemberDto getMemberDto() {
		return memberDto;
	}

	public void setMemberDto(MemberDto memberDto) {
		this.memberDto = memberDto;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}
	
	
	
	
}
