package basic.Socket.UDP;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/4 23:47
 */


import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) throws Exception {
        DatagramSocket datagramSocket = new DatagramSocket(6666);
        while (true) {
            DatagramPacket packet = new DatagramPacket(new byte[512], 512);
            datagramSocket.receive(packet);
            String msg = new String(packet.getData(), 0, packet.getLength());
            System.out.println(packet.getAddress() + "/" + packet.getPort() + ":" + msg);
            packet.setData("I am server!!!".getBytes());
            datagramSocket.send(packet);
        }
    }
}