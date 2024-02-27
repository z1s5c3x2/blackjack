package 팀플1.View;

import java.util.ArrayList;
import java.util.Scanner;

import 팀플1.Controller.Controller;

public class Index {

	private static Index index = new Index();

	public static Index getInstance() {
		return index;
	}
	private Index() {}
	public void Start()
	{
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		
		while(true)
		{
			ArrayList<String> orderList = new ArrayList<String>();

			int _op1 = 0;
			int _op2 = 0;
			int _op3 = 0;
			while (true)
			{
				System.out.println("1. 아이스아메리카노 2. 카라멜 마끼아또 3.딸기라떼 4. 주문");
				switch(sc.nextInt())
				{
					case 1: // 아이스 아메리카노
						_op1 = 0;
						_op2 = 0;
						
						boolean isDone = false;
						while(true)
						{
							System.out.println("1. 샷추가 2. 연하게 3.적용 4.기본" );
							switch(sc.nextInt())
							{
								case 1:
									_op1 ++;
									break;
								case 2:
									_op2 ++;
									break;
								case 3:
									isDone = true;
									break;
								case 4:
									_op1 = 0;
									_op2 = 0;
									isDone = true;
									break;				
							}
							if(isDone){
								orderList.add("아이스아메리카노 "+_op1+","+_op2);
								System.out.println("아이스 아메리카노 주문 추가");
								break;
							}
						}
						break;
					case 2: //카라멜
						_op1 = 0;
						_op2 = 0;
						_op3 = 0;
						isDone = false;
						while(true)
						{
							System.out.println("1. 샷추가 2. 연하게 3.적용 4.기본" );
							switch(sc.nextInt())
							{
								case 1:
									_op1 ++;
									break;
								case 2:
									_op2 ++;
									break;
								case 3:
									isDone = true;
									break;
								default:
									_op1 = 0;
									_op2 = 0;
									isDone = true;
									break;				
							}
							if(isDone){
								orderList.add("카라멜마끼아또 "+_op1+","+_op2);
								System.out.println("카라멜 마끼아또 주문 추가");
								break;
							}
						}
						break;
					case 3: // 딸기라떼
						orderList.add("딸기라떼 ");
						System.out.println("딸기 라떼 주문 추가");
						break;
					default: //결제
						ArrayList<String> _outprin = new ArrayList<String>();
						for(int i=0;i<orderList.size();i++)
						{
							String _tmp = orderList.get(i);
							System.out.println(_tmp);
							if(_tmp.split(" ")[0].equals("아이스아메리카노"))
							{	
								int a = Integer.parseInt(_tmp.split(" ")[1].split(",")[0]);
								int b = Integer.parseInt(_tmp.split(" ")[1].split(",")[1]);
								_outprin.add(Controller.getInstanse().makeAmericano(a, b));
							}
							else if(_tmp.split(" ")[0].equals("카라멜마끼아또"))
							{
								int a = Integer.parseInt(_tmp.split(" ")[1].split(",")[0]);
								int b = Integer.parseInt(_tmp.split(" ")[1].split(",")[1]);

								_outprin.add(Controller.getInstanse().makeMacchiato(a, b));
							}
							else{ //딸기라떼
								_outprin.add(Controller.getInstanse().makeBerryLatte());
							}
						}
						for(int i=0;i<_outprin.size();i++)
						{
							System.out.println(_outprin.get(i));
						}
						orderList = new ArrayList<String>();
						break;
						
					
				}
			}
			
		}
	}

}
