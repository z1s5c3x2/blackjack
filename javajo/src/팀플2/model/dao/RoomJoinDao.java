package 팀플2.model.dao;

import java.util.ArrayList;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import 팀플2.model.dto.RoomDetailsDto;
import 팀플2.model.dto.RoomDto;

public class RoomJoinDao extends Dao{
	private static RoomJoinDao roomJoinDao = new RoomJoinDao();
	public static RoomJoinDao getInstance() {return roomJoinDao;}
	private RoomJoinDao () {}
	
	
	// 방 입장 메소드
	public boolean roomJoin(int rno , int mno) {
		

//		System.out.println("roomJoin");
		
		boolean result = maxPeopleCheck(rno);
		
		if(!result) return false;
		
		//System.out.println("??");

		try {
			String sql = "insert into roomdetails(rno,mno,rdready) values (?,?,0);";
					
			ps = conn.prepareStatement(sql);
			ps.setInt(1, rno );
			ps.setInt(2, mno);
			ps.executeUpdate();
			
			return true;
		} 
		
		catch (Exception e) {
		}finally {
	         try {
	             ps.close();
	             
	             
	          } catch (Exception e2) {
	             System.out.println("closeerror"+e2);
	          }
	       }
		return false;

		
	}//roomJoin
	
	// 들어가려는 방이 이미 인원 최대인지 체크
	public boolean maxPeopleCheck(int rno) {
		String sql = "select * from room where rno = ? and nowpeople >= maxpeople;";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, rno);
			rs = ps.executeQuery();
			
			if(rs.next()) { 
				return false;
			}
			return true;
		}catch (Exception e) {
			System.out.println("maxPeopleCheck 오류" + e);
		}finally {
	         try {
	             ps.close();
	             if(null != rs)rs.close();
	             
	          } catch (Exception e2) {
	             System.out.println("closeerror"+e2);
	          }
	    }
		
		
		return false;
	}
	
	//rno 가져오기 메소드
	public int roomRno(int mno) {
		try {
			String sql = "select rno from roomdetails where mno = ?;";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mno);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1);

			}//if
		} 
		catch (Exception e) {
			System.out.println("roomRno rno 찾기 오류" + e);
		}finally {
	         try {
	             ps.close();
	             if(null != rs)rs.close();
	             
	          } catch (Exception e2) {
	             System.out.println("closeerror"+e2);
	          }
	       }return 0;
	}
	public void roomNowPeopleUpdate(int rno)
	{
		try {	
			String sql = "update room set nowpeople = nowpeople+1 where rno = ? ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, rno);
			ps.execute();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("테스트조인에러"+e);
		}finally {
	         try {
	             ps.close();
	             
	             
	          } catch (Exception e2) {
	             System.out.println("closeerror"+e2);
	          }
	       }
	}
	
	// 방목록 출력 메소드
	public ArrayList<RoomDto> RoomInfoBc() {
		ArrayList<RoomDto> list = new ArrayList<>();
		
		try {
			String sql = "select rno , nowpeople , maxpeople from room";
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				RoomDto dto = new RoomDto(rs.getInt(1),rs.getInt(2),rs.getInt(3));  // 아니 18 rno 1개만 가져오는걸 못하겠음 개같네
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			
		}finally {
	         try {
	             ps.close();
	             if(null != rs)rs.close();
	             
	          } catch (Exception e2) {
	             System.out.println("closeerror"+e2);
	          }
	       }return list;
		
	}

	// 방번호 선택 후 방 입장
	public boolean roomChoice(int rno , int mno) {
		
		boolean result = maxPeopleCheck(rno);
		
		if(!result) return false;
		
		// 방에 추가 하면 나우피플 플러스 되도록 수정 해야 됨 // 룸 디테일에는 mno 다른 인원 추가 되는거 확인 완료
		try {
			String sql = "insert into roomdetails(rno,mno,rdready) values (?,?,0);";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, rno );
			ps.setInt(2, mno);
			ps.executeUpdate();
			roomNowPeopleUpdate(rno);
			return true;
			
		} 

		catch (Exception e) {
		}finally {
			try {
             ps.close();
             
             
			} catch (Exception e2) {
             System.out.println("closeerror"+e2);
			}
	    }return false;
	
		
	}
	
   // 방 나가기 메소드
   public boolean roomOut(int rno) {
      
      try {
         String sql = "update room set nowpeople = nowpeople-1 where rno = ? ";
         ps = conn.prepareStatement(sql);
         ps.setInt(1, rno);
         ps.execute();
         return true;
      } catch (Exception e) {System.out.println("방 나가기 sql 오류 : " + e);}
      finally {
    	  try {
    		  ps.close();
    	  } catch (Exception e2) {
    		  System.out.println("closeerror"+e2);
    	  }
	  }
      return false;
   }

  
}
