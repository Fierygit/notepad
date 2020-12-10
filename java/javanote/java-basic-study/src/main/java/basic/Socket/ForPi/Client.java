package basic.Socket.ForPi;


import javafx.util.Pair;

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/7 16:13
 */

public class Client {

    private Queue<Pair<InputStream, String>> images = new LinkedList<>();
    private Integer count = 0;
    private Set<String> flag = new HashSet<>();


    String root = "C:\\Users\\Firefly\\Desktop\\buffer";

    public Client() {
        new Thread(new Read()).start();
    }


    private void start() throws Exception {
        System.out.println("start!!");
        while (true) {
            synchronized (images) {
                if (images.size() > 0) {
                    Pair<InputStream, String> image = images.remove();
                    send(image);
                }
            }


        }
    }


    private void send(Pair<InputStream, String> image) throws Exception {

//
//        FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\Firefly\\Desktop\\server\\" + count.toString() + ".jpg"));
//        byte[] temp = new byte[1024];
//
//        while (image.getKey().read(temp) != -1) {
//            fos.write(temp);
//        }
        Socket socket = null;
        OutputStream fos = null;

        try {
            socket = new Socket("192.168.43.127", 6666);
            fos = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int len = image.getKey().available();
        //System.out.println(Util.toBinaryString(len));
        // System.out.println(len);

        char[] start = {'f', 'i', 'r', 'e', 'f', 'l', 'y'};

        // System.out.println(start.length);


        for (char c : start) {
            fos.write((byte) c);
        }

        byte[] b = new byte[4];
        b[0] = (byte) (len & 0xff);
        b[1] = (byte) ((len >> 8) & 0xff);
        b[2] = (byte) ((len >> 16) & 0xff);
        b[3] = (byte) ((len >> 24) & 0xff);

        fos.write(b);
        System.out.println("sending-------");
        byte[] temp = new byte[1024];
        while (image.getKey().read(temp) != -1) {
            fos.write(temp);
        }

        
        fos.close();
        socket.close();
        // fos.flush();
        //
        //System.out.println("send");
        count++;
        /**
         * @author Firefly
         * 关闭对应的流
         */
        image.getKey().close();
        //


        //删除文件
        boolean del = new File(root + "\\" + image.getValue()).delete();
        flag.remove(image.getValue());
        System.out.println("send: " + count.toString() + "\tdel: " + del);


    }


    class Read implements Runnable {

        @Override
        public void run() {
            boolean sleep = true;
            while (true) {
                {
                    File path = new File(root);
                    String[] list = path.list();
                    if (list == null) continue;
                    for (String l : list) {
                        try {
                            if (!flag.contains(l)) {
                                FileInputStream fis = new FileInputStream(new File(root + "\\" + l));
                                synchronized (images) {
                                    flag.add(l);
                                    images.add(new Pair<>(fis, l));
                                }
                                System.out.println("\t\t\t\t\t\tqueue_size: " + images.size() + "\tadd: " + l);
                                sleep = false;
                                Thread.sleep(30);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        //里面已经休眠，外面就不用休眠了！！！！
                        if (sleep) Thread.sleep(30);

                        //缓冲区到了 100 张， 停止， 优化的话可以跳着来
                        if (images.size() > 100) break;


                    } catch (Exception e) {
                        System.out.println(e);
                    }

                }
            }
        }

    }


    public static void main(String[] args) throws Exception {

        Client client = new Client();
        client.start();


    }


}
