package 팀플2.view;


import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Scanner;

import 팀플2.controller.GameController;
import 팀플2.controller.MemberController;
import 팀플2.controller.MemberInfoCheckController;
import 팀플2.controller.RoomController;
import 팀플2.model.dao.MemberInfoCheckDao;
import 팀플2.model.dao.RoomDao;
import 팀플2.model.dao.RoomDetailsDao;
import 팀플2.model.dao.RoomJoinDao;


public class GameView {
	private static GameView instance = new GameView();
	public static GameView getInstance() { return instance; }
	private GameView() {}
	private Scanner sc = new Scanner(System.in);
	
	public int index()
	{
		
		RoomController.getInstance().setRoomSession(RoomJoinDao.getInstance().roomRno(MemberController.getInstance().getLoginSession()));
		
		MemberInfoCheckController.getInstance().initSettings(RoomController.getInstance().getRoomSession());
		
		if(MemberInfoCheckController.getInstance().getState() == State.NEW)
		{
			MemberInfoCheckController.getInstance().start(); //db에 주기적으로 접근하는 스레드 시작
		}
		MemberInfoCheckController.getInstance().setIscheck(true);
		System.out.println("1.준비 2.나가기");
		if(sc.nextInt()==2)
		{
			System.out.println("퇴장");
			
			boolean res = GameController.getInstance().gameOut(MemberController.getInstance().getLoginSession());
			
			if(res)
			{
				System.out.println("나가기 성공");
			}else {System.out.println("나가기 에러");}
			
			MemberInfoCheckController.getInstance().setIscheck(false);
			return -1;
		}
		sc.nextLine();
		//db를 계속 체크하며 rdready의 sum이 maxpeople와 같다면 시작,시작키 프린트 해주기
		RoomDetailsDao.getIntance().memberReady(MemberController.getInstance().getLoginSession());

		while(true) // ingame
		{
			
			
			
			int ch = sc.nextInt();
			sc.nextLine();
			if(MemberInfoCheckController.getInstance().getIsEnd())
			{
				MemberInfoCheckController.getInstance().setIscheck(false);
				return ch;
				
			}
			else if(!RoomDetailsDao.getIntance().checkRoomTurn(MemberController.getInstance().getLoginSession()))
			{
				System.out.println("차례를 기다려 주세요");
			}
			else if(ch == 1)
			{
				//System.out.println("hit");
				hitGoView();
			}
			else if(ch==2)
			{
				//System.out.println("stay");
				stayGoView();	
			}
			else if(ch==3)
			{
				System.out.println("퇴장");
				
				
				boolean res = GameController.getInstance().gameOut(MemberController.getInstance().getLoginSession());
				
				if(res)
				{
					System.out.println("나가기 성공");
				}else {System.out.println("나가기 에러");}
				MemberInfoCheckController.getInstance().setIscheck(false);
				break;
			}
			else // 에외
			{
				System.out.println("잘못된 입력");
			
			}
			
		}
		//
		System.out.println("게임 종료");
		return -2;
	}
	public void hitAndStayView()
	{
		System.out.println("1.hit 2.stay 3.exit");
	}
	public void printExceptionGameEnd()
	{
		System.out.println("응답 안한 유저 있음 -1 입력하여 종료");
	}
	
	public void printGameResult(int res)
	{	int mno = MemberController.getInstance().getLoginSession();
		ArrayList<String> list = GameController.getInstance().resultPrint(mno);
		
		for(int i=0; i<list.size(); i++) {
			String[] str = list.get(i).split(",");
			if(Integer.parseInt(str[0])!=mno) {
				System.out.printf("%s님의 보유 카드 : %s 점수 합계 : %s\n",str[1],str[2],str[3]);
			}
		}
		
		if(res == -1)
		{
				System.out.printf("내 카드 : %s 점수 합계 : %s\n",GameController.getInstance().cardPrint(mno),GameController.getInstance().cardScore(mno));
				System.out.println("무승부입니다.");
			
		}
		else if(res == MemberController.getInstance().getLoginSession())
		{
				
				System.out.printf("내 카드 : %s 점수 합계 : %s\n",GameController.getInstance().cardPrint(mno),GameController.getInstance().cardScore(mno));
				System.out.println("승리했습니다!");
		}
		else if(res != 0)
		{
				
				System.out.printf("내 카드 : %s 점수 합계 : %s\n",GameController.getInstance().cardPrint(mno),GameController.getInstance().cardScore(mno));
				System.out.println("패배했습니다.");
		}
		System.out.println("1. 다시하기 -1. 게임 종료");
	}
	public void MemberPrint(ArrayList<String> _tmp )
	{
		System.out.println("게임 시작!");
		for(int i=0;i<_tmp.size();i++)
		{
			System.out.printf("%d번 유저.%s\n",i+1,_tmp.get(i));
		} 
		

		
	}
	
	// hitGoView : hit 하는 View
	public void hitGoView() {
		String card =  GameController.getInstance().hitGoLosic(RoomController.getInstance().getRoomSession());
		System.out.println(card.replace(",", "") + "카드를 뽑았습니다!");
		System.out.println("카드 목록 > "+ GameController.getInstance().cardPrint(MemberController.getInstance().getLoginSession()));
		System.out.println("현재 내 점수 "+ GameController.getInstance().cardScore(MemberController.getInstance().getLoginSession()));
	
		//여기 쉼표 수정 하실거면 하고 안하도 됨
	}
	
	// stayGoView : stay 하는 View
	public void stayGoView() {
		GameController.getInstance().stayGoLosic(RoomController.getInstance().getRoomSession());
		turnOverView();
	}
	
	// turnOverView : turn 넘기는 View
	public void turnOverView() { // -1 : sql 문제 0 : 승리 계산 (turn 할 멤버가 없음) 1 : turn 완료 

		
		int result = GameController.getInstance().turnOverLosic(RoomController.getInstance().getRoomSession());
		if(result == -1) System.out.println("turn 오류가 발생했습니다");
		else if(result == 0) {
			//winCheckView();
		}
		else if(result == 1) {
			System.out.println("턴 넘기기 성공");
		}
	}
	
	// winCheckView : win 체크하는 View
	public void winCheckView() {
		int result = GameController.getInstance().winCheck(RoomController.getInstance().getRoomSession());
		
		if(result == -1) System.out.println("무승부");
		else if(result == 0) System.out.println("?");
		else System.out.println(result + "승리");
		
		
	}
}
