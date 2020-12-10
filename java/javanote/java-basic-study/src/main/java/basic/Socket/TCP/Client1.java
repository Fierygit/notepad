package basic.Socket.TCP;

import java.io.*;
import java.net.Socket;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/2/11 15:56
 */

public class Client1 {
    private Socket scoket;
    private String ip;
    private int port;
    private OutputStream os;
    private InputStream is;
    static char[] startInfo = {'f', 'i', 'r', 'e', 'f', 'l', 'y'};

    private void initCon() {
        try {
            System.out.println("try to connect！");
            scoket = new Socket(this.ip, port);
            this.os = this.scoket.getOutputStream();
            this.is = this.scoket.getInputStream();
            System.out.println("connect success!");
        } catch (Exception e) {
            System.out.println("Exception has happend, it's info is:");
            System.out.println(e.toString() + "\n");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            initCon();
        }
    }


    public void starat(String ip, int port) {
        this.ip = ip;
        this.port = port;
        initCon();
        traverse();
    }

    private void traverse() {

        while (true) {
            try {
                FileInputStream image = getImg();
                byte[] get = new byte[1];
                //这里会阻塞接受吗？
                boolean isValue = false;
                int cnt = 0;
                while (!isValue) {
                    if (cnt == 15) {
                        this.scoket.close();
                        initCon();
                        break;
                    }
                    System.out.println(this.scoket.isClosed()+" wait for " + this.scoket.getRemoteSocketAddress());
                    while (this.is.read(get) != -1) {
                        System.out.println("receive info: " + (char) get[0] + "");
                        if (get[0] == 'a') {
                            isValue = true;
                            break;
                        }
                    }
                    Thread.sleep(200);
                    cnt++;
                }

                for (char c : startInfo) this.os.write((byte) c);
                int len = image.available();
                byte[] b = new byte[4];
                b[0] = (byte) (len & 0xff);
                b[1] = (byte) ((len >> 8) & 0xff);
                b[2] = (byte) ((len >> 16) & 0xff);
                b[3] = (byte) ((len >> 24) & 0xff);
                os.write(b);
                byte[] temp = new byte[1024];
                while (image.read(temp) != -1) {
                    os.write(temp);
                }
                os.flush();
                System.out.println("send over!\n");
            } catch (Exception e) {
                System.out.println("Exception happend while traverse");
                System.out.println(e);
                initCon();
            }
        }
    }

    private FileInputStream getImg() {
        String root = "C:\\Users\\Firefly\\Desktop\\test.jpg";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(root);
        } catch (Exception e) {
            System.out.println("get img error");
            System.out.println(e);
        }
        System.out.println("send img " + root);
        return fis;
    }


    public static void main(String[] args) {
        new Client1().starat("119.3.239.133", 8888);
    }

}
