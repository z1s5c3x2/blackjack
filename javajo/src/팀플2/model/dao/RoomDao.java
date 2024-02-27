package 팀플2.model.dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import 팀플2.controller.RoomController;
import 팀플2.model.dto.RoomDetailsDto;
import 팀플2.model.dto.RoomDto;

public class RoomDao extends Dao {
   private static RoomDao roomDao = new RoomDao();
   public static RoomDao getInstance() { return roomDao;}
   private RoomDao() {}
   public List<Integer> cardlist;
   
   
   public final HashMap<Integer,String> cardWhat = new HashMap<Integer,String>() {{
      put(1,"♠ A");   put(2,"♠ 2");   put(3,"♠ 3");   put(4,"♠ 4");   put(5,"♠ 5");
      put(6,"♠ 6");   put(7,"♠ 7");   put(8,"♠ 8");   put(9,"♠ 9");   put(10,"♠ 10");
      put(11,"♠ J");   put(12,"♠ Q");   put(13,"♠ K");   put(14,"♥ A");   put(15,"♥ 2");
      put(16,"♥ 3");   put(17,"♥ 4");   put(18,"♥ 5");   put(19,"♥ 6");   put(20,"♥ 7");
      put(21,"♥ 8");   put(22,"♥ 9");   put(23,"♥ 10");   put(24,"♥ J");   put(25,"♥ Q");
      put(26,"♥ K");   put(27,"♣ A");   put(28,"♣ 2");   put(29,"♣ 3");   put(30,"♣ 4");
      put(31,"♣ 5");   put(32,"♣ 6");   put(33,"♣ 7");   put(34,"♣ 8");   put(35,"♣ 9");
      put(36,"♣ 10");   put(37,"♣ J");   put(38,"♣ Q");   put(39,"♣ K");   put(40,"◆ A");
      put(41,"◆ 2");   put(42,"◆ 3");   put(43,"◆ 4");   put(44,"◆ 5");   put(45,"◆ 6");
      put(46,"◆ 7");   put(47,"◆ 8");   put(48,"◆ 9");   put(49,"◆ 10");   put(50,"◆ J");
      put(51,"◆ Q");   put(52,"◆ K");
   }};
   
   
   // 카드 섞기
   
   public boolean cardSuffleSQL(int rno, List<Integer> cardlist) {
     
     // 남아있는 카드리스트 갱신 
      this.cardlist = cardlist;
      
      // 현재 방의 남아있는 카드리스트를 갱신해주는 쿼리문 작성 
      String sql = "update room set cardlist = ? where rno = ?";
      
      // 남아있는 카드리스트를 저장할 변수 선언 
      String str = "";
      
      // ,(쉼표)를 구분하여 저장
      for(int i = 0; i < cardlist.size(); i++) {
         str += cardlist.get(i);
         if(i != cardlist.size()-1)
            str += ",";
      }
      
      try {
         
         ps = conn.prepareStatement(sql);
         // ,(쉼표)를 이용해 저장한 카드리스트를 삽입 
         ps.setString(1, str);
         // 현재 방번호 삽입
         ps.setInt(2, rno);
         int row = ps.executeUpdate();
         // 결과가 나온다면 true
         if(row == 1)
            return true;
         
      }catch(Exception e) {
         System.out.println(e);
      }finally {
          try {
             // 썼던 ps 닫기 
              ps.close();
              
              
           } catch (Exception e2) {
              System.out.println("closeerror"+e2);
           }
        }
      
      return false;
   }

   // 카드 리스트 가져오기 
   public void getCardList(int rno) {
	   
	   String sql = "select cardlist from room where rno = ?";
	   
	   try {
		   ps = conn.prepareStatement(sql);
		   ps.setInt(1, rno);
		   rs = ps.executeQuery();
		   rs.next();
		   String str = rs.getString(1);
		   String[] str2 = str.split(",");
		   int[] nums = new int[str2.length];
		   
		   for(int i = 0; i < str2.length; i++) {
			   nums[i] = Integer.parseInt(str2[i]); 
		   }
		   System.out.println(nums.length);
		   
		   List<Integer> numslist = new ArrayList<>();
		   for(int i = 0; i < nums.length; i++) {
			   numslist.add(nums[i]);
		   } 
		   
		   cardlist = new ArrayList<>(numslist);
 
		   
		   
		   
		   
	   }catch(Exception e) {
		   System.out.println("getCardList 오류 : " + e);
	   }finally {
		   try {
			   // 썼던 ps 닫기 
               ps.close();
               if(rs != null) rs.close();
               
           } catch (Exception e2) {
               System.out.println("closeerror"+e2);
           }
       }
	   
   }

   // stay 하기
   public boolean stayGoSQL(int mno, int rno) {
      
      String sql = "update room set stay = stay+1 where rno = ?";
      try {
         ps = conn.prepareStatement(sql);
         ps.setInt(1, rno);
         int row = ps.executeUpdate();
         if(row == 1) return true;
      }catch(Exception e) {
         System.out.println(e);
      }finally {
          try {
              ps.close();
              
              
           } catch (Exception e2) {
              System.out.println("closeerror"+e2);
           }
        }
      
      
      return false;
   }
   


   
   // 상대방 턴 넘기기 
   public int turnOverSQL(int rno, int mno) { // -1 : sql 문제 0 : 승리 계산 (turn 할 멤버가 없음) 1 : turn 완료
      
      
      int nextTurn = getTurnSQL(rno, mno);
      if(nextTurn == -1) return 0;
      
      String sql = "update room set turn = ? where rno = ?";
      try {
         ps = conn.prepareStatement(sql);
         ps.setInt(1, nextTurn);
         ps.setInt(2, rno);
         int row = ps.executeUpdate();
         if(row == 1) return 1;
      }catch(Exception e) {
         System.out.println(e);
      }finally {
          try {
              ps.close();
              if(null != rs)rs.close();
              
           } catch (Exception e2) {
              System.out.println("closeerror"+e2);
           }
        }
      
      return -1;
   }
   
   // 현재 turn의 rdno 가져오기
   public int getTurnSQL(int rno, int mno) {
      String sql = "select rdno from roomdetails where rno = ? and mno = ?";
      try {
         ps = conn.prepareStatement(sql);
         ps.setInt(1, rno);
         ps.setInt(2, mno);
         rs = ps.executeQuery();
         rs.next();
         return getNextTurnSQL(rs.getInt(1), rno);
      }catch(Exception e) {
         System.out.println(e);
      }finally {
          try {
              ps.close();
              if(null != rs)rs.close();
              
           } catch (Exception e2) {
              System.out.println("closeerror"+e2);
           }
        }
      return -1;
   }
   
   
   // 다음 턴 mno 가져오기 
   public int getNextTurnSQL(int turn, int rno) {
      String sql = "select mno from roomdetails where rno = ? and rdno > ? limit 1";
      try {
         ps = conn.prepareStatement(sql);
         ps.setInt(1, rno);
         ps.setInt(2, turn);
         rs = ps.executeQuery();
         if(rs.next()) return rs.getInt(1);
         else return -1;
      }catch(Exception e) {
         System.out.println(e);
      }finally {
          try {
              ps.close();
              if(null != rs)rs.close();
              
           } catch (Exception e2) {
              System.out.println("closeerror"+e2);
           }
        }
      return -1;
   }
   public int getRno(int mno) { // 회원 정보로 방 번호 반환하는 sql문
	      String sql = "select rno from roomdetails where mno = ?";
	      
	      try {
	         ps = conn.prepareStatement(sql);
	         ps.setInt(1, mno);
	         rs = ps.executeQuery();
	         
	         rs.next();
	         
	         return rs.getInt(1);
	      }catch (Exception e) {
	         System.out.println("방 번호 반환 DAO 예외 : "+e);
	         return 0;
	      }finally {
	          try {
	              ps.close();
	              if(null != rs)rs.close();
	              
	           } catch (Exception e2) {
	              System.out.println("closeerror"+e2);
	           }
	        }
	      
	  }
   public int getRoomTurn(int room)
   {
	   try {
		String sql = "select turn from room where rno = "+room;
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		if(rs.next()) {return rs.getInt(1);}
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println("턴 셀렉트 에러"+e);
		
	}finally {
		try {
			ps.close();
			rs.close();
		} catch (Exception e2) {
			// TODO: handle exception
			System.out.println("턴 셀렉트 클로즈 에러"+e2);
		}
	}
	   return -2;
   }
   // room 테이블 가져오기
   public RoomDto getRoomInfo(RoomDetailsDto Dto){ // 방 정보 반환 SQL문

	      String sql = "select * from room where rno = ?";
	      System.out.println();
	      int rno = getRno(Dto.getMno());
	      try {
	         ps = conn.prepareStatement(sql);
	         ps.setInt(1, rno);
	         rs = ps.executeQuery();
	         
	         rs.next();
	         
	            RoomDto dto = new RoomDto();
	            
	            dto.setRno(rs.getInt(1));
	            dto.setNowpeople(rs.getInt(2));
	            dto.setMaxpeople(rs.getInt(3));
	            dto.setStay(rs.getInt(4));
	            dto.setTurn(rs.getInt(5));
	            dto.setHost(rs.getInt(6));
	            dto.setCardlist(rs.getString(7));
            
	         return dto;
	         
	      }catch (Exception e) {
	         System.out.println("getRoomInfo 예외 발생 : "+e);
	      
	         return null;
	      } finally {
	          try {
	              ps.close();
	              if(null != rs)rs.close();
	              
	           } catch (Exception e2) {
	              System.out.println("closeerror11"+e2);
	           }
	        }
   }   // Room 테이블 가져오기 종료
   public void roomInitStay(int rno)
   {
	   try {
		   String sql = "update roomdetails rd,room r set rd.rdready = 0 , r.stay =0 , holdcard = \"\" where rd.rno = ? and r.stay > 0";
		   ps = conn.prepareStatement(sql);
		   ps.setInt(1, rno);
		   ps.execute();
		   changeHost(rno);
	} catch (Exception e) {
			System.out.println("스테이 초기화 에러"+e);
	}finally {
		try {
			ps.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}
   }
   // 호스트 판별 
   public boolean getHost(int rno, int mno) {
	   
	   String sql = "select host from room where rno = ? and host = ?";
	   
	   try {
		   
		   ps = conn.prepareStatement(sql);
		   ps.setInt(1, rno);
		   ps.setInt(2, mno);
		   rs = ps.executeQuery();
		   if(rs.next()) return true;
		   
	   }catch(Exception e) {
		   System.out.println("getHost에러 : " + e);
	   }finally {
		   try {
			ps.close();
			if(rs != null) rs.close();
		} catch (SQLException e) {
			 System.out.println("getHost close에러 : " + e);
		}
		  
	   }
	   
	   return false;
   }
   
   // 제일 작은 rdno로 호스트 바꿔주기 
   public void changeHost(int rno) {
	   
	   int rdno = getMinRdno(rno);
	   
	   if(rdno==-1) return;
	   
	   String sql = "update room set turn = (select mno from roomdetails where rdno = ?) ,host = (select mno from roomdetails where rdno = ?) where rno = ?;";
	   
	   try {
		   ps = conn.prepareStatement(sql);
		   ps.setInt(1,rdno);
		   ps.setInt(2,rdno);
		   ps.setInt(3,rno);
		   ps.executeUpdate();
	   }catch(Exception e) {
		   System.out.println("changeHost 오류 : " + e);
	   }finally {
		   try {
			   ps.close();
		   }catch(Exception e){
			   System.out.println("getMinRdno close 오류 : " + e);
		   }
	   }
   }
   
   // 제일 작은 rdno 가져오기 
   public int getMinRdno(int rno) {
	   
	   String sql = "select rdno from roomdetails where rno = ? order by rdno limit 1";
	   
	   try {
		   ps = conn.prepareStatement(sql);
		   ps.setInt(1,rno);
		   rs = ps.executeQuery();
		   if(rs.next()) return rs.getInt(1); 
	   }catch(Exception e) {
		   System.out.println("getMinRdno 오류 : " + e);
	   }finally {
		   try {
			   ps.close();
			   if(rs!=null)rs.close();
		   }catch(Exception e){
			   System.out.println("getMinRdno close 오류 : " + e);
		   }
	   }
	   return -1;
   }
   

}