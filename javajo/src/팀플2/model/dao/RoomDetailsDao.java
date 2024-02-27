package 팀플2.model.dao;

import java.util.ArrayList;
import java.util.List;


import 팀플2.controller.MemberInfoCheckController;

import 팀플2.model.dto.MemberDto;

import 팀플2.model.dto.RoomDetailsDto;

public class RoomDetailsDao extends Dao {
	
	// 싱글톤
	private static RoomDetailsDao roomDetailsDao = new RoomDetailsDao();
	public static RoomDetailsDao getIntance () { return roomDetailsDao; }
	private RoomDetailsDao () { }
	public void memberReady(int mno)
	{
		try {
			String sql = "update roomdetails set rdready = rdready +1 where mno = "+mno;
			ps = conn.prepareStatement(sql);
			ps.execute();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("준비 에러");
		}finally {
			try {
				ps.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
	}

	public ArrayList<String> checkRoomReadyCount(int rno)
	{
		try {
			String sql = "select if(a.maxpeople = sum(rdready),true,false) res from room a left join(select rdready,rno,mno from roomdetails) b on a.rno = b.rno where a.rno = "+rno;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			if(rs.getInt(1) == 1)
			{
				ArrayList<String> tmp = getEnterRoomMemberList(rno);
				if(tmp != null)
				{
					return tmp;
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("준비 카운트 에러"+e);
		
		}finally {
			try {
				ps.close();
				rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return null;
	}
	private ArrayList<String> getEnterRoomMemberList(int room)
	{
		ArrayList<String> memlist = new ArrayList<>();
		
		try {
			String sql = "select (select mid from member where mno = a.mno) from roomdetails a where rno = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, room);
			rs = ps.executeQuery();
			while(rs.next())
			{
				memlist.add(rs.getString(1));
			}
			return memlist;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("방에 입장한 멤버 가져오기  에러"+e);
			
		}finally {
			try {
				ps.close();
				rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println("멤버 리스트 호출 에러"+e2);
			}
		}
		
		return	null;
		
	}
	
	public boolean checkRoomTurn(int mno)
	{
		try {
			String sql = "select turn from room where rno = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, MemberInfoCheckController.getInstance().getRoomSession());
			rs = ps.executeQuery();
			rs.next();
			if(rs.getInt(1)== mno)
			{
				return true;
			}
			return false;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("턴 체크 에러"+e);
		}finally {
			try {
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("턴체크 close 에러"+e);
			}
		}
		
		return false;
	}
	// 보유 카드 갱신
	public boolean myCardGetSQL(int mno, String card) {
		
		RoomDetailsDto dto = new RoomDetailsDto(mno);
		// 현재 갖고 있는 카드리스트 가져오기 
		holdCard(dto);
		// 변수에 넣어주기 
		String holdcard = dto.getHoldcard();
		// ,(쉼표)를 이용해 배열로 저장 
		String[] holdcardArr = holdcard.split(",");
		// 카드리스트 담아줄 리스트 선언 
		ArrayList<String> holdcardList = new ArrayList<String>();
		
		// 배열에 있는 카드리스트 리스트로 옮겨주기 
		for(int i = 0; i < holdcardArr.length; i++) {
			if(!holdcardArr[i].equals("")) {
				holdcardList.add(holdcardArr[i]);
				
			}
		}
		
		// 방금 뽑은 카드 넣어주기
		holdcardList.add(card);
		
		// 카드리스트를 넣어줄 스트링 변수 선언
		String cardList = "";
		
		// 리스트에 있는 카드리스트를 ,(쉼표)를 이용해 스트링 변수에 넣어주기
		for(int i = 0 ; i < holdcardList.size(); i++) {
			cardList += holdcardList.get(i);
			if(i != holdcardList.size()-1) {
				cardList += ",";
			}
			
		}
		
		// 해당 멤버에 카드리스트 갱신하는 쿼리 작성
		String sql = "update roomdetails set holdcard = ?  where mno = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1,cardList);
			ps.setInt(2, mno);
			int row = ps.executeUpdate();
			if(row == 1) return true;
		}catch(Exception e) {
			System.out.println(e);
		}finally {
	         try {
	             ps.close();
	             
	             
	          } catch (Exception e2) {
	             System.out.println("closeer11ror"+e2);
	          }
	       }
		return false;
	}
	
	public ArrayList<RoomDetailsDto> getRoomDetailsInfo(RoomDetailsDto detailsDto){ // 룸디테일 테이블 가져오기
		String sql = "select * from roomdetails where rno = ? ";
		
		int mno = RoomDao.getInstance().getRno(detailsDto.getMno());
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mno);
			rs = ps.executeQuery();
			
			ArrayList<RoomDetailsDto> arrList = new ArrayList<>();
			
			while(rs.next()) {
				RoomDetailsDto dto = new RoomDetailsDto();
				
				dto.setRdno(rs.getInt(1));
				dto.setRno(rs.getInt(2));
				dto.setMno(rs.getInt(3));
				dto.setRddatetime(rs.getString(4));
				dto.setRdready(rs.getInt(5));
				dto.setHoldcard(rs.getString(6));
				arrList.add(dto);
				}
			return arrList;
			
		}catch (Exception e) {
			System.out.println("getRoomInfo 예외 발생 : "+e);
		
			return null;
		}/*finally {
	         try {
	             ps.close();
	             if(null != rs)rs.close();
	             
	          } catch (Exception e2) {
	             System.out.println("close22error"+e2);
	          }
	       }*/
	}	// Roomdetails 테이블 가져오기 종료
	
	
	public RoomDetailsDto holdCard(RoomDetailsDto dto) { // 소유 카드 반환 SQL
		String sql = "select holdcard from roomdetails where mno = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dto.getMno());
			rs = ps.executeQuery();
			
			rs.next();
			dto.setHoldcard(rs.getString(1));
			
			return dto;
			
		}catch (Exception e) {
			System.out.println("cardPrint 예외 발생 : "+e);
			return null;
		}/*finally {
	         try {
	             ps.close();
	             if(null != rs)rs.close();
	             
	          } catch (Exception e2) {
	             System.out.println("closeerror"+e2);
	          }
	       }*/
	} // 소유 카드 반환 SQL 종료

	public void winsUpdate(int mno) { // 승리 수 증가 sql
		String sql = "update member set wins = wins+1 where mno = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mno);
			ps.executeUpdate();			
			
		}catch (Exception e) {
			System.out.println("승리수 증가 sql 예외 발생 : " +e);
		}
		
	} // 승리 수 증가 sql 종료
	
	public void losesUpdate(int mno) { // 패배 수 증가 sql
		String sql = "update member set loses = loses+1 where mno = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mno);
			ps.executeUpdate();			
			
		}catch (Exception e) {
			System.out.println("패배수 증가 sql 예외 발생 : " +e);
		}
		
	} // 패배 수 증가 sql 종료
	
	public void playCountUpdate(int mno) { // 게임 수 증가 sql
		String sql = "update member set playCount = playCount+1 where mno = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mno);
			ps.executeUpdate();			
			
		}catch (Exception e) {
			System.out.println("게임수 증가 sql 예외 발생 : " +e);
		}
		
	} // 게임 수 증가 sql 종료
	
	
	
}
