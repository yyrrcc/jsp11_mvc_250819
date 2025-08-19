package jsp11_mvc_250819.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jsp11_mvc_250819.dto.BoardDto;

public class BoardDao {
	private String driverName = "com.mysql.cj.jdbc.Driver"; 
	private String url = "jdbc:mysql://localhost:3306/jspdb";
	private String username = "root";
	private String password = "12345";
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	
	// 게시판의 모든 글 목록 가져와서 반환하는 메서드
	public List<BoardDto> boardList() {
		String sql = "SELECT * FROM boardmvc ORDER BY bnum DESC";
		List<BoardDto> boardDtos = new ArrayList<>();

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
				int bhit = rs.getInt("bhit");
				String bdate = rs.getString("bdate");
				
				// BoardDto 생성자 이용해서 그 값을 넣어줌
				BoardDto boardDto = new BoardDto(bnum, btitle, bcontent, memberid, bhit, bdate);
				
				// 만들어진 boardDto 인스턴스를 리스트에 넣어준다
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
		return boardDtos; // 리스트 객체로 된 boardDtos 반환
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
		BoardDto boardDto = new BoardDto();
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, num);
			rs = pstmt.executeQuery();
			
			// rs에 있는 값을 가져오기. boardDto 인스턴스에 넣어주기
			while(rs.next()) {
				boardDto.setBnum(rs.getInt("bnum"));
				boardDto.setBtitle(rs.getString("btitle"));
				boardDto.setBcontent(rs.getString("bcontent"));
				boardDto.setMemberid(rs.getString("memberid"));
				boardDto.setBhit(rs.getInt("bhit"));
				boardDto.setBdate(rs.getString("bdate"));
				
				/*
				rs.getInt("bnum");
				rs.getString("btitle");
				rs.getString("bcontent");
				rs.getString("memberid");
				rs.getString("bhit");
				rs.getString("bdate");
				*/
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
	
	
	
	
	
	
	
}
