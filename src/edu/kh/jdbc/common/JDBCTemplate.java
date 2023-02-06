package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	/*
	 * DB 연결(Connection 생성), 자동 커밋off, 트랜잭션 제어, JDBC 객체 자원 반환(close)
	 * 이러한 JDBC에서 반복 사용되는 코드를 모아둔 클래스
	 * 
	 * 모든 필드, 메서드가 static이어야 한다 static은 어디서든지 꺼내쓸수 있는 공간이다
	 * -> 어디서든지 클래스명.필드명 / 클래스명.메서드명 호출 가능 (별도 객체 생성 X)
	 * 
	 * 
	 */

	private static Connection conn = null;
	
	/** DB 연결 정보를 담고있는 Connection 객체 생성 및 반환 메서드
	 * @return conn
	 */
	public static Connection getConnection() {
		try {
			
			// 현재 커넥션 객체가 없을 경우 -> 새 커넥션 객체 생성
			if(conn == null || conn.isClosed()) {
				
				Properties prop = new Properties();
				// Map<String, String>형태의 객체, XML 입출력 특화
				
				// driver.xml 파일 읽어오기
				//FileInputStream fos = new FileInputStream();
				//prop.loadFromXML(fos);
				// 밑에와 같지만 한번만 쓸것이기 떄문에 따로 새 객체를 할당하는것이 아니라
				// 밑에 처럼 바로 대입시킨다
		
				prop.loadFromXML(new FileInputStream("driver.xml") );
				// -> XML 파일에 작성된 내용이 Properties 객체에 모두 저장됨
				
				// XML에서 읽어온 값을 모두 String 변수에 저장
				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				String user = prop.getProperty("user");
				String password = prop.getProperty("password");

				//커넥션 생성
				Class.forName(driver); // Oracle jdbc Drovewr를 객체 메모리에 로드
				
				// DriverManager를 이용해서 Connection 객체 만들기
				conn = DriverManager.getConnection(url, user, password);
				
				// 개발자가 직접 트랜젝션을 제어 할 수 있도록
				// 자동 커밋 비활성화
				conn.setAutoCommit(false);
				
			}
			
			
		} catch(Exception e) {
			System.out.println("Connection 성성중 예외 발생");
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	
	
	
	
	
	
	
	/** Connection 자원 반환 메서드
	 * @param conn
	 */
	public static void close(Connection conn) {
		

		try {
			//전달받은 conn가
			//참조받은 Connection 객체가 있고
			//그 Connection 객체가 close 상태가 아니라면
			if(conn !=null && !conn.isClosed()) conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/** Statement(부모), PreparedStatement(자식) 객체 자원 반환 메서드
	 * (다형성, 동적바인딩)에 의해서 stmt지만 pstmt로도 사용이 가능하다
	 * 동적바인딩 : runtime중에 시행
	 * 정적바인딩 : comfile중에 시행
	 * @param stmt
	 */
	public static void close(Statement stmt) { // 오버로딩 -- 위 close와 같은 메서드지만 오버로딩 규칙에 의해 매개변수가 달라서 가능
						
		try {
			if(stmt != null && !stmt.isClosed()) stmt.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/** ResultSet 객체 자원 반환 메서드
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		
		try {
			if(rs != null && !rs.isClosed()) rs.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/** 트랜잭션 Commit 메서드
	 * @param conn
	 */
	public static void commit(Connection conn) {
		try {
			
			// 연결되어있는 Connection 객체일 경우에만 Commit 진행
			if(conn !=null && !conn.isClosed()) conn.commit(); // conn이 null이 아니고 closed가 아닐경우에만 commit
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/** 트랜잭션 rollback 메서드
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		try {
			
			// 연결되어있는 Connection 객체일 경우에만 rollback 진행
			if(conn !=null && !conn.isClosed()) conn.rollback(); // conn이 null이 아니고 closed가 아닐경우에만 rollback
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	
}
