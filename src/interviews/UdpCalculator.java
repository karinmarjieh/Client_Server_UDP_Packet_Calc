package interviews;

import java.time.Instant;
import java.util.ArrayList;

public class UdpCalculator 
{
	
	private int latestNumber = 0;//it saves the serial number of the latest packet received
	private int amountLost = 0; // saves the number of packets lost
	private ArrayList<PacketInfo> packets = new ArrayList<>(); // stores the packets that were received in an array of packetInfo (serialNumber & time received)
	
	//public static final int MAX_PACKETS_IN_TWO_SEC=16 ; ////maxPacketsIn2Sec=16 we assume that in each sec 8 packets are recieved

	
	//
	public void OnPacketReceived(int packetSerialNumber) 
	{
		//assume that serial numbers are sequential
		
		
		int dif=packetSerialNumber-this.latestNumber; 
		
		if(packetSerialNumber != this.latestNumber+1) //it means that a packet or more were lost in the middle // as same as if ((packetSerialNumber-this.latestNumber)>1) or this.latestNumber + 1 != packetSerialNumber
		{ 
			this.amountLost += (dif-1); // calculate the number of lost packets in the middle 
		}
		
		this.packets.add(new PacketInfo(packetSerialNumber));//add the received packet to the array 
		this.latestNumber = packetSerialNumber; //let latest number be the serial number of the latest packet received 
		
		 //fun1(packetSerialNumber,  dif);
		
	}
	
	
	public int GetCurrentLoss()
	{
		
//*********** this will work only with the assumption below ************************************************************///
//          the assumption is that the server always sends 8 packets per second
//          constantly therefore in two seconds the max of packets that can be sent is 16 
//		
//		int lostPackets = MAX_PACKETS_IN_TWO_SEC;
//		
//		for(int i=packets.size()-1 ; i>=0 ; i--)//start from the end of the array of packets received 
//		{	
//			long packetTimeStamp = packets.get(i).timestamp;
//			long timePassed = ((Instant.now().toEpochMilli()- packetTimeStamp )/1000);//L)%60;//convert it to seconds
//			if (timePassed<=2)
//			{
//				lostPackets-- ;//compute the lost packets 
//			}
//			else 
//			{
//				return ( lostPackets / MAX_PACKETS_IN_TWO_SEC ) * 100;	
//			}
//		}
//		return (lostPackets/MAX_PACKETS_IN_TWO_SEC)*100;
//		
//**********************************************************************************************************************///

		
		int i=0,lostPackets=0;//count is the number of loss packages in two seconds
		long timeNow=Instant.now().toEpochMilli();

		ArrayList<PacketInfo> packetsValue  = new ArrayList<>(); //this stores all of the packets serialNumbers the were received in the last two seconds 
		
		for(PacketInfo info : this.packets)
		{	
			long timePassed = ((timeNow-info.getTimestamp())/1000);
			if(timePassed<=2)
			{
				packetsValue.add(i, info);//for the packages that were received in the past 2 sec , put them in the arr
				i++;
			}
		}
		
		for(i=0;i<packetsValue.size()-1;i++)
		{
			int curr=packetsValue.get(i+1).getPacketValue();
			int prev=packetsValue.get(i).getPacketValue();
			if(curr-prev > 1)
				lostPackets++;
		}
		
		int totalNumOfPacketsIn2Sec=packetsValue.size()+lostPackets;
		// return the percentage of the loss packages in two seconds
		return ((lostPackets*1000 )/(totalNumOfPacketsIn2Sec*10));//*100;
	}
	
	public int GetAverageLoss() 
	{
		int totalNumOfPackets = this.amountLost + this.packets.size() ;// the total num of packets is the ones that were recievd and the ones the were lost
		//fun3( totalNumOfPackets);
		
		int resInPercent=( this.amountLost*1000) / (totalNumOfPackets*10 ) ;//* 100;
		
		return (resInPercent) ;//return the lost percentage	
	
	}
	
	//testing values printing methods in functions

	private void fun1(int packetSerialNumber, int dif)
	{
		System.out.println("in fun 1");
		System.out.println("serNumber "+packetSerialNumber);
		System.out.println("latestNumber "+this.latestNumber);
		System.out.println("dif"+dif);
		System.out.println("lost amount "+amountLost);
		
	}
	
	private void fun3(int totalNumOfPackets)
	{
		System.out.println("in fun3");
		System.out.println("packetsSize "+this.packets.size());
		System.out.println("lost amount "+amountLost);
		System.out.println("total num of packets " + totalNumOfPackets);
	}
	

}
