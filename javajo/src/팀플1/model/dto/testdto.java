package 팀플1.model.dto;

public class testdto {

	//1 필드 200 100 100 100 100 50
	
	private int Beans;	// 원두
	private int warter;	// 물
	private int ice;		// 얼음
	private int milk;		// 우유
	private int syrup;	// 시럽
	private int strB;		// 스트로베리 딸기
	
	public testdto () {}

	public testdto(int beans, int warter, int ice, int milk, int syrup, int strb) {
		super();
		Beans = beans;
		this.warter = warter;
		this.ice = ice;
		this.milk = milk;
		this.syrup = syrup;
		this.strB = strb;
	}

	public int getBeans() {
		return Beans;
	}

	public void setBeans(int beans) {
		Beans = beans;
	}

	public int getWarter() {
		return warter;
	}

	public void setWarter(int warter) {
		this.warter = warter;
	}

	public int getIce() {
		return ice;
	}

	public void setIce(int ice) {
		this.ice = ice;
	}

	public int getMilk() {
		return milk;
	}

	public void setMilk(int milk) {
		this.milk = milk;
	}

	public int getSyrup() {
		return syrup;
	}

	public void setSyrup(int syrup) {
		this.syrup = syrup;
	}

	public int getStrb() {
		return strB;
	}

	public void setStrb(int strb) {
		this.strB = strb;
	}

	@Override
	public String toString() {
		return "testdto [Beans=" + Beans + ", warter=" + warter + ", ice=" + ice + ", milk=" + milk + ", syrup=" + syrup
				+ ", strb=" + strB + "]";
	}
	
	
	
	
	
}
