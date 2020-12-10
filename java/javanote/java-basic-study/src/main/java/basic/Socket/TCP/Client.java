package basic.Socket.TCP;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/2/10 21:25
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        //创建Socket服务 字符输出流 读取流
        Socket s = null;
        PrintWriter out = null;
        BufferedReader bufIn = null;
        try {
            //指定IP地址和端口
            s = new Socket("119.3.239.133", 8888);
            //指定输出流为s的网络输出流，并可以自动刷新
            out = new PrintWriter(s.getOutputStream(),true);
            //输入流为s的网络输入流
            bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        Scanner input = new Scanner(System.in);

        String line = null;

        while(!("over".equals(line))) {
            line = input.nextLine();
            out.println(line);
            String str = null;
            try {
                str = bufIn.readLine();
            } catch (Exception e) {
                // TODO: handle exception
            }
            System.out.println(str);
        }

        try {
            bufIn.close();
            s.close();
            out.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}