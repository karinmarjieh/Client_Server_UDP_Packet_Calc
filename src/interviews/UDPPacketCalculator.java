package interviews;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

public class UDPPacketCalculator
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
		
		if(packetSerialNumber != this.latestNumber+1) // if the last arrived packets are not sequential 
		{ 
			if(dif>0)//it means that a packet or more were lost in the middle // as same as if ((packetSerialNumber-this.latestNumber)>1) or this.latestNumber + 1 != packetSerialNumber
				this.amountLost += (dif-1); // calculate the number of lost packets in the middle 
			
			if(dif<0)//if the packet that arrived now has a smaller serial number 
				this.amountLost--;//so its not lost anymore
		}
		
		this.packets.add(new PacketInfo(packetSerialNumber));//add the received packet to the array 
		this.latestNumber = packetSerialNumber; //let latest number be the serial number of the latest packet received 
	
		 Collections.sort(packets);//sort by serialnumber() ///packets.sort();/////TODO implement comparable in packet Info
		
		System.out.println("in fun1");
		System.out.println("serNumber "+packetSerialNumber);
		System.out.println("latestNumber "+this.latestNumber);
		System.out.println("dif"+dif);
		System.out.println("lost amount "+amountLost);
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

		
//		int packetsValue [] = null; //this stores all of the packets serialNumbers the were received in the last two seconds 
//		
//		for(PacketInfo info : this.packets)
//		{	
//			long timePassed = ((timeNow-info.timestamp)/1000);
//			if(timePassed<=2)
//			{
//				packetsValue[i]=info.packetValue;//for the packages that were received in the past 2 sec , put them in the arr
//				i++;
//			}
//		}
//		
//		for(i=0;i<packetsValue.length-1;i++)
//		{
//			if(packetsValue[i+1]-packetsValue[i] > 1)
//				lostPackets++;
//		}
		
		ArrayList<PacketInfo> packetsValue  = new ArrayList<>(); //this stores all of the packets serialNumbers the were received in the last two seconds 
		
		for(PacketInfo info : this.packets)
		{	
			long timePassed = ((timeNow-info.timestamp)/1000);
			if(timePassed<=2)
			{
				packetsValue.add(i, info);//for the packages that were received in the past 2 sec , put them in the arr
				i++;
			}
		}
		
		Collections.sort(packetsValue);//packetsValue.sort();/////TODO implement comparable in packet Info
		
		for(i=0;i<packetsValue.size()-1;i++)
		{
			int curr=packetsValue.get(i+1).packetValue;
			int prev=packetsValue.get(i).packetValue;
			if(curr-prev > 1)
				lostPackets++;
		}
//		
		int totalNumOfPacketsIn2Sec=packetsValue.size()+lostPackets;
		// return the percentage of the loss packages in two seconds
		return ((lostPackets*1000 )/(totalNumOfPacketsIn2Sec*10));//*100;
	}
	
	public int GetAverageLoss() 
	{
		/*int accepted = this.latestNumber - this.amountLost; //calculating the number of accepted packages
		return (accepted / this.latestNumber) * 100;//percentage of recieved packets */
		
		int totalNumOfPackets = this.amountLost + this.packets.size() ;// the total num of packets is the ones that were recievd and the ones the were lost
		
		System.out.println("in fun3");
		System.out.println("this.packets.size()  "+this.packets.size());
		System.out.println("lost amount "+amountLost);
		System.out.println("total num of packets " + totalNumOfPackets);
		
		int resInPercent=( this.amountLost*1000) / (totalNumOfPackets*10 ) ;//* 100;
		
		return (resInPercent) ;//return the lost percentage
		
		
	
	}
	
	private static class PacketInfo // TODO : implements Comparable
	{
		private int packetValue ;
		private long timestamp ;
		
		private PacketInfo(int packetValue) 
		{
			this.packetValue = packetValue ;
			this.timestamp = Instant.now().toEpochMilli() ;
		}

		/*@Override
		public int compareTo(PacketInfo p) {//TODO this function was made to use sort 
			if(this.packetValue>p.packetValue)
				//switch betweenthem
			return 0;
		}*/
		
	}
	
	public UDPPacketCalculator() {
		// TODO Auto-generated constructor stub
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
