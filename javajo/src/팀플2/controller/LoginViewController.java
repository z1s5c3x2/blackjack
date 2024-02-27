package 팀플2.controller;

import java.io.IOException;
import java.util.ArrayList;

import 팀플2.model.dto.RoomDto;

public class LoginViewController extends Thread{

	private static LoginViewController instance = new LoginViewController();
	public static LoginViewController getInstance() { return instance; }
	private LoginViewController() { start(); }
	public boolean work = false; 
	
	@Override
	public void run() {
		while(true) {
			
			if(work) {				
				clearScreen();
				System.out.println(" ----- 방 목록 ----- ");
				ArrayList<RoomDto> result = RoomJoinController.getInstance().RoomInfoBc();
				
				System.out.printf("%-3s %-4s %-19s \n" , "방번호" , "현재인원" , "최대인원" );
				for( int i = 0 ; i<result.size(); i++ ) {
					RoomDto dto = result.get(i);	// i번째의 객체를 호출 
					System.out.printf("%-5s %-7s %-19s \n" , 
							dto.getRno() , dto.getNowpeople() , dto.getMaxpeople() );
				}
				
				System.out.println("1.방생성 2.방입장 3.로그아웃 선택 : ");
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				Thread.yield(); // 현재스레드의 양보 
			}
			
			
			
		}
	}
	public void clearScreen() {
		for (int i = 0; i < 50; ++i) System.out.println();
	}
}
