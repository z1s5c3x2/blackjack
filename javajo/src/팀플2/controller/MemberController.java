package 팀플2.controller;

import 팀플2.model.dao.MemberDao;
import 팀플2.model.dto.MemberDto;

public class MemberController {
	
	//싱글톤
	private static MemberController memberController = new MemberController();
	public static MemberController getInstance() {return memberController;}
	private MemberController() {}
	
	// 회원가입 로직
	public int signupViewLogic(String id , String pw) {
		//System.out.println("회원가입 로직 실행 완료");
		
		// 아이디 중복 체크
		if (MemberDao.getInstance().infoCheckSQL("mid" , id)) {return 3;}
			
		MemberDto dto = new MemberDto(0 , id , pw); // 회원번호 , 회원 아이디 , 비밀번호 대입 
		boolean result = MemberDao.getInstance().signupSQL(dto); 
		
		if (result) {return 1;}
		else {return 2;}
		
	}// signupViewLogic
	
	
	//로그인 처리
	private int loginSession = 0;
	public int getLoginSession () {return loginSession;}
	
	//로그아웃 처리
	public void logOut() {this.loginSession=0;}
	
	
	// 로그인 로직
		
	public boolean loginViewLogic(String id , String pw) {
		
		
		int result = MemberDao.getInstance().loginSQL(id , pw);
		
		if (result >=1) {
			this.loginSession = result;
			
			return true;
		}else {
			return false;
		}
	}// loginViewLogic
	
	
	

}//class
