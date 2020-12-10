package basic.JDBC.util;





import basic.JDBC.dto.Person;
import basic.JDBC.dto.User;

import java.sql.Connection;

import java.util.List;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/18 22:09
 */

public class Util {

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("close error");
        }

    }

    public static void printUsers(List<User> all) {
        int len = 30;
        System.out.println(formatStr("username", len) + formatStr("pass", len));
        for (User u : all) {
            System.out.println(formatStr(u.getUsername(), len) + formatStr(u.getPass(), len));
        }
    }

    public static void printPerson(List<Person> all) {
        int len = 30;
        System.out.println(formatStr("username", len) + formatStr("name", len) + formatStr("age", len) + formatStr("teleno", len));
        for (Person u : all) {
            System.out.println(formatStr(u.getUsername(), len) + formatStr(u.getName(), len) + formatStr(Integer.toString(u.getAge()), len) + formatStr(u.getTeleno(), len));
        }
    }

    public static String formatStr(String str, int length) {
        if (str == null) {
            str = "";
        }
        int strLen = str.getBytes().length;
        if (strLen == length) {
            return str;
        } else if (strLen < length) {
            int temp = length - strLen;
            String tem = "";
            for (int i = 0; i < temp; i++) {
                tem = tem + " ";
            }
            return str + tem;
        } else {
            return str.substring(0, length);
        }
    }

}
