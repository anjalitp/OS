import java.util.*;
public class Distributed{
 public static void main(String args[])
	{
		ArrayList<Site> process=new ArrayList<Site>();
		final Site[] pc = new Site[5];
		for(int i=0;i<5;++i)
		{
			pc[i]=new Site(process,i);
			process.add(pc[i]);
			//pc[i].pid=i;
		}
		
		System.out.println("Enter the Number of process requesting  the Critical section( Requests :1 to 5)");
		Scanner s=new Scanner(System.in);
		int ch=s.nextInt();
		int seq[]={0,0,0,0,0};
		System.out.println("Enter the sequence of the Proceess (Format 1 2 3 4 0): P0->0,P1->1,P2->2,P3->3,P4->4\n");
		for(int i=0;i<ch;++i)
		{
		seq[i]=s.nextInt();
		}
		
		for(int i=0;i<ch;++i)
		{
		pc[seq[i]].assignTimestamp(i+1);
		
		}
		for(int i=0;i<ch;++i)
		{
		pc[seq[i]].request(process);
		
		}
	
		
	
		
	}

}


class Site{
private int pid;
private int timestamp;
private boolean cs=false;
private boolean requested=false;
private boolean receive_request=false;
private int per[]={0,0,0,0,0};
private int req_pid;
private int cs_executed=0;
private Queue<Integer> requester=new LinkedList<>();
private Queue<Integer> timestampQ=new LinkedList<>();
private Queue<Integer> timestampR=new LinkedList<>();
Site(ArrayList<Site> process,int i)
{

listen(process);
timestamp=0;
pid=i;

}

void assignTimestamp(int i)
{
//if(timestamp==0)
timestampQ.add(i);

}

void Send(int i,int time)
{
 receive_request=true;
 requester.add(i);
 timestampR.add(time);
	
}
void givePermission(int pid)
{

per[pid]=1;
}

public static void CS()
{

System.out.println("\n\n******Executing Critical Section *******\n\n");
}

void listen(ArrayList<Site> process)
{

     		 
	 Thread t0 = new Thread(new Runnable()
        {
           // @Override
           synchronized  public void run()
            {
                while(true)
		{
		try
                {
		
		while(requester.isEmpty()){Thread.sleep(10);}

		//while(cs==true){Thread.sleep(10);}
		
				
			while(!requester.isEmpty()  && requested ==true)
			{
				while(cs==true){
				Thread.sleep(5);
				//System.out.println("Executing critical section"+pid);
				}
			
				req_pid=requester.peek();
				int t=timestampR.peek();
				if(t<timestamp )
					{
					int s=requester.remove();
					int time=timestampR.remove();

					System.out.println("\nProcess" + pid +"  :Granting permission to "+ s +" after timestamp check");
					process.get(s).givePermission(pid);
					
					if(requester.isEmpty())
					{
						//System.out.println("Queue is empty " +pid);
							receive_request=false;
							//Thread.sleep(10);
					
					}
			
					}
				else
					{
						Thread.sleep(10+2*timestamp);
					}
			
			}
			
			
			while(!requester.isEmpty()  && requested ==false)		
			{
			while(cs==true){
				Thread.sleep(5);
				
				}

			int s=requester.remove();
			int t=timestampR.remove();
			System.out.println("\nProcess" + pid +" : Granting permission to "+ s );
			process.get(s).givePermission(pid);
			//Thread.sleep(5);
			if(requester.isEmpty())
			{
				//System.out.println("Queue is empty " +pid);
					receive_request=false;
					Thread.sleep(10);
			}
		
			//Thread.sleep(10);
			}
	
	}
                catch(Exception e)
                {
                    e.printStackTrace();
                }
		}
            }
        });
	t0.start();
}

void request(ArrayList<Site> process) {
	

		//public int myport;
		 
	 Thread t1 = new Thread(new Runnable()
        {
           // @Override
            synchronized public void run()
            {
		
                try

                {
			//while(requested==true);
			
			int time=timestampQ.remove();
			
			Thread.sleep(10+10*time);
			if(requested==false)
			requested=true;
			else
			{
			while(requested==true || cs==true){Thread.sleep(10+5*time);}
			//while(requested==true || cs==true){Thread.sleep(10+5*time);}		
			requested=true;
			timestamp=time;
			//Thread.sleep(10);
			
				
			}
			
		
			System.out.println("\nRequesting for "+pid+ "starts->");
		  //	 is_req=true;
			for(int i=0;i<5;++i)
				
				{	//if(pid=i)
					
					if(i!=pid)
					{
					process.get(i).Send(pid,timestamp);
					System.out.println("\nProcess" + pid +" :Requesting CS to "+i);
					}
					
		
				}
				per[pid]=1;
				//Thread.sleep(10);
				while(per[0]+per[1]+ per[2]+per[3]+per[4]!=5)
				{
					Thread.sleep(10);
		
				}
		cs=true;
		//Thread.sleep(10);
		System.out.println("\n\nProcess" + pid +" :******Entering Critical Section ********\n\n");
		CS();
		++cs_executed;
		Thread.sleep(1000);
		System.out.println("\n\nProcess" + pid +" :******Releasing Critical Section*********\n\n");		
		
		requested=false;
		//Thread.sleep(10);
		per[0]=0;
		per[1]=0;
		per[2]=0;
		per[3]=0;
		per[4]=0;
		//Thread.sleep(10);
		cs=false;
		

		}
	
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            
	}
        });
	t1.start();
}



}
