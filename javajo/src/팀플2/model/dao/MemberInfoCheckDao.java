package 팀플2.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import 팀플2.controller.GameController;
import 팀플2.controller.MemberController;
import 팀플2.controller.MemberInfoCheckController;

public class MemberInfoCheckDao {
	
	private static MemberInfoCheckDao instance = new MemberInfoCheckDao();
	public static MemberInfoCheckDao getInstance() { return instance; }
	private MemberInfoCheckDao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");	
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/blackjack", "admin", "12341234");
			//System.out.println("안내] DB연동 성공");
		} catch (Exception e) {
			System.out.println("안내] DB연동 실패 : " + e);
		}
	}
	public Connection conn; // DB연동 객체
	public PreparedStatement ps; // 연동된 DB에서 SQL 조작하는 객체
	public ResultSet rs; // SQL 조작 결과를 가져오는 객체
	
	public boolean exitRoomInMember(int me)
	{
		try {
			String sql = "delete from roomdetails where mno = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, me);
			
			int res = ps.executeUpdate();
			if(res != 1)
			{
				return false;
				
			}
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("퇴장 에러"+e);
			return false;
		}finally {
	         try {
	             ps.close();
	             
	             
	          } catch (Exception e2) {
	             System.out.println("closeerror"+e2);
	          }
	       }
		
	}
	//winCheck
	public boolean stayCountCheck(int myroom)
	{
		try {
			String sql = "select maxpeople = sum(stay) from room where rno = "+myroom;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			if(rs.getInt(1) == 1)
			{
				return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("스테이 카운터 에러");
		}finally {
			try {
				ps.close();
				rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println("스테이 카운터 close 에러"+e2);
			}
		}
		
		return false;
	}
	//
	public synchronized boolean gameExectiponCheck(int myroom)
	{
		try {
			String sql = "select \r\n"
					+ "    (a.nowpeople != count(b.mno)) or (0 < SUM( b.rddatetime < DATE_SUB(NOW(), INTERVAL 10 SECOND))) as gameend \r\n"
					+ "from \r\n"
					+ "    room a\r\n"
					+ "left JOIN\r\n"
					+ "    (select rno,rddatetime,mno from roomdetails) b\r\n"
					+ "ON\r\n"
					+ "    a.rno = b.rno\r\n"
					+ "WHERE\r\n"
					+ "    a.rno = ?;";
			//String sql = "select (a.nowpeople != count(b.mno)) from room a left JOIN (select * from roomdetails) b ON a.rno = b.rno WHERE a.rno = ? and b.mno != ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, myroom);
			rs = ps.executeQuery();
			int chk = 0;
			if(rs.next())
			{
				//System.out.println( rs.getString( gameend ) );
				chk = rs.getInt(1);
			}
			if(chk !=0)
			{
				deleteUserInRoom(myroom);
				System.out.println("게임에서 나간 유저 있음");
				return true;
			}else {return false;}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("db체크 에러"+e);
			return true;
		}finally {
			try {
				ps.close();
				if(null != rs)rs.close();
				
			} catch (Exception e2) {
				System.out.println("closeerror"+e2);
			}
		}
	
	}
	
	public void nowPeopleUpdate(int mno) { // nowpeople 업데이트
		String sql = "update room set nowpeople = (select count(mno) from roomdetails) where rno = ?";
		int rno = RoomDao.getInstance().getRno(mno);
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,rno);
			ps.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("nowpeople 업데이트 에러"+e);
		}finally {
	         try {
	             ps.close();
	             
	             
	          } catch (Exception e2) {
	             System.out.println("closeerror"+e2);
	          }
	       }
	}


	public void deleteUserInRoom(int mno)
	{
		try {
			String sql = "delete from roomdetails where mno = ? and rddatetime < DATE_SUB(NOW(), INTERVAL 10 SECOND)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,mno);
			ps.executeUpdate();
			
			RoomDao.getInstance().changeHost(mno);
			nowPeopleUpdate(mno);
		} catch (Exception e) {
			System.out.println("삭제 에러"+e);
		}finally {
	         try {
	             ps.close();
	             
	          } catch (Exception e2) {
	             System.out.println("closeerror"+e2);
	          }
	       }
	}
	//자신의 응답시간 업데이트
	public void myResponseTimeUpdate(int me)
	{
		try {
			String sql = "update roomdetails set rddatetime = now() where mno = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, me);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("내 응답시간 업데이트 에러"+e);
		}finally {
	         try {
	             ps.close();
	             
	             
	          } catch (Exception e2) {
	             System.out.println("closeerror"+e2);
	          }
	       }
	}
}
