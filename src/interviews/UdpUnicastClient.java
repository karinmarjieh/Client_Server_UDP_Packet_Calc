
package interviews;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpUnicastClient implements Runnable 
{
	private final int port ;
	private UdpCalculator calc = null;
	
	public UdpUnicastClient(int port ,UdpCalculator udpc)
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
			
			while(true)//receive messages here
			{
				DatagramPacket datagramPacket = new DatagramPacket(buffer,0 ,buffer.length);
				
				clientSocket.receive(datagramPacket);
				
				//int serialNumber=datagramPacket1.getSerialNumber()???/im not sure how to get the serial number
				this.calc.OnPacketReceived(serialNumber);
				System.out.println("New Packet Receivd "+serialNumber);
				System.out.println("Current Loss is "+this.calc.GetCurrentLoss()+"%");
				System.out.println("Average Loss is "+this.calc.GetAverageLoss()+"%");
				
				serialNumber+=5;//make a gap of 5
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
