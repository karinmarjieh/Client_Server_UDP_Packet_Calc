package interviews;

import java.time.Instant;

public class PacketInfo  implements Comparable
{
	private int packetValue ;
	private long timestamp ;
	
	public PacketInfo(int packetValue) 
	{
		setPacketValue(packetValue);
		setTimestamp();
	}


	@Override
	public int compareTo(Object p) {//TODO this function was made to use sort 
		
		int comparPacketValue=((PacketInfo)p).getPacketValue();

		 return this.getPacketValue()-comparPacketValue;
	}

    @Override
    public String toString() {
        return "[ packetValue=" + packetValue + ", timestamp=" + getTimestamp() +  "]";
    }

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp() {
		this.timestamp=Instant.now().toEpochMilli();
	}

	public int getPacketValue()
	{
		return this.packetValue;
	}
	
	public void setPacketValue(int packetValue2) {
		this.packetValue = packetValue2 ;
		
	}
}
