package edu.kh.jdbc.board.model.dao.BoardDAO;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.vo.board.Board;
import static edu.kh.jdbc.common.JDBCTemplate.*;

public class BoardDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null;
	
	public BoardDAO() {
	
	try {
		prop = new Properties();
				
	 prop.loadFromXML(new FileInputStream("board-query.xml"));;		
		
	} catch(Exception e) {
		e.printStackTrace();
	}
}
	

	/** 게시글 목록 조회 DAO
	 * @param conn
	 * @return boardLirt
	 */
	public List<Board> selectAllBoard(Connection conn) throws  Exception{
		
		List<Board> boardList = new ArrayList<>();
		
		String sql = prop.getProperty("selectAllBoard");
		
		//stmt 객체 생성
		stmt = conn.createStatement();
		
		//sql 수행 후 resultset 반환 받기
		
		rs = stmt.executeQuery(sql);
		
		while(rs.next()) {
			int boardNo = rs.getInt("BOARD_NO");
			String boardTitle = rs.getString("BOARD_TITLE");
			String memberName = rs.getString("MEMBER_NM");
			int commentReadCount = rs.getInt("READ_COUNT");
			String createDate = rs.getString("CREATE_DT");
			int commentCount = rs.getInt("COMMENT_COUNT");
			
			
			Board board = new Board();
			board.setBoardNo(boardNo);
			board.setBoardTitle(boardTitle);
			board.setMemberName(memberName);
			board.setReadCount(commentReadCount);
			board.setCreateDate(createDate);
			board.setCommentCount(commentCount);
			
			boardList.add(board);
			
				
		}
		
		
		
		try {
			
		} finally {
			close(rs);
			close(stmt);
		}
		
		return boardList;
	}

}
