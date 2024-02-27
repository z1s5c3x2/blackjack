package 팀플2.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import 팀플2.model.dao.RoomDao;
import 팀플2.model.dao.RoomJoinDao;

public class RoomController {
	
	private static RoomController roomController = new RoomController();
	public static RoomController getInstance() { return roomController;}
	private RoomController() {}
	private int roomSession;
	
	public int getRoomSession() {
		return roomSession;
	}
	public void setRoomSession(int roomSession) {
		this.roomSession = roomSession;
	}
	// 카드 섞기 
	public boolean cardSuffleLosic(int rno) {
		
		Integer nums[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37
				,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52};
        List<Integer> numslist = new ArrayList<>(Arrays.asList(nums));
        Collections.shuffle(numslist);
		
		return RoomDao.getInstance().cardSuffleSQL(rno, numslist);
	}

	public void checkHostInitStay()
	{
		RoomDao.getInstance().roomInitStay(roomSession);
		if(RoomDao.getInstance().getHost(RoomController.getInstance().getRoomSession(), MemberController.getInstance().getLoginSession()))
		{

			RoomController.getInstance().cardSuffleLosic(roomSession);
			
		}
	}

   // 방나가기
   public boolean roomOut() {
      
      int mno = MemberController.getInstance().getLoginSession();
      
      int rno = RoomJoinDao.getInstance().roomRno(mno);
      
      // 방 나가기 (nowpeople 줄이기)
      return RoomJoinDao.getInstance().roomOut(rno);
      
      
      
      // 방 목록 다시 출력 // 일단 호출만 가져옴
      //    RoomJoinDao.getInstance().RoomInfoBc();
      
   }

}
