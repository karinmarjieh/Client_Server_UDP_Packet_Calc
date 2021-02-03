package interviews;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class tester {

	public static void main(String[] args) 
	{
		int port = 5432;
		//UdpCalculator calc= new UdpCalculator();//v1
		UDPPacketCalculator calc= new UDPPacketCalculator();//v2
		UdpUnicastServer server= new UdpUnicastServer(port);
		UdpUnicastClient client= new UdpUnicastClient(port, calc);
		
		ExecutorService executorService= Executors.newFixedThreadPool(2);
		executorService.submit(client);
		executorService.submit(server);
		
//		for (int i = 1; i < 20 ; i+=3) 
//		{
//			calc.OnPacketReceived(i);
//			System.out.println("New Packet Receivd " );
//			System.out.println("Current Loss is"+calc.GetCurrentLoss());
//			System.out.println("Average Loss is"+calc.GetAverageLoss());
//		}
		
	}

}
