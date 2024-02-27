package 팀플2.model.dao;

public class RoomCreateDao extends Dao{
	
	private static RoomCreateDao rommJoinDao = new RoomCreateDao();
	public static RoomCreateDao getInstance() {return rommJoinDao;}
	private RoomCreateDao() {}
	
	
	public int roomCreateSQL(int mno) {
		//System.out.println("방생성 다오 도착");
	
		try {
		String sql = "insert into room(nowpeople , maxpeople, stay , turn , host , cardlist) values (0 , 2 , 0, ? , ? , ?)";
		
		ps = conn.prepareStatement(sql);
		ps.setInt(1, mno);
		ps.setInt(2, mno);
		ps.setString(3, null);
		ps.executeUpdate();
		
		roomFirstJoin(mno);
		
		return 1;
		}
		catch (Exception e) {
			System.out.println("roomCreate 에서 오류" + e);
		}
		finally {
	         try {
	            ps.close();
	            
	            
	         } catch (Exception e2) {
	            System.out.println("closeerror"+e2);
	         }
	      }
		return 0;
		
	}// roomCreateSQlL
	
	
	public boolean roomFirstJoin(int mno) {
		try {
			String sql = "insert into roomdetails(rno,mno,rdready) values ((select rno from room where host = ?),?,0);";
					
			ps = conn.prepareStatement(sql);
			ps.setInt(1,mno );
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
	       }return false;
		
	}
	
	
	
}
