package edu.kh.jdbc.board.model.service.BoardServie;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO.BoardDAO;
import edu.kh.jdbc.board.model.vo.board.Board;
import static edu.kh.jdbc.common.JDBCTemplate.*;


public class BoardService {
	
	private BoardDAO dao = new BoardDAO();

	public List<Board> selectAllBoard()  throws Exception{
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		
		List<Board> boardList = dao.selectAllBoard();
		
		close(conn);
		
		return boardList;
	}

	public int insertBoard(Board board) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		
		//게시글 번호 생성 DAO 호출
		//동시에 여러 사람이 게시글을 등록하면
		// 시퀀스가 한번에 증가하여 currval 구문 이용시 문제 발생
		// -> 게시글 등록 서비스를 호출한 순서대로
		// 미리 게시글 번호를 생성해서 얻어온 다음 이를 이용해 insert 진행
		int boardNo = dao.nextBoardNo(conn);
		
		
		close(conn);
		return 0;
	}
	

	

}
