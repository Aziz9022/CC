Practical No. 4 AIM: RMI based application program to display current date and time.
1.
        ServerDate.java

                import java.rmi.*;
                import java.rmi.server.*;
                import java.util.*;
                public class ServerDate extends UnicastRemoteObject implements InterDate
                {
                public ServerDate() throws Exception
                {
                }
                public String display() throws Exception
                {
                String str = "";
                Date d = new Date();
                str = d.toString();
                return str;
                }
                public static void main(String args[]) throws Exception
                {
                ServerDate s1 = new ServerDate();
                Naming.bind("DS", s1);
                System.out.println("Object registered.....");
                }
                }

                
2.
ClientDate.java

        import java.rmi.*;
        import java.io.*;
        public class ClientDate
        {
        public static void main(String args[]) throws Exception
        {
        String s1;
        InterDate h1 = (InterDate)Naming.lookup("DS");
        s1 = h1.display();
        System.out.println(s1);
        }
        }
  
4.
InterDate.java

      import java.rmi.*;
      public interface InterDate extends Remote
      {
      public String display() throws Exception;
      } explain this code to me so that i can write it in tomorrow exam


practical 3
BraodcastClient.java

    import java.io.*;
    import java.net.*;
    
    public class BroadcastClient {
        public static final int PORT = 1234; // Same port as server

    public static void main(String[] args) {
        MulticastSocket socket = null;
        try {
            InetAddress address = InetAddress.getByName("239.1.2.3"); // Multicast address
            socket = new MulticastSocket(PORT);
            socket.joinGroup(address); // Join the multicast group

            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // Receive the packet
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received: " + received);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.leaveGroup(InetAddress.getByName("239.1.2.3"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket.close();
            }
        }
    }
    }


// BroadcastClient.java

            import java.io.*;
            import java.net.*;
            
            public class BroadcastServer {
                public static final int PORT = 1234; // Port to send the broadcast

    public static void main(String[] args) {
        MulticastSocket socket = null;
        try {
            InetAddress address = InetAddress.getByName("239.1.2.3"); // Multicast address
            socket = new MulticastSocket();

            byte[] data;
            while (true) {
                Thread.sleep(10000); // Sleep for 10 seconds
                System.out.println("Sending");
                String str = "Omkar is calling......";
                data = str.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, address, PORT);
                socket.send(packet); // Send the packet
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
    }
practical 2a

Practical No. 2A
Write a program for implementing Client Server communication model using UDP.
Program which finds entered number is even or odd
1. udpClientEO.java

        
        import java.io.*;
        import java.net.*;
        public class udpClientEO
        {
        public static void main(String args[])
        {
        try
        {
        DatagramSocket ds = new DatagramSocket(1000);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a number : ");
        String num = br.readLine();
        byte b[] = new byte[1024];
        b = num.getBytes();
        DatagramPacket dp = new DatagramPacket(b, b.length,InetAddress.getLocalHost(), 2000);
        ds.send(dp);
        byte b1[] = new byte[1024];
        DatagramPacket dp1 = new DatagramPacket(b1, b1.length);
        ds.receive(dp1);
        String str = new String(dp1.getData(), 0, dp1.getLength());
        System.out.println(str);
        }
        catch(Exception e)
        {
        e.printStackTrace();
        }
        }
        }


3. udpServerEO.java

   
        import java.io.*;
        import java.net.*;
        public class udpServerEO
        {
        public static void main(String args[])
        {
        try
        {
        DatagramSocket ds = new DatagramSocket(2000);
        byte b[] = new byte[1024]; // 1 mb of variable storage
        DatagramPacket dp = new DatagramPacket(b, b.length);
        ds.receive(dp);
        String str = new String(dp.getData(), 0, dp.getLength());
        System.out.println(str);
        int a = Integer.parseInt(str);
        String s = new String();
        if (a % 2 == 0)
        s = "Number is even";
        else
        s = "Number is odd";
        byte b1[] = new byte[1024];
        b1 = s.getBytes();
        DatagramPacket dp1 = new DatagramPacket(b1, b1.length, InetAddress.getLocalHost(), 1000);
        ds.send(dp1);
        }
        catch(Exception e)
        {
        e.printStackTrace(); }}}
        
        
practical 2b


Practical No. 2 B
Write a program for implementing Client Server communication model using UDP.
A program to implement simple calculator operations like addition, subtraction, multiplication and
division.
RPCServer.java
      
      import java.util.*;
      import java.net.*;
      class RPCServer
      {
      DatagramSocket ds;
      DatagramPacket dp;
      String str, methodName, result;
      int val1, val2;
      RPCServer()
      {
      try
      {
      ds=new DatagramSocket(1200);
      byte b[]=new byte[4096];
      while(true)
      {
      dp=new DatagramPacket(b,b.length);
      ds.receive(dp);
      str=new String(dp.getData(),0,dp.getLength());
      if(str.equalsIgnoreCase("q"))
      {
      System.exit(1);
      }
      else
      {
      StringTokenizer st = new StringTokenizer(str," ");
      int i=0;
      while(st.hasMoreTokens())
      {
      String token=st.nextToken();
      methodName = token;
      val1 = Integer.parseInt(st.nextToken());
      val2 = Integer.parseInt(st.nextToken());
      }
      }
      System.out.println(str);
      InetAddress ia = InetAddress.getLocalHost();
      if(methodName.equalsIgnoreCase("add"))
      {
      result= "" + add(val1,val2);
      }
      else if(methodName.equalsIgnoreCase("sub"))
      {
      result= "" + sub(val1,val2);
      }
      else if(methodName.equalsIgnoreCase("mul"))
      {
      result= "" + mul(val1,val2);
      }
      else if(methodName.equalsIgnoreCase("div"))
      {
      result= "" + div(val1,val2);
      }
      byte b1[]=result.getBytes();
      DatagramSocket ds1 = new DatagramSocket();
      DatagramPacket dp1 = new DatagramPacket(b1,b1.length,InetAddress.getLocalHost(), 1300);
      System.out.println("result : "+result+"\n");
      ds1.send(dp1);
      }
      }
      catch (Exception e)
      {
      e.printStackTrace();
      }
      }
      public int add(int val1, int val2)
      {
      return val1+val2;
      }
      public int sub(int val3, int val4)
      {
      return val3-val4;
      }
      public int mul(int val3, int val4)
      {
      return val3*val4;
      }
      public int div(int val3, int val4)
      {
      return val3/val4;
      }
      public static void main(String[] args)
      {
      new RPCServer();
      }
      }

RPCClient.java

      
      import java.io.*;
      import java.net.*;
      class RPCClient
      {
      RPCClient()
      {
      try
      {
      InetAddress ia = InetAddress.getLocalHost();
      DatagramSocket ds = new DatagramSocket();
      DatagramSocket ds1 = new DatagramSocket(1300);
      System.out.println("\nRPC Client\n");
      System.out.println("Enter method name and parameter like add 3 4\n");
      while (true)
      {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String str = br.readLine();
      byte b[] = str.getBytes();
      DatagramPacket dp = new DatagramPacket(b,b.length,ia,1200);
      ds.send(dp);
      dp = new DatagramPacket(b,b.length);
      ds1.receive(dp);
      String s = new String(dp.getData(),0,dp.getLength());
      System.out.println("\nResult = " + s + "\n");
      }
      }
      catch (Exception e)
      {
      e.printStackTrace();
      }
      }
      public static void main(String[] args)
      {
      new RPCClient();
      }
      }
      
Practical 1A: TCP server-client prime number

ServerPrime.java

      import java.net.*;
      import java.io.*;
      class ServerPrime
      {
      public static void main(String args[])
      {
      try
      {
      ServerSocket ss = new ServerSocket(8001);
      System.out.println("Server Started...............");
      Socket s = ss.accept();
      DataInputStream in = new DataInputStream(s.getInputStream()); int x= in.readInt();
      DataOutputStream dout = new DataOutputStream(s.getOutputStream());
      int y = x/2;
      if(x ==1 || x ==2 || x ==3)
      {
      dout.writeUTF(x + "is Prime");
      System.exit(0);
      }
      for(int i=2; i<=y; i++)
      {
      if(x%i != 0)
      {
      dout.writeUTF(x + " is not Prime");
      }
      else
      {
      otc.writeUTF(x + " is  Prime");
      }
      }
      }
      catch(Exception e)
      {
      System.out.println(e.toString());
      }
      }
      }

ClientPrime.java
      
      import java.net.*;
      import java.io.*;
      class ClientPrime
      {
      public static void main(String args[])
      {
      try
      {
      Socket cs = new Socket("LocalHost",8001); BufferedReader infu = new BufferedReader(new
      InputStreamReader(System.in));
      System.out.println("Enter a number : ");
      int a = Integer.parseInt(infu.readLine());
      DataOutputStream out = new
      DataOutputStream(cs.getOutputStream());
      out.writeInt(a);
      DataInputStream in = new DataInputStream(cs.getInputStream()); System.out.println(in.readUTF()); cs.close();
      }
      catch(Exception e)
      {
      System.out.println(e.toString());
      }
      }
      }
      
      


Practical 1B : Server-Client TCP based on chat server

ChatServer.java

      import java.net.*;
      import java.io.*;
      class ChatServer
      {
      public static void main(String args[])
      {
      try
      {
      ServerSocket ss = new ServerSocket(8000);
      System.out.println("Waiting for client to connect..");
      Socket s = ss.accept();
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      DataOutputStream out = new DataOutputStream(s.getOutputStream());
      DataInputStream in = new DataInputStream(s.getInputStream());
      String receive, send;
      while((receive = in.readLine()) != null)
      {
      if(receive.equals("STOP"))
      break;
      System.out.println("Client Says : "+receive);
      System.out.print("Server Says : ");
      send = br.readLine();
      out.writeBytes(send+"\n");
      }
      br.close();
      in.close();
      out.close();
      s.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
      }
      }

ChatClient.java
      
      import java.net.*;
      import java.io.*;
      class ChatClient
      {
      public static void main(String args[])
      {
      try
      {
      Socket s = new Socket("Localhost",8000);
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      DataOutputStream out = new DataOutputStream(s.getOutputStream());
      DataInputStream in = new DataInputStream(s.getInputStream());
      String msg;
      System.out.println("To stop chatting with server type STOP");
      System.out.print("Client Says: ");
      while((msg = br.readLine()) != null)
      {
      out.writeBytes(msg+"\n");
      if(msg.equals("STOP"))
      break;
      System.out.println("Server Says : "+in.readLine());
      System.out.print("Client Says : ");
      }
      br.close();
      in.close();
      out.close();
      s.close();
      }
      catch(Exception e)
      {
      e.printStackTrace();
      }
      }
      }



practical 5-9


5 Implement xen virtualization and manage with xen centre
1.	Click on new virtual machine and then click on next button
2.	Click on next
3.	Select the disc image file and click on next
4.	Select the Linux option and click disc
5.	Set the 50 gb disk size
6.	Select the iso image file
7.	After selecting the iso image file power on the virtual machine
8.	Click on ok
9.	Accept the license
10.	Click on ok
11.	Click on no
12.	Click on ok 
13.	Click on ok
14.	Now set the password and click on ok
15.	Click on ok
16.	Select Asia>>Kolkata
17.	Click on ok
18.	Click on install xen server
19.	Now open xencenter software and then enter the username and password

    
6 Implement virtualization using VMware ESXI SERVER and managing with vcenter
1.	Click on new virtual machine and then click on next button and select the disc image file 
2.	Press the continue
3.	Accept the license Agreement
4.	Select all the default option which one is recommended by the system
5.	Enter the password
6.	Reboot the system
7.	Install the v sphere client. click on next
8.	Enter the username and click on next
9.	Click on install
10.	Enter the IP address, username n password then login
 
7 Implement server cluster with windows server
1.	Click on new virtual machine and then click on next button and select the disc image file 
2.	Click on install now
3.	Click on second option and click next
4.	Click on custom option
5.	Click on next
6.	Enter username n password
7.	Enter the system password
8.	Select the yes option
9.	Click on add roles and features
10.	Click on next
11.	Add the roles
12.	Click on next
13.	Write domain name
14.	Enter password
    
8 Implement virtualization
1.	Because the VMware workstation installer doesn’t allow running on a hyper v virtual machine, we must first uninstall any VMware software that may already be installed on the PC. once VMware has been removed, we can move onto the next step, which is to go to the control panel and select uninstall a program
2.	Turn windows features on or off by clicking on it. the hyper v option in windows features check is now available
3.	After restart search for Hyper-V manager in search box and click on that
4.	We need to setup virtual switch before we can create a virtual machine. Select the option for virtual switch manager
5.	After choosing external as the connection type, click on “create virtual switch”. Install the windows XP.iso file and create a new virtual switch and virtual machine will start


 
9 Implement opennebula
1.	Install oracle vm virtual box 7.0.10, click on next> click on yes> click on install>click on finish
2.	Click on machine and then new
3.	Make a folder and give the destination
4.	Click on add
5.	Select the opennebula 4.14.2-sandbox
6.	Click on choose
7.	Click on next
8.	Click on next to power on

