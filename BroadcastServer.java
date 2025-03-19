// BroadcastServer.java
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

// BroadcastClient.java
