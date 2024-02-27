package 팀플2.controller;

import java.util.ArrayList;

import org.apache.catalina.tribes.util.Arrays;

import 팀플2.model.dao.MemberDao;
import 팀플2.model.dao.MemberInfoCheckDao;
import 팀플2.model.dao.RoomDao;
import 팀플2.model.dao.RoomDetailsDao;
import 팀플2.model.dto.MemberDto;
import 팀플2.model.dto.RoomDetailsDto;
import 팀플2.model.dto.RoomDto;


public class GameController {
	
	
	/*
	 	public static void main(String[] args) {
		int result = GameController.gameController.winCheck(2);
		int result2 = GameController.gameController.cardScore(2);
		//int result3 = GameController.gameController.cardScore(1);
		boolean result3 = GameController.gameController.startCheck(2);
		System.out.println(result);
		System.out.println(result2);
		System.out.println(result3);
	}
	 */
	 	
	

	//싱글톤
	private static GameController gameController = new GameController();
	public static GameController getInstance () { return gameController; }
	private GameController () { }
	
   // hit 선택
   public String hitGoLosic(int rno) {
      
      // 남아있는 카드리스트의 맨 마지막 카드를 num에 저장 
      int num = RoomDao.getInstance().cardlist.get(RoomDao.getInstance().cardlist.size()-1);
      
      // 남아있는 카드리스트의 마지막 카드를 없앰
      RoomDao.getInstance().cardlist.remove(RoomDao.getInstance().cardlist.size()-1);
      
      // 변동된 남아있는 카드리스트 DB에 업데이트
      RoomDao.getInstance().cardSuffleSQL(rno, RoomDao.getInstance().cardlist);
      
      // 사용자에게 보여줄 카드로 변환
      String card = cardInfo(num);
      
      // 지금 로그인한 회원의 카드리스트 DB에 업데이트 
      RoomDetailsDao.getIntance().myCardGetSQL(MemberController.getInstance().getLoginSession(),Integer.toString(num));
      
      // 카드 반환
      return card;
   }
	
	// stay 선택
	public boolean stayGoLosic(int rno) {
		return RoomDao.getInstance().stayGoSQL(MemberController.getInstance().getLoginSession(), rno);
	}
	
	// 상대방 턴 넘기기
	public int turnOverLosic(int rno) {
		return RoomDao.getInstance().turnOverSQL(rno,MemberController.getInstance().getLoginSession());
	}
	
	// cno(인덱스 번호) 기준으로 모양+숫자 
	public String cardInfo(int cno) { // 카드 정보 확인 함수
		String[] suits = {"♠", "♥", "◆", "♣"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        String[] cards = new String[52];
        
        int index = 0;
        for (String suit : suits) {
            for (String rank : ranks) {
                cards[index] = suit + "," + rank;
                index++;
            }
        }
       return cards[cno-1]; // 카드 인덱스 받아 ♠,2 형태로 반환
	} // 카드 정보 확인 종료
	
	
	
	// 메서드 영역
	
	public String cardPrint(int mno) { // 보유 카드 출력 로직
		RoomDetailsDto dto = new RoomDetailsDto(); // dto 객체 생성
		dto.setMno(mno); // dto에 외원 번호 세팅
		RoomDetailsDao.getIntance().holdCard(dto);			// dao에 dto 객체 전달하여 dao에서 소유 카드 정보 dto 객체에 저장
		 
		if(!dto.getHoldcard().equals("")) { // 보유 카드가 있을때만 실행
			String[] holdcard = dto.getHoldcard().split(",");	// dto 객체에 저장된 정보 중 소유 카드들 반환하여 쉼표로 구분 후 배열에 저장
			
			StringBuilder sb = new StringBuilder();				// String 객체를 저장해두는 클래스 생성
			
			for( int i=0; i<holdcard.length; i++) {				// 보유 카드 수만큼 반복문 실행
				String[] card = cardInfo(Integer.parseInt(holdcard[i])).split(",");	// 카드 정보 확인 함수에 카드 인덱스 번호 인수로 전달
																					//카드 정보 반환하여 카드 종류, 숫자 쉼표로 구분하여 배열에 저장
				sb.append(card[0]).append(card[1]+" ");			//쉼표로 구분된 카드 합쳐서 쉼표 제거 후 카드 정보 String 객체에 순차적으로 저장
				
			}
			
			//System.out.println();
			return(sb+""); // String 객체에 저장된 소유 카드 정보 리턴
		}
		else {
			return null; // 남은 카드가 없으면 null 반환
			
		}
	} // 보유 카드 출력 로직 종료
	
	public int cardScore(int mno) { // 카드 점수 합산 로직
		
		RoomDetailsDto dto = new RoomDetailsDto();
		dto.setMno(mno);
		RoomDetailsDao.getIntance().holdCard(dto);
		
		if(dto.getHoldcard()!=null) { // 보유 카드가 null이 아닐 때 실행
			String[] holdCard = dto.getHoldcard().split(",");
			int sumScore = 0; int sumA = 0; int score = 0; // 리턴될 합산 점수, A카드 소지 갯수, 점수 합산용 변수(score) 선언
			 				
			for( int i=0; i<holdCard.length; i++) {
				String card = cardInfo(Integer.parseInt(holdCard[i])).split(",")[1]; // 소지중인 카드 인덱스 번호를 카드 확인용 함수 매개변수로 전달하여 카드 정보 반환
						 															// 카드종류,카드번호로 카드 정보가 저장되어 있어 쉼표로 구분 후 카드 번호만 저장
				if(card.charAt(0)=='A') { sumA++; score = 11; }	// 반환된 카드 번호가 A일 경우 A 소지 갯수 증가, score 11로 저장
				
				else if ((int)card.charAt(0)>=65 || card.equals("10")) { //카드 숫자가 10이거나, 소지 카드가 영문 대문자 일 경우 아스키 코드가 65이상이므로 65이상일 경우 score 10으로 저장
				score = 10;
				}
				else { // 그 외에는 카드 번호가 숫자이므로 아스키코드에서 48값을 빼면 소지 카드 숫자와 일치하므로 카드 숫자 번호를 score에 저장
				score = (int)card.charAt(0)-48;
				}
				
				sumScore += score; // j 반복문 (소지 카드 갯수) 만큼 점수 합산하여 cardScore 배열에 저장
				}
				
			for( int j=0; j<sumA; j++) { // j 반복문 종료(소지 카드 갯수만큼 점수 총 합산 완료)후 점수가 21점 초과면 A 카드 소지 갯수 만큼 10씩 차감, 반복 조건 실행으로 21 초과일때만 실행
				if(sumScore>21) {
					sumScore -= 10;
				}
			}
			return sumScore; // 카드 합산 점수 반환
			
		} else {
			return 0; // 보유 카드가 없으면 0 반환
		}
	} // 카드 점수 합산 로직 종료
	
	
	public int winCheck(int mno) { // 승리 조건 확인 시작 [ 리턴 값 (-1 : 무승부), (0 : 턴 넘김), (>1 해당 회원번호 승리) ]
		
		RoomDetailsDto detailDto = new RoomDetailsDto(); // 회원 번호 전달할 dto 생성
		
		detailDto.setMno(mno); // dto에 회원번호 저장
		
		RoomDto roomDto = RoomDao.getInstance().getRoomInfo(detailDto); // 방 정보 DAO에서 객체로 호출
		 
		int maxPeople = roomDto.getMaxpeople(); // 호출된 객체에서 방 최대 인원 저장
		int stay = roomDto.getStay(); // stay을 선언한 회원 수 저장

		
		if(stay == maxPeople || roomDto.getCardlist()!=null || !roomDto.getCardlist().equals("")) { // 방 최대인원만큼 stay 선언 시 결과 비교 실행
			ArrayList<RoomDetailsDto> roomDetailsList = RoomDetailsDao.getIntance().getRoomDetailsInfo(detailDto);  // 회원 정보로 전달한 방 정보 DAO에서 불러온 객체 리스트 변수 저장
			String[] holdCardInfo = new String[roomDetailsList.size()]; // 회원이 가진 소지 카드 합산 점수 저장할 배열 선언			
			for( int i=0; i<roomDetailsList.size(); i++ ) { // 카드 점수 합산하여 배열에 담을 for문 시작
				int _mno = roomDetailsList.get(i).getMno();  // 회원 번호 for문에 맞춰 순차적으로 호출
				int cardScore = cardScore(_mno);				// 점수 합산 함수에 회원 번호 인수로 전달하여 합산 점수 매개변수로 받아 변수에 저장
				
				holdCardInfo[i] = _mno + "," + cardScore; // 회원 소지 카드 배열에 회원번호 + 카드 총점 저장
			} // i for문 종료
			/*----------------------------결과 비교 시작 ----------------------------*/
						
			int lose = 0; // 21 초과(패배) 플레이어 수 확인용 변수 선언
			int winner = 0; // 승리 플레이어 확인 용 변수 선언
			int maxScore = -1; // 21 이하의 최대값 담을 변수
			int draw = 0; // 최고 점수가 동점이 나왔을 시 무승부 처리를 위한 변수
			
			for(int i=0; i<holdCardInfo.length; i++) { // 최종 결과 비교할 반복문 실행
				int score = Integer.parseInt(holdCardInfo[i].split(",")[1]); // 회원 소지 카드 배열에서 카드 합산 점수만 분리하여 저장
				//System.out.println("테스트)회원번호 "+Integer.parseInt(holdCardInfo[i].split(",")[0])+"의 카드 총합 "+score);
				if(score > 21) { // 카드 합산 점수가 21초과시 패배 플레이어 수 증가
					lose++;
				}
				else if (score > maxScore) { // 합산 점수가 21 미만이고 현재 저장된 최대 점수보다 높으면 최대 점수 교체, winner 변수에 회원 번호 임시 저장
					maxScore = score;
					winner = Integer.parseInt(holdCardInfo[i].split(",")[0]);
				}
			} // 반복문 종료
			
			for(int i=0; i<holdCardInfo.length; i++) { // 최대 스코어 동점자가 있는지 확인하여 무승부 여부 확인
				if(Integer.parseInt(holdCardInfo[i].split(",")[1])==maxScore) {
					draw++;
				}
			} // 반복문 종료
			
			if(draw > 1 || lose == maxPeople) {
				resultSave(mno,-1); return -1; // 최고 점수 동점자 발생 또는 패배 플레이어 수가 방 최대 인원과 같을때는 -1(무승부) 리턴
			}
			else { // 그 외 조건에서 21이하의 최대 점수의 회원 번호를 리턴 ( 승리 플레이어 회원 번호 )
				resultSave(mno,winner); return winner;
			}
			
		} // 승리 조건 확인용 if 문 종료
		
		return 0; // stay 수가 방 최대 인원에 미달 시 0(턴 넘김) 반환
	
	} // 승리 조건 확인 함수 종료
	
	public void resultSave (int mno, int result) { // 승리, 패배, 무승부 결과 저장 함수
		
		RoomDetailsDto dto = new RoomDetailsDto();
		dto.setMno(mno);
		
		ArrayList<RoomDetailsDto> detailsDtoList = RoomDetailsDao.getIntance().getRoomDetailsInfo(dto);
		
		if(result==-1) { // 무승부 시
			
			for(int i=0; i<detailsDtoList.size(); i++) {
				RoomDetailsDao.getIntance().playCountUpdate(detailsDtoList.get(i).getMno());
			}
			return;
		} // 무승부 시 e
		
		if(result>0) { // 특정 플레이어 승리 시
			for(int i=0; i<detailsDtoList.size(); i++) {
				RoomDetailsDao.getIntance().playCountUpdate(detailsDtoList.get(i).getMno());
				if(result==detailsDtoList.get(i).getMno()) {
					RoomDetailsDao.getIntance().winsUpdate(detailsDtoList.get(i).getMno());
				}else {
					RoomDetailsDao.getIntance().losesUpdate(detailsDtoList.get(i).getMno());
				}
			}
			return;
		} // 특정 플레이어 승리 e
		
	} // 결과 저장 종료
	
	public void gameOutResult(int mno) { // 승리, 패배, 무승부 결과 저장 함수
		
		RoomDetailsDto dto = new RoomDetailsDto();
		dto.setMno(mno);
		
		ArrayList<RoomDetailsDto> detailsDtoList = RoomDetailsDao.getIntance().getRoomDetailsInfo(dto);
		
	
			for(int i=0; i<detailsDtoList.size(); i++) {
				RoomDetailsDao.getIntance().playCountUpdate(detailsDtoList.get(i).getMno());
				if(mno==detailsDtoList.get(i).getMno()) {
					RoomDetailsDao.getIntance().losesUpdate(detailsDtoList.get(i).getMno());
				}else {
					RoomDetailsDao.getIntance().winsUpdate(detailsDtoList.get(i).getMno());
				}
			}
			return;
	
		
	} // 결과 저장 종료
	
	public ArrayList<String> resultPrint(int mno) { // 게임 종료 시 모든 플레이어 보유 카드 출력
		ArrayList<String> strList = new ArrayList<>();
		
		RoomDetailsDto dto = new RoomDetailsDto();
		dto.setMno(mno);
		ArrayList<RoomDetailsDto> detailsDtoList = RoomDetailsDao.getIntance().getRoomDetailsInfo(dto);
		
		for(int i=0; i<detailsDtoList.size(); i++) {
			String str = new String();
			int no =  detailsDtoList.get(i).getMno();
			str = no+","+MemberDao.getInstance().getMidSQL(no)+","+cardPrint(no) +","+cardScore(no);
			strList.add(str);
		}
		return strList;
		
		
		
	} // 함수 종료
	
	public boolean gameOut(int mno) {
		gameOutResult (mno);
		MemberInfoCheckDao.getInstance().deleteUserInRoom(mno);
		return true;
	}
	
	public boolean startCheck(int mno) { // 시작 가능 여부 체크
		RoomDetailsDto detailDto = new RoomDetailsDto(); // 회원 번호 전달할 dto 생성
		
		detailDto.setMno(mno); // dto에 회원번호 저장
		
		RoomDto roomDto = RoomDao.getInstance().getRoomInfo(detailDto); 
		
		int cardCount = roomDto.getCardlist().split(",").length;
		int nowPeople = roomDto.getNowpeople();
		
		if(cardCount>(nowPeople*5)) {
		
			return true;
		}
		else {
			return false;
		}
	} // 시작 여부 체크 종료
}
