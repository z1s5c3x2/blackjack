
package 팀플1.Controller;

import 팀플1.model.dao.testdao;

import 팀플1.model.dao.testdao;
import 팀플1.model.dto.testdto;

public class Controller {

	
	
	// 싱글톤
	private static Controller controller = new Controller();
	public static Controller getInstanse() {
		return controller;
	}
	private Controller() {} 
	
	// 주문 
	public String makeAmericano(int 샷추가, int 연하게) {
		testdto dto = new testdto();
		dto.setBeans(5+샷추가-연하게);
		dto.setIce(10);
		dto.setMilk(0);
		dto.setStrb(0);
		dto.setSyrup(0);
		dto.setWarter(5);
		
		if(dto.getBeans() < testdao.coffeStock[0] && dto.getWarter() < testdao.coffeStock[1] && dto.getIce() < testdao.coffeStock[2] ) {
			testdao.coffeStock[0]-=dto.getBeans();
			testdao.coffeStock[1]-=dto.getWarter();
			testdao.coffeStock[2]-=dto.getIce();
			String outStr = "아메리카노 원두:" + dto.getBeans() + " 물:" + dto.getWarter() + " 얼음:" + dto.getIce();
			for(int i = 0 ; i < testdao.oder.length; i++) {
				if(testdao.oder[i] == null) {
					testdao.oder[i] = dto;
					return outStr;
				}
			}
			return "오늘은 마감되었습니다.";
		}
		return "재고가 부족합니다.";
	}
	
	public String makeBerryLatte() {
		testdto dto = new testdto();
		dto.setBeans(0);
		dto.setIce(10);
		dto.setMilk(10);
		dto.setStrb(5);
		dto.setSyrup(5);
		dto.setWarter(0);
		
		if(dto.getIce() < testdao.coffeStock[2] && dto.getMilk() < testdao.coffeStock[3] && dto.getStrb() < testdao.coffeStock[5] &&  dto.getSyrup() < testdao.coffeStock[4]) {
			testdao.coffeStock[2]-=dto.getIce();
			testdao.coffeStock[3]-=dto.getMilk();
			testdao.coffeStock[4]-=dto.getStrb();
			testdao.coffeStock[5]-=dto.getSyrup();
			String outStr = "딸기라떼 얼음:" + dto.getIce() + " 우유:" + dto.getMilk() + " 딸기:" + dto.getStrb() + " 시럽:" + dto.getSyrup();
			for(int i = 0 ; i < testdao.oder.length; i++) {
				if(testdao.oder[i] == null) {
					testdao.oder[i] = dto;
					return outStr;
				}
			}
			return "오늘은 마감되었습니다.";
		}
		return "재고가 부족합니다.";
	}
	
	public String makeMacchiato(int 연하게, int 진하게) {
		testdto dto = new testdto();
		dto.setBeans(5+진하게-연하게);
		dto.setIce(10);
		dto.setMilk(10);
		dto.setStrb(0);
		dto.setSyrup(10);
		dto.setWarter(0);
		
		if(dto.getBeans() < testdao.coffeStock[0] && dto.getWarter() < testdao.coffeStock[1] && dto.getIce() < testdao.coffeStock[2] && dto.getMilk() < testdao.coffeStock[3] ) {
			testdao.coffeStock[0]-=dto.getBeans();
			testdao.coffeStock[1]-=dto.getWarter();
			testdao.coffeStock[2]-=dto.getIce();
			testdao.coffeStock[3]-=dto.getSyrup();
			String outStr = "카라멜마끼야또 원두:" + dto.getBeans() + " 물:" + dto.getWarter() + " 얼음:" + dto.getIce() + " 시럽:" + dto.getSyrup();
			for(int i = 0 ; i < testdao.oder.length; i++) {
				if(testdao.oder[i] == null) {
					testdao.oder[i] = dto;
					return outStr;
				}
			}
			return "오늘은 마감되었습니다.";
		}
		return "재고가 부족합니다.";
	}
}
