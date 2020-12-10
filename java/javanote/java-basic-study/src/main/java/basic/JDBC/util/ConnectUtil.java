package basic.JDBC.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/22 15:23
 */

public class ConnectUtil {

    private Connection conn;

    /**
     * 在构造的时候初始化连接一次
     */
    public ConnectUtil() {

        Properties p = new Properties();
        try {
            InputStream inputStream = Util.class.getResourceAsStream("mysql.properties");
            p.load(inputStream);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("getMsgerror");
        }
        String JDBC_DRIVER = p.getProperty("mysql.driver");
        String DB_URL = "jdbc:mysql://" + p.getProperty("mysql.ip") + ":" + p.getProperty("mysql.port") + "/" + p.getProperty("mysql.database") + "?useUnicode=true&characterEncoding=utf8&useSSL=false";
        String USER = p.getProperty("mysql.user");
        String PASS = p.getProperty("mysql.pass");
        try {
            Class.forName(JDBC_DRIVER); // 加载类
            conn = DriverManager.getConnection(DB_URL, USER, PASS);// 连接
            System.out.println("construed connection......................");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("connect error!!");
        }
    }


    public Connection getConnect() {
        return conn;
    }


}
