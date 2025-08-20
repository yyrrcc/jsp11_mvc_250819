package jsp11_mvc_250819.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jsp11_mvc_250819.dto.BoardDto;
import jsp11_mvc_250819.dto.BoardMemberDto;
// BoardMemberDto 사용함
public class BoardDao2 {
	private String driverName = "com.mysql.cj.jdbc.Driver"; 
	private String url = "jdbc:mysql://localhost:3306/jspdb";
	private String username = "root";
	private String password = "12345";
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	
	// 게시판의 모든 글 목록 가져와서 반환하는 메서드
	public List<BoardMemberDto> boardList() {
		// 기존:sql문
		//String sql = "SELECT * FROM boardmvc ORDER BY bnum DESC";
		
		// membermvc 테이블과 boardmvc 테이블 조인한 sql문
		String sql = "SELECT b.bnum, b.btitle, b.bcontent, b.memberid, m.memberemail, b.bhit, b.bdate "
				+ "FROM boardmvc b "
				+ "INNER JOIN membermvc m "
				+ "ON b.memberid = m.memberid "
				+ "ORDER BY bnum DESC;";
		
		//기존:리스트
		//List<BoardDto> boardDtos = new ArrayList<>();
		List<BoardMemberDto> bmDtos = new ArrayList<>();

		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); // 모든 데이터값을 전달 받음
			
			while (rs.next()) {
				// rs에서 하나씩 값을 가져온 후
				int bnum = rs.getInt("bnum");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				String memberid = rs.getString("memberid");
				// email 추가
				String memberemail = rs.getString("memberemail");
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");
				
				// 기존:BoardDto 생성자 이용해서 그 값을 넣어줌
				//BoardDto boardDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate);
				// 기존:만들어진 boardDto 인스턴스를 리스트에 넣어준다
				//boardDtos.add(boardDto);
				
				BoardMemberDto bmDto = new BoardMemberDto(bnum, btitle, bcontent, memberid, memberemail, bhit, bdate);
				bmDtos.add(bmDto);
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
		return bmDtos; // 기존:리스트 객체로 된 boardDtos 반환
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
		String sql = "SELECT * FROM boardmvc WHERE bnum = ?";
		// BoardDto boardDto = new BoardDto();
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
				
				boardDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate);				
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
	
	
	
	
	
}
