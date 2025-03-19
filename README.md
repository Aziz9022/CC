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
      dout.writeUTF(x + " is Prime");
      }
      else
      {
      otc.writeUTF(x + " is not Prime");
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
