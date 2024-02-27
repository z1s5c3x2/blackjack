package 팀플2.model.dao;

import java.util.ArrayList;

import 팀플2.model.dto.MemberDto;

public class MemberDao extends Dao {
	
	private static MemberDao dao = new MemberDao();
	public static MemberDao getInstance() {return dao;}
	private MemberDao () {}
	
	

	// 회원가입 아이디 중복 검사
	public boolean infoCheckSQL(String serchFiled , String serchId ) {
		
		//System.out.println("아이디 중복검사 확인");
		try {
			// "select * from member where mid = "'p1';
			String sql = "select * from member where " + serchFiled + " = ?";
			//System.out.println(sql);

			ps = conn.prepareStatement(sql);
			ps.setString(1, serchId);
			rs = ps.executeQuery();
			
			if (rs.next()) {return true;}
		} // try

		catch (Exception e) {System.out.println("infocheckSql에서 오류 발생" + e);}finally {
	         try {
	             ps.close();
	             if(null != rs)rs.close();
	             
	          } catch (Exception e2) {
	             System.out.println("closeerror"+e2);
	          }
	       }
		
		return false;
	}// INFOCHECK
	
	
	//회원가입 SQL
	public boolean signupSQL(MemberDto dto) {
		//System.out.println("다오 정상 도착 완료");
		
		try {
			String sql = "insert into member(mid , mpw) values(? , ? )";

			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getMid());
			ps.setString(2, dto.getMpw());

			ps.executeUpdate();
			//System.out.println("회원등록 성공");
			return true;

		} // try
		catch (Exception e) {System.out.println("signupSQL 에서 오류발생 " + e);} // catch
		finally {
	         try {
	            ps.close();
	            
	            
	         } catch (Exception e2) {
	            System.out.println("closeerror"+e2);
	         }
	      }
		return false;
	}// signupSQL
	
	
	//로그인 SQL
	public int loginSQL(String id , String pw) {
		//System.out.println("로그인 SQL 정상 도착");
		
		try {
			String sql = "select * from member where mid = ? and mpw = ? ";

			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);

			rs = ps.executeQuery();

			if (rs.next()) {return rs.getInt(1);} // if
		} // try

		catch (Exception e) {
			System.out.println("loginSQL 에서 오류 발생 : " + e);} // catch
		finally {
	         try {
	            ps.close();
	            if(null != rs)rs.close();
	            
	         } catch (Exception e2) {
	            System.out.println("closeerror"+e2);
	         }
	      }
		return 0;
	} // loginSQL
	
	public String getMidSQL(int mno) { // 아이디 가져오기 함수
		String sql = "select mid from member where mno = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mno);
			rs = ps.executeQuery();
			
			rs.next();
			return rs.getString(1);
			
		}catch (Exception e) {
			System.out.println("아이디 가져오기 sql 예외 : "+e);
			return null;
		} // 아이디 가져오기 함수 종료 
		
	}
	
	
	
	
	
	
}//class
