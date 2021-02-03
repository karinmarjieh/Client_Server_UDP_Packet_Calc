package interviews;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpUnicastServer implements Runnable {
	//A class that implements Runnable can run without subclassing Thread by instantiating a Thread instance and passing itself in as the target.
	private final int clientPort;
	
	public UdpUnicastServer(int clientPort) 
	{
		this.clientPort=clientPort;
	}

	@Override
	public void run() {
		try( DatagramSocket serverSocket=new DatagramSocket(5000) )
		{
			for(int i=0 ; i<20 ; i++)
			{
				String message="Message number" + i ;
				DatagramPacket datagramPacket1 = new DatagramPacket(
						message.getBytes(), message.length() ,
						InetAddress.getLocalHost(),clientPort);
				
				serverSocket.send(datagramPacket1);
			}
		}
		catch(SocketException e)
		{
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
