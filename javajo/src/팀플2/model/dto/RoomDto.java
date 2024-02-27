package 팀플2.model.dto;

public class RoomDto {
	private int rno; 
	private int nowpeople; 
	private int maxpeople; 
	private int stay;
	private int turn;
	private int host;
    private String cardlist;  
    
    public RoomDto() {
		// TODO Auto-generated constructor stub
	}
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return super.toString();
    }
    
    
	public RoomDto(int rno, int nowpeople, int maxpeople) {
		super();
		this.rno = rno;
		this.nowpeople = nowpeople;
		this.maxpeople = maxpeople;

	}
    
    
    
	public RoomDto(int rno, int nowpeople, int maxpeople, int stay, int turn, int host, String cardlist) {
		super();
		this.rno = rno;
		this.nowpeople = nowpeople;
		this.maxpeople = maxpeople;
		this.stay = stay;
		this.turn = turn;
		this.host = host;
		this.cardlist = cardlist;
	}
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public int getNowpeople() {
		return nowpeople;
	}
	public void setNowpeople(int nowpeople) {
		this.nowpeople = nowpeople;
	}
	public int getMaxpeople() {
		return maxpeople;
	}
	public void setMaxpeople(int maxpeople) {
		this.maxpeople = maxpeople;
	}
	public int getStay() {
		return stay;
	}
	public void setStay(int stay) {
		this.stay = stay;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}
	public int getHost() {
		return host;
	}
	public void setHost(int host) {
		this.host = host;
	}
	public String getCardlist() {
		return cardlist;
	}
	public void setCardlist(String cardlist) {
		this.cardlist = cardlist;
	}
}
