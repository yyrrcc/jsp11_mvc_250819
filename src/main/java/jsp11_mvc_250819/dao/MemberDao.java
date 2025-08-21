package jsp11_mvc_250819.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDao {
	private String driverName = "com.mysql.cj.jdbc.Driver"; 
	private String url = "jdbc:mysql://localhost:3306/jspdb";
	private String username = "root";
	// 이클립스 run configurations 에서 비밀번호 설정함
	private String password = System.getenv("DB_PASSWORD");
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	
	// 로그인 확인하는 메서드
	public int loginCheck(String id, String pw) {
		String sql = "SELECT * FROM membermvc WHERE memberid = ? AND memberpw = ?";
		int sqlResult = 0;
		
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			
			// 값이 존재하면, 참이면, 로그인 성공
			if (rs.next()) {		
				sqlResult = 1;
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
		return sqlResult;	

	}
}
