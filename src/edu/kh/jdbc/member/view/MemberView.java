package edu.kh.jdbc.member.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.jdbc.main.model.service.MemberServie.MemberService;
import edu.kh.jdbc.member.vo.Member;

public class MemberView {
	
	private Scanner sc = new Scanner(System.in);
	
	private MemberService Memberservice = new MemberService();
	
	//로그인 회원 정보 저장용 변수
	private Member loginMember = null; 

	public void memberMenu(Member loginMember) {//로그인 된 회원의 정보를 가지고 진행해야하므로 loginmember를 갖고 온다
		// TODO Auto-generated method stub
		
		// 전달 박은 로그인 회원 정보를 필드에 저장
		this.loginMember = loginMember;
		
		int input = -1;
		
		do {
			
			try {
				
				if( this.loginMember != null ) {
					
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
		
		try {
			
			loginMember = Memberservice.selectMyinfo(memberId, memberPw, memberName, memberGender);
			
			
			
			System.out.println();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}


}
