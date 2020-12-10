package basic.Http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


class Test {
	public static String SendGet(String url) {
	    String result = "";
	    BufferedReader in = null;
	    try {
	        String urlNameString = url ;
	        URL realUrl = new URL(urlNameString);
	        // 打开和URL之间的连接
	        URLConnection connection = realUrl.openConnection();
	        // 设置通用的请求属性
	        connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
	        connection.setRequestProperty("connection", "Keep-Alive");
	        connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
	        connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
	        connection.setRequestProperty("Host", "statistics.pandadastudio.com");
	        connection.setRequestProperty("Referer", "http://statistics.pandadastudio.com/player/giftCodeView");
	        connection.setRequestProperty("user-agent",
	                "Opera/9.80 (Windows NT 6.0) Presto/2.12.388 Version/12.14");
	 
	        connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");      
	        // 建立实际的连接
	        connection.connect();   
	        // 定义 BufferedReader输入流来读取URL的响应
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")); // 获取输入流  
	        String line = null;  
	        StringBuilder sb = new StringBuilder();  
	        while ((line = br.readLine()) != null)  
	        {  
	            sb.append(line + "\n");  
	        }  
	        br.close();  
	        System.out.println(sb.toString());  
	        
	    } catch (Exception e) {
	        System.out.println("发送GET请求出现异常！" + e);
	        e.printStackTrace();
	    }

	    // 使用finally块来关闭输入流
	    finally {
	        try {
	            if (in != null) {
	                in.close();
	            }
	        } catch (Exception e2) {
	            e2.printStackTrace();
	        }
	    }
	    return result;
	}
	
	
	public static void main(String[] args) {	
		
		for(int i  = 0 ; i < 10; i++) {
			
			new Thread(Test.SendGet("http://statistics.pandadastudio.com/player/giftCode?uid=814897167&code=he")).start();
		}
		
		
	}
	
}


