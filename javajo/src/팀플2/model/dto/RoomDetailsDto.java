package 팀플2.model.dto;

public class RoomDetailsDto {
	private int rdno;
	private int rno;
    private int mno;
    private String rddatetime;
    private int rdready; 
    private String holdcard;
    
    public RoomDetailsDto() {
		// TODO Auto-generated constructor stub
	}
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return super.toString();
    }

    public RoomDetailsDto(int mno) {
    	this.mno = mno;
    }


	public RoomDetailsDto(int rdno, int rno, int mno, String rddatetime, int rdready, String holdcard) {
		super();
		this.rdno = rdno;
		this.rno = rno;
		this.mno = mno;
		this.rddatetime = rddatetime;
		this.rdready = rdready;
		this.holdcard = holdcard;
	}
	public int getRdno() {
		return rdno;
	}
	public void setRdno(int rdno) {
		this.rdno = rdno;
	}
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public int getMno() {
		return mno;
	}
	public void setMno(int mno) {
		this.mno = mno;
	}
	public String getRddatetime() {
		return rddatetime;
	}
	public void setRddatetime(String rddatetime) {
		this.rddatetime = rddatetime;
	}
	public int getRdready() {
		return rdready;
	}
	public void setRdready(int rdready) {
		this.rdready = rdready;
	}
	public String getHoldcard() {
		return holdcard;
	}
	public void setHoldcard(String holdcard) {
		this.holdcard = holdcard;
	}
    
}
