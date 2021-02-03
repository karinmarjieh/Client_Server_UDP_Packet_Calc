
package interviews;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

public class UdpUnicastClient implements Runnable 
{
	private final int port ;
	//private UdpCalculator calc = null;//v1
	private UDPPacketCalculator calc = null;//v2
	
	public UdpUnicastClient(int port , UDPPacketCalculator udpc)//v1 UdpCalculator udpc)
	{
		this.port=port;
		this.calc=udpc;
	}

	@Override
	public void run() 
	{
		try(DatagramSocket clientSocket=new DatagramSocket(port))
		{
			byte[] buffer = new byte[65507] ;
			//clientSocket.setSoTimeout(5000);//if nothing is recieved in 5 sec , stop listening to the server
			
			//int serialNumber = packet.getSerialNumber()???/im not how to get the serial number
			int serialNumber =1;// if i assume that SerialNumber starts from 1 and is sequential 
			Random rand = new Random();// for version 2
			while(true)//receive messages here
			{
				DatagramPacket datagramPacket = new DatagramPacket(buffer,0 ,buffer.length);
				
				clientSocket.receive(datagramPacket);
				
				//int serialNumber=datagramPacket1.getSerialNumber()???/im not sure how to get the serial number
				this.calc.OnPacketReceived(serialNumber);
				System.out.println("New Packet Receivd "+serialNumber);
				System.out.println("Current Loss is "+this.calc.GetCurrentLoss()+"%");
				System.out.println("Average Loss is "+this.calc.GetAverageLoss()+"% ");
				System.out.println();
				
				//serialNumber +=5;//for version 1
				serialNumber=rand.nextInt(100); //for version 2
			}
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
