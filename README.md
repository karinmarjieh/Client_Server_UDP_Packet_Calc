# Client Server UDP Packet JAVA Calculator

I tried to implement a client server UDP Packet calculator .
Server Sends Packets to the Client through a datagram socket , 
The Client receives the packets and calculates the number of the lost packets . 

- The project was developed using JAVA technology .

## Classes 
Class | Description | 	File Name | 
--- | --- | --- |
[Main]() | opens a new connection between the client and the server | tester.java | 
[Server]() | sends messages to the client through Datagram socket packets | UdpUnicastServer.java |
[Client]() | receiving packets from the server and calling the calculator fuctions | UdpUnicastClient.java |
[Calculator]() | calculates the total and the last 2 sec lost packets in the middle of the connection  |  v1- UdpCalculator.java ,<br> v2- UDPPacketCalculator

## Main
**run this file to execute the program .** <br>
It created an object for each class using the classess constructors , both client and server are asking for a port to connect through so ive choosen a common port and sent it to them using the constructor , the client is asking for a calculator objet to calculate the loss packets . ExecutorService is helping the main function to run the client and server both at the same time as a multi threads runnig. 
<br> <br>
**how the program works**
1) client open its ears and listening to the server through the common port . <br>
2) server open the connection  through the same port and sends a messages .  <br>
3) client recieve the packets and for each packet it calculates the packets loss by calling the functions in its calculator object .
## Server  
The Server implements runnable so it ovverides the run() method , he tries to connect to the client through a DatagramSocket , he puts the message into a DatagramPacket and sends it to the client .
## Client
 The Server implements runnable so it ovverides the run() method , he opens a buffer to put the arrived message in it , he tries to connect to the server through a DatagramSocket in which he listens to the server and waiting for him to send packets of data ; the client receive a calculator object in its constructor and uses its functions when he receive a packet from the server to calculate times and packets loss in the middle . 
## Calculator  
we use this class wen we call its functions from the client . <br>
for each packet we want to know : 
1) The Serial Number of the Packet .
2) The Time that this packet arrived .
3) The Number of Packets that were lost in the middle (hasn't arrived) between this current packet and the packet before to calculate the precentage of all lost packets until this moment , and the precentage of lost packets in the past two seconds .<br>
<br> so ive choosen to save all arrived packets in an Array List called **packets** that has the **packet value** as the packet serial number and the **timestamp** of the packet arrival .<br>
In addition this class also has two variables : <br>
**latestNumber** as the latest packet serial number <br>
**amountLost** as the total number of lost packets <br>

#### Functions
Function Name | Input | Output | Description | Differnce between the versions
--- | --- | --- |--- | --- |
OnPacketReceived | Serial Number of the Arrived Packet | nothing | It recievs a Serial Number of the entered Packet , calculates the difference between it & the previous serial number and calculetes the number of the lost packets between the current and precious one and puts the packet with its info in the packets array. | v1 : calculated the difference and added it to the **amountLost** . <br> v2: checked if the arrived packet has a smaller serial number than the previous so its not lost anymore , it means that we need to remove it from the **amountLost** and also we need to put it before the previous packet in the **packets** arrayList so i tried to use the function Sort() from Collections . (arrayList from type PacketInfo implement Comparable in order to use the sort function so i still need to implement the compareTo() function in order to use it )
GetCurrentLoss | no input | the percentage of lost packets in the past 2 sec | for each packet in the **packets** array check if its recieved in the past 2 seconds , if yes then add it to a new array and in the end calculate the percentage of the lost packets only of the past two seconds | as same as the function above if i want to use the sort function i still need to do all of the Comparable implementations |
GetAverageLoss | no input | the percentage of all lost packets until now | numOfLostPackets/num of(found + lost ) packets | no difference between the versions 

#### Versions 
- In the **First Version** i thought that packets are recieved in sequential order of their serial number so ive done of the calculations according to it .
- In the **Second Version** i figured out that the packets might arrive not in the same order they meant to be , so i tried to think about a quick way to solve this problem by sorting the packets array  , and i am still thinking about some better and more efficient way to implement it . **this version still doen't work**
