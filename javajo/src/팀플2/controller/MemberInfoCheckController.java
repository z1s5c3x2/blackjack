package 팀플2.controller;

import java.util.ArrayList;

import 팀플2.model.dao.MemberInfoCheckDao;
import 팀플2.model.dao.RoomDao;
import 팀플2.model.dao.RoomDetailsDao;
import 팀플2.view.GameView;

public class MemberInfoCheckController extends Thread{
	private static MemberInfoCheckController instance = new MemberInfoCheckController();
	public static MemberInfoCheckController getInstance() { return instance; }
	private MemberInfoCheckController() {}
	
	private ArrayList<Integer> roomUserList = new ArrayList<>(); 
	private int roomSession;
	
	private Boolean isEnd = false;
	private Boolean ischeck ;
	private Boolean isPrinter ;
	
	
    public Boolean getIsPrinter() {
		return isPrinter;
	}
	public void setIsPrinter(Boolean isPrinter) {
		this.isPrinter = isPrinter;
	}
	public Boolean getIscheck() {
		return ischeck;
	}
	public void setIscheck(Boolean ischeck) {
		this.ischeck = ischeck;
	}
	public Boolean getIsEnd() {
		return this.isEnd;
	}
	public void setIsEnd(Boolean isEnd) {
		this.isEnd = isEnd;
	}
	public ArrayList<Integer> getRoomUserList() {
		return this.roomUserList;
	}
	public void setRoomUserList(ArrayList<Integer> roomUserList) {
		this.roomUserList = roomUserList;
	}
	public int getRoomSession() {
		return this.roomSession;
	}
	public void setRoomSession(int roomSession) {
		this.roomSession = roomSession;
	}
	public void initSettings(int rno)
	{
		roomSession = rno;
		ischeck = true;
		isEnd = false;
		isPrinter = true;
		isMyTurn = false;
	}
	private boolean isMyTurn;
	private void initRoomState()
	{
		
	}
	public void run()
    {
		//중간에 나간 유저는 rdready 카운트와 maxpeople 비교하여 확인
		
    	try {
    		while(true)
    		{
    			//System.out.println(isEnd);
    			if(ischeck)
    			{
    				//System.out.println("t1run"+roomSession);
    			if(isPrinter)
    			{
    				ArrayList<String> tmp =  RoomDetailsDao.getIntance().checkRoomReadyCount(roomSession);
    				if(tmp != null)
    				{
    					isPrinter = false;
    					GameView.getInstance().MemberPrint(tmp);

    					boolean result = RoomDao.getInstance().getHost(roomSession, MemberController.getInstance().getLoginSession());
    					if(result) RoomController.getInstance().cardSuffleLosic(roomSession);
    					
    				}
    				
    			}
    			if(!isMyTurn && !isPrinter && RoomDao.getInstance().getRoomTurn(roomSession) == MemberController.getInstance().getLoginSession())
    			{
    				System.out.println("내턴입니다!");
    				
    				if(RoomDao.getInstance().cardlist == null)
    				{
    				
    					RoomDao.getInstance().getCardList(roomSession);
    				}
    				//
					GameView.getInstance().hitGoView();
    				GameView.getInstance().hitGoView();
    				GameView.getInstance().hitAndStayView();
    				isMyTurn = true;
    				
    			}
              //내 응답시간 업데이트
                MemberInfoCheckDao.getInstance().myResponseTimeUpdate(MemberController.getInstance().getLoginSession());
                //게임이 끝나기 전에 나간 유저 체크
                if(MemberInfoCheckDao.getInstance().gameExectiponCheck(roomSession))
                {
                	//게임중 상대방이 나갔을때 실행
                	GameView.getInstance().printExceptionGameEnd();
                	ischeck = false;
                	isEnd = true;
                	//initRoomState
                }
                
    			if(MemberInfoCheckDao.getInstance().stayCountCheck(roomSession))
    			{
    				
    				GameView.getInstance().printGameResult(GameController.getInstance().winCheck(MemberController.getInstance().getLoginSession()));
                	ischeck = false;
                	isEnd = true;
    			}
    			}
    			Thread.sleep(1000);
    		}	
		}
    	catch (Exception e) {
			// TODO: handle exception
			System.out.println("스레드 에러 "+e);
		} 
    }
}
