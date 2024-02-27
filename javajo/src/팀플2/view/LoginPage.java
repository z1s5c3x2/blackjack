package 팀플2.view;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Scanner;

import 팀플2.controller.LoginViewController;
import 팀플2.controller.MemberController;
import 팀플2.controller.RoomController;
import 팀플2.controller.RoomJoinController;
import 팀플2.model.dao.MemberDao;
import 팀플2.model.dao.RoomDao;
import 팀플2.model.dto.MemberDto;
import 팀플2.model.dto.RoomDto;



public class LoginPage {
	
	private static LoginPage loginPage = new LoginPage();
	public static LoginPage getInstance() {return loginPage;}
	private LoginPage() {}
	
	private Scanner sc = new Scanner(System.in);
	
	public void LoginView() {


		LoginViewController.getInstance().work = true;
		


		int ch = sc.nextInt();
		sc.nextLine();
		LoginViewController.getInstance().work = false;
		
		if (ch == 1) {roomCreate();}//ch1
		else if (ch == 2) {RoomChoice();}//ch2
		else if (ch == 3) {logOut();}//ch3
	}//loginView
	
	
	
	
	//방생성 메소드
	public void roomCreate() {

		boolean result = RoomJoinController.getInstance().roomCreate(MemberController.getInstance().getLoginSession());
		
		
		if (result) {
			System.out.println("방생성 완료.");
			
		}else {
			System.out.println("방생성에 실패 했습니다.");
		}
		int _roomres = 0;
		while(true)
		{
			_roomres = GameView.getInstance().index();
			if(_roomres == -1) //나가기
			{
				break;
			}
			else if(_roomres == -2)  //에러종료
			{
				//게임 비정상 종료
				System.out.println("게임 입장 오류");
			}
			RoomController.getInstance().checkHostInitStay();
		}
		//roomJoin();
		
		

	}
	
	//방입장 메소드
	
	public void roomJoin() {

		boolean result = RoomJoinController.getInstance().roomJoin();
		
		if (result) {
			System.out.println("방에 입장 하였습니다.");
		}else {
			System.out.println("방입장에 실패 하였습니다.");
		}
	}// roomJoin
	
	
	// 방번호 선택 후 방 입장
	public void RoomChoice() {
		System.out.println("입장할 방 번호를 입력 하세요"); int rno = sc.nextInt();
		sc.nextLine();
		int mno = MemberController.getInstance().getLoginSession();
		boolean result = RoomJoinController.getInstance().roomChoice(rno, mno);
		
		if (result) {
			System.out.println("방 입장 성공");
			// 게임 뷰 화면 출력 메소드 필요함
			int _roomres = 0;
			while(true)
			{
				_roomres = GameView.getInstance().index();
				if(_roomres == -1) //나가기
				{
					break;
				}
				else if(_roomres == -2)  //에러종료
				{
					//게임 비정상 종료
					System.out.println("게임 입장 오류");
				}
				RoomController.getInstance().checkHostInitStay();
			}
			
		}else {
			System.out.println("방입장 실패");
			
		}
	}
	
	
	
	//로그아웃 메소드
	public void logOut() {
		MemberController.getInstance().logOut();
		MainView.getInstance().mainView();
	}
	
	

}//class
