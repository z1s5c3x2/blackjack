
package 팀플2.view;

import java.util.Scanner;

import 팀플2.controller.MemberController;


public class MainPage {
	
	// 싱글톤 생성
	// private 객체 생성
	private static MainPage mainPage = new MainPage();
	// 외부 접근 메소드 생성
	public static MainPage getInstance() {return mainPage;}
	// 외부 객체 생성 제한
	private MainPage() {}
	
	private Scanner sc = new Scanner(System.in);

	
	public void mainView() {
		

		while (true) {
			
			System.out.println("BLACK JACK에 오신걸 환영 합니다.");
			System.out.println("1. 회원 가입  /  2. 로그인");
			
			try {				
				// 1 회원가입 or 2 로그인 입력 
				int ch = sc.nextInt();
				
				if (ch == 1) {signupView();}
				if (ch == 2) {loginView();}				
			} catch (Exception e) {
				System.out.println("while 에서 에러 발생 " + e);

			} //catch
			
			
		}//while
	}// mainView
	
	// 회원가입 메소드
	public void signupView() {
			
		System.out.println("아이디 : "); String id = sc.next();
		System.out.println("비밀번호 : "); String pw = sc.next();
		System.out.println("비밀번호 확인 : "); String pwCk = sc.next();
		System.out.println("이름 : "); String name = sc.next();

		if (pw.equals(pwCk)) {
			System.out.println("비밀번호가 일치 합니다.");
		} else {
			System.out.println("비밀번호가 일치하지 않습니다. 다시 시도 해주세요");
			mainView();
		}

		int result = MemberController.getInstance().signupViewLogic(id, pw);

		if (result == 1) {
			System.out.println("회원 가입에 성공 하였습니다.");
		} else if (result == 2) {
			System.out.println("회원 가입에 실패 하였습니다. 관리자에게 문의 해주세요.");
		} else if (result == 3) {
			System.out.println("중복된 아이디가 있습니다");
		}
		
	} // signupView
	
	
	
	// 로그인 메소드
	public void loginView() {
		
		System.out.println("아이디 : "); String id = sc.next();
		System.out.println("비밀번호 : "); String pw = sc.next();
		
		boolean result = MemberController.getInstance().loginViewLogic(id, pw);

		if (result) {
			System.out.println("로그인에 성공 하였습니다.");
			LoginPage.getInstance().LoginView();
			
		} else {
			System.out.println("경고!! 정보 불일치!!  로그인에 실패 하였습니다.");
		}
		
		
		
		
	} //loginView
	
	
	

}//class
