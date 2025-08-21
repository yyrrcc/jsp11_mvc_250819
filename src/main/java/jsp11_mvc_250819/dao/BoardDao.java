package jsp11_mvc_250819.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jsp11_mvc_250819.dto.BoardDto;
import jsp11_mvc_250819.dto.MemberDto;

public class BoardDao {
	private String driverName = "com.mysql.cj.jdbc.Driver"; 
	private String url = "jdbc:mysql://localhost:3306/jspdb";
	private String username = "root";
	// 이클립스 run configurations 에서 비밀번호 설정함
	private String password = System.getenv("DB_PASSWORD");
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 만약 게시글이 308개가 있고 15개씩 보인다고 하면 308/10 총 30 + 1 페이지가 보임
	// 그리고 페이지 블록을 10개로 지정하면 1~10, 11~20, 21 이렇게 3개 블록이 보임
	public static final int PAGESIZE = 10; // 한 페이지당 보이는 글 수
	public static final int BLOCKSIZE = 5; // 페이지 블록 크기 (하단에 보여지는 [1][2] ..)
	
	
	
	// 게시판의 모든 글 목록 가져와서 반환하는 메서드 + 페이징
	public List<BoardDto> boardList(int page) {
		// 기존:하나의 테이블 sql문
		//String sql = "SELECT * FROM boardmvc ORDER BY bnum DESC";
		
		// 새로운:membermvc 테이블과 boardmvc 테이블 조인한 sql문
		String sql = "SELECT row_number() OVER (ORDER BY bnum ASC) AS bno, "
				+ "b.bnum, b.btitle, b.bcontent, b.memberid, m.memberemail, b.bhit, b.bdate "
				+ "FROM boardmvc b LEFT JOIN membermvc m "
				+ "ON b.memberid = m.memberid ORDER BY bno DESC"
				+ " LIMIT ? OFFSET ?";
		List<BoardDto> boardDtos = new ArrayList<>();
		
		int offset = (page - 1) * PAGESIZE; // offset 구하는 

		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, PAGESIZE);
			pstmt.setInt(2, offset);
			rs = pstmt.executeQuery(); // 모든 데이터값을 전달 받음
			
			while (rs.next()) {
				// rs에서 하나씩 값을 가져온 후
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");
				// email 추가
				String memberemail = rs.getString("memberemail");
				// 실제 글 개수 가져오는 데이터
				int bno = rs.getInt("bno");
				
				// 기존:BoardDto 생성자 이용해서 그 값을 넣어줌
				//BoardDto boardDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate);
				// 기존:만들어진 boardDto 인스턴스를 리스트에 넣어준다
				//boardDtos.add(boardDto);
				
				// BoardDto 인스턴스 boardDto에다가 memberDto 넣어주고, 그 전에 memberDto 인스턴스로 만들어줘야 함
				// 그리고 memberDto 인스턴스에다가 필요한 id, email 값 넣어주기 
				MemberDto memberDto = new MemberDto();
				memberDto.setMemberid(memberid);
				memberDto.setMemberemail(memberemail);
				BoardDto boardDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate, memberDto, bno);
				boardDtos.add(boardDto);
			} 

			} catch (Exception e) {
				System.out.println("DB 에러 발생, 로그인 실패");
				e.printStackTrace();
			} finally { 
				try {
					if (rs != null) {
						rs.close();
				}
					if (pstmt != null) {
						pstmt.close();
					}
					if (conn != null) {
						conn.close();
				}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return boardDtos;
	}
	
	
	
	
	// 게시판의 모든 글 개수 반환하는 메서드
	public int countBoard() {
		String sql = "SELECT count(*) AS totalCount FROM boardmvc";
		int totalCount = 0;
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				totalCount = rs.getInt("totalCount");
			}

			} catch (Exception e) {
				System.out.println("DB 에러 발생, 모든 글 개수 가져오기 실패");
				e.printStackTrace();
			} finally { 
				try {
					if (rs != null) {
						rs.close();
				}
					if (pstmt != null) {
						pstmt.close();
					}
					if (conn != null) {
						conn.close();
				}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return totalCount;
	}

	
	
	// 게시글 검색 결과 보내주는 메서드
	public List<BoardDto> searchBoardList(String searchKeyword, String searchType) {
		String sql = "SELECT row_number() OVER (ORDER BY bnum ASC) AS bno, "
				+ "b.bnum, b.btitle, b.bcontent, b.memberid, m.memberemail, b.bhit, b.bdate "
				+ "FROM boardmvc b "
				+ "LEFT JOIN membermvc m ON b.memberid = m.memberid"
				+ " WHERE "+ searchType +" LIKE ?"
				+ " ORDER BY bno DESC;";
		
		List<BoardDto> boardDtos = new ArrayList<>();

		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql);
			// sql where문에서 LIKE로 받은 경우엔 다음과 같이 만들어줘야 한다
			pstmt.setString(1, "%" + searchKeyword + "%");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// rs에서 하나씩 값을 가져온 후
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");
				// email 추가
				String memberemail = rs.getString("memberemail");
				// 실제 글 개수 가져오는 데이터
				int bno = rs.getInt("bno");
				
				// 기존:BoardDto 생성자 이용해서 그 값을 넣어줌
				//BoardDto boardDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate);
				// 기존:만들어진 boardDto 인스턴스를 리스트에 넣어준다
				//boardDtos.add(boardDto);
				
				// BoardDto 인스턴스 boardDto에다가 memberDto 넣어주고, 그 전에 memberDto 인스턴스로 만들어줘야 함
				// 그리고 memberDto 인스턴스에다가 필요한 id, email 값 넣어주기 
				MemberDto memberDto = new MemberDto();
				memberDto.setMemberid(memberid);
				memberDto.setMemberemail(memberemail);
				BoardDto boardDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate, memberDto, bno);
				boardDtos.add(boardDto);
			} 

			} catch (Exception e) {
				System.out.println("DB 에러 발생, 로그인 실패");
				e.printStackTrace();
			} finally { 
				try {
					if (rs != null) {
						rs.close();
				}
					if (pstmt != null) {
						pstmt.close();
					}
					if (conn != null) {
						conn.close();
				}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return boardDtos; // 기존:리스트 객체로 된 boardDtos 반환
	}
	
	
	
	// 게시글 작성 메서드 (매개변수를 필드로 받았을 경우)
	public void boardWrite(String title, String content, String writer) {
		String sql = "INSERT INTO boardmvc (btitle, bcontent, memberid, bhit) VALUES (?, ?, ?, 0)";
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, writer);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생, 새 글 작성 실패");
			e.printStackTrace();
		} finally { 
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	// 클릭한 글 세부내용 가져오기 메서드
	public BoardDto boardDetail(String num) {
		//String sql = "SELECT * FROM boardmvc WHERE bnum = ?";
		String sql = "SELECT b.bnum, b.btitle, b.bcontent, b.memberid, m.membername, m.memberemail, b.bdate, b.bhit "
				+ "FROM boardmvc b "
				+ "LEFT JOIN membermvc m "
				+ "ON b.memberid = m.memberid "
				+ "WHERE bnum = ? ORDER BY bnum DESC";
		BoardDto boardDto = null;
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, num);
			rs = pstmt.executeQuery();
			
			// rs에 있는 값을 가져오기. boardDto 인스턴스에 넣어주기
			while(rs.next()) {
				/*
				boardDto.setBnum(rs.getInt("bnum"));
				boardDto.setBtitle(rs.getString("btitle"));
				boardDto.setBcontent(rs.getString("bcontent"));
				boardDto.setMemberid(rs.getString("memberid"));
				boardDto.setBhit(rs.getInt("bhit"));
				boardDto.setBdate(rs.getString("bdate"));
				*/
				
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");
				// 이메일, 이름 추가
				String memberemail = rs.getString("memberemail");
				String membername = rs.getString("membername");

				// 이메일, 이름 추가한 거 넣어주기
				MemberDto memberDto = new MemberDto();
				memberDto.setMemberid(memberid);
				memberDto.setMemberemail(memberemail);
				memberDto.setMembername(membername);
				boardDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate, memberDto);				
			}
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생, 글 불러오기 실패");
			e.printStackTrace();
		} finally { 
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		return boardDto;
		}
	
	
	// 게시글 수정 메서드
	public void boardUpdate(String num, String title, String content) {
		String sql = "UPDATE boardmvc SET btitle = ?, bcontent = ? WHERE bnum = ?";
	
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, num);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생, 글 수정 실패");
			e.printStackTrace();
		} finally { 
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	
	// 게시글 삭제 메서드
	public void boardDelete(String num) {
		String sql = "DELETE FROM boardmvc WHERE bnum = ?";
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, num);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생, 글 삭제 실패");
			e.printStackTrace();
		} finally { 
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	// 게시글 눌렀을 때 조회수 올라가게 해주는 메서드
	public void updateHit(String num) {
		String sql = "UPDATE boardmvc SET bhit = (bhit + 1) WHERE bnum = ?";
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, num);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("DB 에러 발생, 조회수 에러");
			e.printStackTrace();
		} finally { 
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	
	 
	
	
}
	
	