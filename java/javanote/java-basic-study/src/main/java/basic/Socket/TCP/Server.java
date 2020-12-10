package basic.Socket.TCP;
import java.io.*;
import java.net.*;

public class Server {
    //这里直接把异常抛出了
    public static void main(String[] args) throws Exception{
        //创建服务端对象，监听10020端口
        ServerSocket ss = new ServerSocket(8888);
        //用ServerSocket里面的accept方法获取客户端对象
        Socket s = ss.accept();
        //获取对方IP 并输出
        System.out.println(s.getInetAddress().getHostAddress()+"连接");

        BufferedReader bufIn = new BufferedReader(new InputStreamReader(s.getInputStream()));

        PrintWriter out = new PrintWriter(s.getOutputStream(),true);

        String str = null;
        while((str=bufIn.readLine())!=null) {
            System.out.println(str);
            //变成大写并返回
            out.println(str.toUpperCase());
        }

        ss.close();
        s.close();
        bufIn.close();
        out.close();
    }
}
