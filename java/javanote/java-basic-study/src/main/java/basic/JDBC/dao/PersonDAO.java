package basic.JDBC.dao;



import basic.JDBC.dto.Person;
import basic.JDBC.dto.User;
import basic.JDBC.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/18 21:44
 */

public class PersonDAO {

    private Connection conn;
    private PreparedStatement ps;
    private String sql;

    public PersonDAO(Connection connection) {
        conn = connection;
    }

    public void close() {
        try {
            Util.close(conn);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public List<Person> getAll() {
        ResultSet rs;
        List<Person> persons = new ArrayList<>();
        try {
            sql = "select * from person";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Person person = new Person(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4));
                persons.add(person);
            }
            return persons;
        } catch (Exception e) {
            System.out.println("get all error!!");
        }
        return null;
    }


    public boolean addOnePerson(Person person) {
        try {
            sql = "insert into person values(?,?,?,?);";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, person.getUsername());
            ps.setObject(2, person.getName());
            ps.setObject(3, person.getAge());
            ps.setObject(4, person.getTeleno());
            int flag = ps.executeUpdate();
            if (flag > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("query person error!!");
        }
        return false;
    }



    /**
     * @author Firefly
     * 这里传name就行了， 测试。。。。
     */
    public boolean queryByName(Person person) {
        try {
            sql = "select name from person where username = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, person.getName());
            ResultSet flag = ps.executeQuery();
            //  System.out.println(flag);
            if (flag.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("query person error!!");
        }
        return false;
    }



    /*
    更新
    首先 查找有没有
     */

    public boolean updateOne(Person person) {
        try {
            sql = "update person set username = ? , age = ?, teleno = ? where name = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, person.getUsername());
            ps.setObject(2, person.getAge());
            ps.setObject(3, person.getTeleno());
            ps.setObject(4, person.getName());
            int flag = ps.executeUpdate();
            if (flag >= 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("add person error!!");
        }
        return false;
    }

    public boolean deleteByPersonname(String name) {
        try {
            sql = "delete from person where username = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, name);
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("del person error!!");
        }
        return false;
    }

    /**
     * @author Firefly
     * 测试使用
     */
    public boolean addPerson(Person person) { // 增加
        PreparedStatement ps = null;
        UserDAO userDAO = new UserDAO(conn);
        //如果没有，先添加
        if (!userDAO.queryByName(person.getUsername())) {
            User user = new User(person.getUsername(), "8888888");
            userDAO.addUser(user);
        }
        userDAO.close();

        if (queryByName(person)) {
            updateOne(person);
            return true;
        } else {
            addOnePerson(person);
        }
        return false;
    }

}
