# OS
Distributed Scheduling Algorithm

The Scheduling Algorithm takes care of mutliple processes trying to execute the same critical section(CS) ensuring mutual exclusion.
The algorithm works as follows:
Whenever a Process needs to execute the CS , it sends a requests to all other Processes for permission along with the timestamp.
The Process receiving the requests, gives the permission if and only if
It is not currently executing the CS.Otherwise ,the permission will be given once it comes out of the crtical section.The permission is given in the order in which the requests are received.
First Come First Serve logic is followed here. 
A process can enter the CS if and only if it receives a go ahead from every other Processes.This ensures distributed decision making.



Steps to Run 

 1. Download the Distributed.java file
 
 2. Run using command line :
 
 javac Distributed.java
 
 java Distributed
 

