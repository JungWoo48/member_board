package edu.kh.jdbc.member.model.dao.MemberDAO;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.member.vo.Member;
import static edu.kh.jdbc.common.JDBCTemplate.*;

public class MemberDAO {
	
		
		private Statement stmt = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;
		private Properties prop = null;
		
		// 기본 생성자
		public MemberDAO() {
			
			try {
				prop = new Properties();
				prop.loadFromXML(new FileInputStream("member-query.xml"));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	
		
		/**
		 * @param conn
		 * @return
		 */
		public List<Member> selectAll(Connection conn) throws Exception {
		
			List<Member> memberList = new ArrayList<>();
			
			try {
				//sql 얻어오기
				String sql = prop.getProperty("selectAll");
				
				//Statement 객체 생성
				stmt = conn.createStatement();
				
				//resultset 반환받기
				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					
					//컬럼값을 얻어와 Member객체 저장후 List 추가
					
					String memberId = rs.getString("MEMBER_ID");
					String memberName = rs.getString("MEMBER_NM");
					String memberGender = rs.getString("MEMBER_GENDER");
					
					Member member = new Member();
					member.setMemberId(memberId);
					member.setMemberName(memberName);
					member.setMemberGender(memberGender);
					
					memberList.add(member);
					
					
				}
				
			} finally {
				close(rs);
				close(stmt);
				
			}
			
			
			
		return memberList;
	}


		/** 회원정보 수정 DAO
		 * @param conn
		 * @param member
		 * @return result
		 */
		public int updateMember(Connection conn, Member member) throws Exception{
			//결과 저장용 변수 생성
			int result = 0;
			
			try {
				//sql 얻어오기
				String sql = prop.getProperty("updateMember");
				
				pstmt = conn.prepareStatement(sql);
				
				//? 에 값 대입
				pstmt.setString(1, member.getMemberName());
				pstmt.setString(2, member.getMemberGender());
				pstmt.setInt(3, member.getMemberNo());
				
				//sql 수행 후 결과 반환
				result = pstmt.executeUpdate();
			
			} finally {
				close(pstmt);
				
			}
			return result;
		}


		/** 비밀번호 변경 DAO
		 * @param conn
		 * @param currentPw
		 * @param newPw1
		 * @return
		 * @throws Exception
		 */
		public int updatePw(Connection conn, String currentPw, String newPw1, int memberNo) throws Exception {
			//결과 저장용 변수
			int result=0;
			
			try {
				String sql = prop.getProperty("updatePw");
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1,newPw1);
				pstmt.setInt(2, memberNo);
				pstmt.setString(3, currentPw);
				
		
			} finally {
				close(pstmt);
				
			}
			
			return result;
		}


		public int secession(Connection conn, String memberPw, int memberNo) throws Exception {
			
			int result = 0;
			
			try {
				String sql = prop.getProperty("secession");
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, memberNo);
				pstmt.setString(2, memberPw);
				
				result = pstmt.executeUpdate();
				
				
			} finally {
				close(pstmt);
				
			}
			
			return result;
		}
 		
	
	

}
