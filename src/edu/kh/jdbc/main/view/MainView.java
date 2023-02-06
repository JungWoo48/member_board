package edu.kh.jdbc.main.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.jdbc.board.view.BoardView;
import edu.kh.jdbc.main.model.service.MainService;
import edu.kh.jdbc.member.view.MemberView;
import edu.kh.jdbc.member.vo.Member;

public class MainView {
	
	
	private Scanner sc = new Scanner(System.in);
	
	private MainService service = new MainService();
	
	
	// 로그인 된 회원 정보를 저장한 객체 참조 변수
	private  static Member LoginMember = null;
	// -> 로그인 X == null
	// -> 로그인 O != null
	
	//회원 기능 메뉴 객체 생성
	private MemberView memberView = new MemberView();
	
	//게시판 기능 메뉴 객체 생성
	private BoardView boardView = new BoardView();
	
	/**
	 * 메인 메뉴 출력 메서드
	 */
	public void mainmenu() {
		
		int input = -1;
		
		do {
			
			try {
				
				if( LoginMember == null ) {//로그인 X
				
				System.out.println("\n****회원제 게시판 프로그램 ****");
				System.out.println("1. 로그인");
				System.out.println("2. 회원가입");
				System.out.println("0. 프로그램 종료");
				
				System.out.println("\n메뉴 선택 : ");
				
				input = sc.nextInt();
				sc.nextLine();// 입력 버퍼 개행 문자 제거
				System.out.println();
				
				switch(input) {
				
				case 1: login();//로그인
				case 2: signUp();//회원가입
				case 0:	//프로그램 종료
					System.out.println("프로그램 종료");
					break;
				default : System.out.println("입력된 문자 오류");
				
				
				}
				
				} else { // 로그인 O
					System.out.println(" 회원 메뉴");
					System.out.println("1. 회원 기능");
					System.out.println("2. 게시판 기능");
					System.out.println("3. 로그아웃");
					System.out.println("99. 프로그램 종료");
					
					System.out.println("\n 메뉴선택");
					input = sc.nextInt();
					
					System.out.println();
					
					switch(input) {
					
					case 1:	memberView.memberMenu("loginMember"); break;//회원 기능 서브메뉴 출력
					case 2: boardView.boardMenu(); break;//게시판 기능 서브메튜 출력
					case 0: 
						LoginMember = null; // 로그아웃 == loginmember가 참조하는 객체가 없음(null)
						System.out.println("로그아웃 완료");
						input = -1; // do-while문이 종료되지 않도록 0이 아닌값으로 변경
						break; // 없을 경우 바로 프로그램 종료
					case 99 : System.out.println("프로그램 종료");	
					// System.exit(0); // JVM 종료, 매개변수는 0 아니면 오류를 의미
							break;
					default : System.out.println("메뉴에 작성된 번호만 입력해수세요.");	
					
					}
					System.out.println();
					
				}
				
			} catch(InputMismatchException e) {
				System.out.println("\n <<입력 형식이 올바르지 않습니다.>>");
				sc.nextLine(); // 입력버퍼에 남아있는 잫못된 문자열 제거
			} 
			
			
			
		} while (input != 0);
		
		
		/*
		 * 로그인 안했을때
		 * 
		 * 1.로그인
		 * 2.회원 가입
		 * 0.프로그램 종ㄽ
		 * 
		 * ------------------
		 * 
		 * 로그인 했을때
		 * 
		 * 1.회원기능
		 * 2.게시판기능
		 * 0.로그아웃
		 * 99프로그램 종료
		 * 
		 * 
		 */
		
	
		
	}
	
	/** 
	 * 로그인 화면
	 */
	private void login() {
		System.out.println("로그인");
		
		System.out.println("아이디 : ");
		String memberId = sc.next();
		
		System.out.println("비밀번호 : ");
		String memberPw = sc.next();
		
		try {
			
		// 로그인 서비스 호출 후 조회 결과를 Loginmember에 저장
		LoginMember = service.login(memberId, memberPw);
		
		
		System.out.println();
		if(LoginMember != null) { //로그인 성공시 
			System.out.println(LoginMember.getMemberName() + "님 환영합니다");
			
		} else { //로그인 실패시
			System.out.println("아이디 또는 비밀번호가 일치하지 않음");
		}
		
		System.out.println();
		
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  회원 가입 화면
	 */
	private void signUp() {
		System.out.println("회원가입");
		
		String memberId = null;
		
		String memberPw1 = null;
		String memberPw2 = null;
		
		String memberName = null;
		String memberGender = null;
		
				
		try {
		
			//아이디를 입력받아 중복이 아닐때까지 반복
			while(true) {
				
				System.out.println("아이디 입력 : ");
				memberId = sc.next();
				
				//입력 받은 아이디를 매개변수로 전달하여 중복 검사하는 서비스 호출
				//후 결과 반환 있으면 1 없으면 0
				
				
				
				int result = service.idDupCheck(memberId); //1 or 0 대입
				
				System.out.println();
				
				if(result == 0) { //사용가능 아이디
					System.out.println("사용가능 아이디");
					break;
					
				} else { //중복인경우
					System.out.println("이미 사용중인 아이디");
				}
				
				System.out.println();
				
			
			}	
			
			// 비밀번호 입력
			// 비밀번호 확인/이 일치할때 까지 반복
			
			while(true) {
				
				System.out.println("비밀번호 : ");
				memberPw1 = sc.next();
				
				System.out.println("비밀번호 확인 : ");
				memberPw2 = sc.next();
				
				System.out.println();
				
				if(memberPw1.equals(memberPw2)) { //일치할경우
					System.out.println("비밀번호 일치");
						break;
					
				} else { //일치안하는걍우
					System.out.println("불일치, 다시입력");
				}
				
				System.out.println();
				
				

			}
			
			// 이름 입력
			
			System.out.println("이름 입력 : ");
			memberName = sc.next();
			
			// 성별입력
			// M F 가 입력될떄까지 반복
			
			while(true) {
				
				System.out.println("성별(M/F) : ");
				memberGender = sc.next().toUpperCase();
				
				System.out.println();
				
				if(memberGender.equals("M") || memberGender.equals("F")) {
					break;
				}else {
					System.out.println("M 또는 F만 입력");
				}
				
				System.out.println();
				
				
			}
			
			// 아이디 비밀번호 이름 성별 입력완료
			// -> 하나의 VO에 담아서 서비스 호출 후 결과 반환 받기
			Member member = new Member(memberId, memberPw1, memberName, memberGender);
			
			int result = service.signUp(member);
			
			
			// 서비스 처리 결과에 따른 출력화면 제어
			
			System.out.println();
			if(result > 0) {
				System.out.println("회원가입 완료");
				
			} else {
				System.out.println("회원가입 실패");
			}
			System.out.println();
			
		
			
		} catch(Exception e) {
			System.out.println("\n회원가입중 예외발생\n");
			e.printStackTrace();
		}
		
		
	}
	
	
	
}
/* 회원기능 (Member View, Service, DAO, member-query.xml)
 * 
 * 1. 내 정보 조회
 * 2. 회원 목록 조회(아이디, 이름, 성별)
 * 3. 내 정보 수정(이름, 성별)
 * 4. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)
 * 5. 회원 탈퇴
 * 
 * ------------------------------------------------------------------
 * 
 * 게시판 기능 (Board View, Service, DAO, board-query.xml)
 * 
 * 1. 게시글 목록 조회(작성일 내림차순)
 * 	  (게시글 번호, 제목[댓글 수], 작성자명, 작성일, 조회수 )
 * 
 * 2. 게시글 상세 조회(게시글 번호 입력 받음)
 *    (게시글 번호, 제목, 내용, 작성자명, 작성일, 조회수, 
 *     댓글 목록(작성일 오름차순 )
 *     
 *     2-1. 댓글 작성
 *     2-2. 댓글 수정 (자신의 댓글만)
 *     2-3. 댓글 삭제 (자신의 댓글만)
 * 
 *     // 자신이 작성한 글 일때만 메뉴 노출
 *     2-4. 게시글 수정
 *     2-5. 게시글 삭제
 *     
 *     
 * 3. 게시글 작성(제목, 내용 INSERT) 
 * 	-> 작성 성공 시 상세 조회 수행
 * 
 * 4. 게시글 검색(제목, 내용, 제목+내용, 작성자)
 * 
 * */

// board-query.xml
// member-query.xml
// comment-query.xml 필요
/*
 * 
 * edu.kh.jdbc.board.view.boardView
 * edu.kh.jdbc.board.model.dao.BoardDAO
 * edu.kh.jdbc.board.model.daoCommentDAO
 * edu.kh.jdbc.board.model.service.BoardService
 * edu.kh.jdbc.board.model.CommentService
 * edu.kh.jdbc.board.model.vo.board
 * edu.kh.jdbc.board.model.vo.Comment
 * 
 * edu.kh.jdbc.member.model.dao,MemberDAO
 * edu.kh.jdbc.member.model.service.MemberService
 * edu.kh.jdbc.member.view.MemberView
 * edu.kh.jdbc.member.vo.Member
 * 
 * edu.kh.jdbc.main.model.dao.MainDAO
 * edu.kh.jdbc.main.model.service.MainService
 * edu.kh.jdbc.main.view.MainView
 * edu.kh.jdbc.main.run.MainRun
 * 
 * edu.kh.jdbc.common.CreateXMLFile
 * edu.kh.jdbc.common.JDBCTemplate
 * 
 * 
 */


