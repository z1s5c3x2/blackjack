package 팀플2.controller;

import java.util.ArrayList;

import 팀플2.model.dao.RoomCreateDao;
import 팀플2.model.dao.RoomDao;
import 팀플2.model.dao.RoomJoinDao;
import 팀플2.model.dto.MemberDto;
import 팀플2.model.dto.RoomDto;
import 팀플2.view.MainView;

public class RoomJoinController {
	private static RoomJoinController roomJoinController = new RoomJoinController();
	public static RoomJoinController getInstance() {return roomJoinController;}
	private RoomJoinController () {}
	
	
	// 방목록 메소드
	public ArrayList<RoomDto> RoomInfoBc(){
		
		return RoomJoinDao.getInstance().RoomInfoBc();
	}
	
	
	// 방생성 메소드
	public boolean roomCreate(int mno) {

		int result = RoomCreateDao.getInstance().roomCreateSQL(mno);
		
		if (result >=1) {
			RoomJoinDao.getInstance().roomNowPeopleUpdate(RoomDao.getInstance().getRno(mno));
			return true;
		}else {
			return false;
		}
	}//roomCreate
	
	
	//방입장 메소드
	
	public boolean roomJoin() {
		
		int mno = MemberController.getInstance().getLoginSession();
		//System.out.println("mno 값 " + mno);
		int rno = RoomJoinDao.getInstance().roomRno(mno);
		//System.out.println("rno 값 " + rno);
		
		boolean result = RoomJoinDao.getInstance().roomJoin(rno, mno);

		return result;
		
	}//roomJoin
	
	// 방번호 선택 후 방 입장
	public boolean roomChoice(int rno , int mno) {
		
		boolean result =  RoomJoinDao.getInstance().roomChoice(rno, mno);
		return result;
	}
	
	
	
	
	//로그아웃 메소드
	public void logOut() {
		MemberController.getInstance().logOut();
		MainView.getInstance().mainView();
	}// logOut
	
	
}// class
