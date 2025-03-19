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