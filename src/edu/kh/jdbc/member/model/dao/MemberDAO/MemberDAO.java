package edu.kh.jdbc.member.model.dao.MemberDAO;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.member.vo.Member;
import static edu.kh.jdbc.common.JDBCTemplate.*;

public class MemberDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	private Properties prop = null;
	
	public MemberDAO() {
		
		 try {
			 prop = new Properties();  // properties 객체 생성
			 
			 prop.loadFromXML(new FileInputStream("member-query.xml"));;
			 // "main-query.xml" 파일의 내용을 읽어와서 properties에 저장
			 
		 } catch(Exception e) {
			 e.printStackTrace();
		 }
		
		
	}
	
	/**
	 * @param conn
	 * @param memberId
	 * @param memberPw1
	 * @param memberName
	 * @param memberGender
	 * @return selectMyinfo
	 */
	public Member selectMyinfo(Connection conn, String memberId, String memberPw1, String memberName, String memberGender) throws Exception {
		
		Member selectMyinfo = null;
		
		try {
			//sql문 얻어오기
			String sql = prop.getProperty("selectMyinfo");
			
			// 3. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
					
			// 4. ?에 알맞은 값 대입
			pstmt.setString(1, memberId);
			
			// 5.SQL 수행결과 반환받기
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				
				selectMyinfo = new Member(rs.getString("MEMBER_ID"),
											rs.getString("MEMBER_PW"),
											rs.getString("MEMBER_NM"),
											rs.getString("MEMBER_GENDER"));
				
			}
			
		} finally {
			close(rs);
			close(pstmt);
			
		}
		
		return selectMyinfo;
		
	}

}
