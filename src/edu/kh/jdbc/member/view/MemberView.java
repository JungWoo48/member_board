package edu.kh.jdbc.member.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.main.model.service.MemberServie.MemberService;
import edu.kh.jdbc.member.vo.Member;
import static edu.kh.jdbc.main.view.MainView.*;

public class MemberView {
	
	private Scanner sc = new Scanner(System.in);
	
	private MemberService service = new MemberService();
	
	private int input = -1;
	
	//로그인 회원 정보 저장용 변수
	private Member loginMember = null; 

	public void memberMenu(Member loginMember) {//로그인 된 회원의 정보를 가지고 진행해야하므로 loginmember를 갖고 온다
		// TODO Auto-generated method stub
		
		// 전달 박은 로그인 회원 정보를 필드에 저장
		this.loginMember = loginMember;
		
		
		do {
			
			try {
					
					System.out.println("회원 기능");
					System.out.println("1. 내 정보 조회");// slelctMyinfo()//
					System.out.println("2. 회원 목록 조회");// selectAll()//
					System.out.println("3. 내 정보 수정(이름, 성별");// updateMember()
					System.out.println("4. 비밀번호 변경");// updatePw()
					System.out.println("5. 회원 탈퇴");//secession()
					System.out.println("0. 프로그램 종료");
					
					
					System.out.println("\n메뉴 선택");
					
					input = sc.nextInt();
					sc.nextLine();// 입력 버퍼 개행 문자 제거
					System.out.println();
					
					switch(input) {
					
					case 1: selectMyinfo();
					//case 2: selectAll();
					//case 3: updatemember();
					//case 4: updatePw();
					//case 5: secession();
					case 0: 
						System.out.println("프로그램 종료");
						break;
					default : System.out.println("부정확한 숫자 입력");
						
					}
					
				
				
			}catch(InputMismatchException e) {
				e.printStackTrace();
			}
			
		} while(input != 0);
		
		
	}
	
	
	/** 내 회원 정보 조회 
	 * 
	 */
	private void selectMyinfo() {
		System.out.println("나의 회원 정보");
		
		System.out.println("회원 번호" + loginMember.getMemberNo());
		System.out.println("아이디" + loginMember.getMemberId());
		System.out.println("이름" + loginMember.getMemberName());
		System.out.println("성별");
		if(loginMember.getMemberGender().equals("M")) {
			System.out.println("남");
		} else {
			System.out.println("여");
		}
		System.out.println("가입일" + loginMember.getEnrollDate());
		
		
		

		
	}
	
	/** 전부 조회
	 *
	 */
	public void selectAll() {
		System.out.println("회원목록 조회");
		
		//DB에서 목록 조회

		try {
		
		List<Member> memberList = service.selectAll();
		
		//조회 결과가 있으면 모두 출력
		//없으면 없다 출력
		
		if(memberList.isEmpty()) {
			System.out.println("조회결과 없음");
		} else {
			System.out.println("아이디 이름 성별");
			
			//향상된 for문
			for(Member member : memberList) {
				System.out.printf("%s %s %s", member.getMemberId(),
						member.getMemberName(),
						member.getMemberGender());
			}
			
			
		}
		
		} catch(Exception e) {
			System.out.println("회원목록 조회중 문제 발생");
			e.printStackTrace();
		}
		
	}
	
	/** 정보 수정
	 * 
	 */
	public void updateMember() {
		System.out.println("회원 정보 수정");
		
		try {
			System.out.println("변경할 이름 :");
			String memberName = sc.next();
			
			String memberGender = null;
			
			while(true) {
				System.out.println("변경할 성별");
				
				if(memberGender.equals("M") || memberGender.equals("F")) {
					break;
				} else {
					System.out.println(" m f 만 입력");
				}
				
				
			}
			// 서비스로 전달할 emember 객체 생성
			Member member = new Member();
			member.setMemberNo(loginMember.getMemberNo());
			member.setMemberName(memberName);
			member.setMemberGender(memberGender);
			
			//회원 정보 수정 서비스 메서드 호출 후 결과 반환
			int result = service.updateMember(member);
			
			if(result > 0) {
				// loginMember에 저장된 값과
				// DB에 수정된 값으 동기화 하는 작업
				loginMember.setMemberName(memberName);
				loginMember.setMemberGender(memberGender);
				
				System.out.println("회원 정보 수정 완료");
			} else {
				System.out.println("수정 실패");
			}
			
			
			
		} catch(Exception e) {
			System.out.println("정보 수정 중 오류");
			e.printStackTrace();
		}
		
	}
	
	/** 비밀번호 수정
	 * 
	 */
	public void updatePw() {
		System.out.println("비밀번호 변경");
		
		try {
			System.out.println("현재 비밀번호");
			String currentPw = sc.next();
			
			String newPw1 = null;
			String newPw2 = null;
			
			while(true) {
				System.out.println("새 비밀번호 :");
				newPw1 = sc.next();
				
				System.out.println("새 비밀번호 확인:");
				newPw2 = sc.next();
				
				if(newPw1.equals(newPw2)) {
					break;
				} else {
					System.out.println("비밀번호가 같지 않음");
				}
			}
			
			// 서비스 호출후 값 반환
			int result = service.updatePw(currentPw, newPw1, loginMember.getMemberNo());
			
			if(result >0) {
				System.out.println("비밀번호 변경 완료");
			}else {
				System.out.println("비밀번호 오류");
			}
			
			
			
		} catch(Exception e) {
			System.out.println("비밀번호 변경중 오류");
			e.printStackTrace();
		}
		
	}
	
	/** 회원 탈퇴
	 * 
	 */
	public void secession() {
		System.out.println("회원 탈퇴");
		
		try {
			System.out.println("비밀번호 입력 :");
			String memberPw = sc.next();
			
			while(true) {
				System.out.println("정말 탈퇴하시겠습니까? (Y/N) :");
				char ch = sc.next().toUpperCase().charAt(0);
				
				if(ch== 'Y') {
					//서비스 호출후 결과 반환
					int result = service.secession(memberPw, loginMember.getMemberNo());// 비밀번호, 회원번호
							
					if(result > 0) {
						System.out.println("탈퇴 완료");
						
						input = 0; // 메인메뉴로 이동
						loginMember = null; // 로그아웃
						
						// 로그 아웃 진행
						
					} else {
						System.out.println("비밀번호가 일치하지 않음");
						
					}
					break;
					
					
				}else if(ch == 'N') {
					System.out.println("탈퇴 취소");
				} else {
					System.out.println("잘못된 입력");
				}
				break;
			}
			
			
		} catch(Exception e) {
			System.out.println("회원 탈퇴중 문제 발생");
			e.printStackTrace();
		}
	}

	


}
