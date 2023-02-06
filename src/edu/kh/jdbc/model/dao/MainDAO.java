package edu.kh.jdbc.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.member.vo.Member;

public class MainDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	
	private Properties prop = null;
	//Map<String, String> 제한, XML 파일 일고 쓰는데 특화
	
	//기본생성자
	public MainDAO() {
		
		 try {
			 prop = new Properties();  // properties 객체 생성
			 
			 prop.loadFromXML(new FileInputStream("main-query.xml"));;
			 // "main-query.xml" 파일의 내용을 읽어와서 properties에 저장
			 
		 } catch(Exception e) {
			 e.printStackTrace();
		 }
		
		
	}
	
	
	
	
	/** 로그인 DAO
	 * @param conn
	 * @param memberId
	 * @param memberPw
	 * @return loginMember
	 * @throws Exception
	 */
	public Member login(Connection conn, String memberId, String memberPw) throws Exception{
		
		// 1. 결과 저장용 변수 선언
		Member loginMember = null;
		
		try {
			// 2. SQL 얻어오기 (main -queary에 저장된 파일
			String sql = prop.getProperty("login");
			
			// 3. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ?에 알맞은 값 대입
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			// 5.SQL 수행결과 반환받기
			rs = pstmt.executeQuery();
			
			//6. 조회결과가 있을 경우
			//		컬럼값을 모두 얻어와
			//		member 객체를 생성해서 loginmember 대입
			
			if(rs.next()) {
				
				loginMember = new Member(rs.getInt("MEMBER_NO"),
							memberId,
							rs.getString("MEMBER_NM"),
							rs.getString("MEMBER_GENDER"),
							rs.getString("ENROLL_DATE")
						);	
				
				
			}
			
			
			
		} finally {
			// 사용한 객체 반환 , JDBCTemplate import 하기
			close(rs);
			close(pstmt);
			
		}
		
		
		
		return loginMember;
	}


	/** 아이디 중복 검사 DAO
	 * @param conn
	 * @param memberId
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(Connection conn, String memberId) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("idDupcheck");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberId);
			
			rs = pstmt.executeQuery();
			
			// COUNT(*) 숫자를 가져오지만 COUNT(*)를 가져오는 RS이다
			//		1
			
			if(rs.next()) {
				result = rs.getInt("COUNT(*)");
				//result = rs.getInt(1); // 컬럼순서 하나만 있을때는 가능
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return result;
	}



	/** 회원가입 DAO
	 * @param conn
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int singUp(Connection conn, Member member) throws Exception {
		int result = 0;
		
		try {
			String sql = prop.getProperty("signUp");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberGender());
			
			result = pstmt.executeUpdate();
			
			
			
		} finally {
			close(pstmt);
				
		}
		
		
		
		
		return result;
	}


}
