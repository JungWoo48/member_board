package edu.kh.jdbc.board.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardServie.BoardService;
import edu.kh.jdbc.board.model.vo.board.Board;
import edu.kh.jdbc.main.view.MainView;


public class BoardView {
	
	private Scanner sc = new Scanner(System.in);
	
	private BoardService bservice = new BoardService();
	

	public void boardMenu() {
		// TODO Auto-generated method stub
		
		int input =-1;
		
		do {
			
		
			try {
				System.out.println("게시판 기능");
				System.out.println("1. 게시글 목록 조회");
				System.out.println("2. 게시글 상세 조회");
				System.out.println("3. 게시글 작성");
				System.out.println("4. 게시글 검색");
				System.out.println("0. 로그인 메뉴로 이동");
				
				System.out.println("메뉴 선택 :");
				input = sc.nextInt();
				sc.nextLine();
				
				System.out.println();
				//개행
				
				switch(input) {
				
				case 1: selectAllBoard(); break;
				case 2: selectBoard(); break;
				case 3: insertBoard(); break;
				
				case 4: serchBoard(); break;
				
				case 0: System.out.println("로그인 메뉴로 이동");
				default: System.out.println("메뉴에 작성된 번호만 입력");
				}
				
				System.out.println();
				
			} catch(InputMismatchException e) {
				System.out.println("입력 형식이 올바르지 않음");
				sc.nextLine();
			}
		
		}while(input !=0);

	}
	
	private void selectAllBoard() {
		System.out.println("게시글 목록 조회");
		
		try {
			
			List<Board> boardList = bservice.selectAllBoard();
			
			if(boardList.isEmpty()) { //조회 결과 X
				System.out.println("게시글이 존재하지 않습니다");
				
			} else {
				for(Board b : boardList) {
					
					
				}
				
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/**게시글 등록
	 * 
	 */
	private void insertBoard() {
		System.out.println("게시글 등록");
		
		System.out.println("제목 : ");
		String boardTitle = sc.nextLine();
		
		System.out.println("내용 : ");
		String boardContent = inputContent();
		
		//board 객체에 제목, 내용, 회원 번호를 담아서 서비스에 전달
		Board board = new Board();
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
		board.setMemberNo(MainView.LoginMember.getMemberNo());
		
		int result = bservice.insertBoard(board);
		
		
		
	
	}
	
	/** 내용 입력
	 * @return
	 */
	private String inputContent() {
		String content =""; //빈 문자열
		String input = null; // 참조하는 객체가 없음
		
		System.out.println("입력 종료시 ($exit) 입력");
		
		while(true) {
			input = sc.nextLine();
			
			if(input.equals("&exit")) {// &exit 입력시 탈출
				break;
			}
			
			//입력 값을 conment에 누적
			content += input + "\n";
		}
		
		return content;
	}
	
	

}

	
	


