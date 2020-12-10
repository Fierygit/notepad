package basic.JDBC.dao;


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

public class UserDAO {

    private Connection conn;
    private PreparedStatement ps;
    private String sql;

    //使用的时候初始化连接
    public UserDAO(Connection connection) {
        conn = connection;
    }

    public void close() {
        Util.close(conn);
    }

    public List<User> getAll() {
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            sql = "select * from users;";
            ps = this.conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getString(1), rs.getString(2));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            System.out.println("get all error!!");
        }
        return null;
    }

    public boolean addUser(User user) { // 增加
        try {
            sql = "insert into users values(?,?);";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, user.getUsername());
            ps.setObject(2, user.getPass());
            int flag = ps.executeUpdate();
            if (flag > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("add usr error!!");
        }
        return false;
    }

    /*
     根据  user的name 查询是否 有这个人
     有 返回  true
     */
    public boolean queryByName(String username) {

        try {
            sql = "select count(1) from users where username = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, username);

            ResultSet flag = ps.executeQuery();
            // System.out.println(flag);
            if (flag.next()) {
                return flag.getInt(1) > 0;
            }
        } catch (Exception e) {
            System.out.println("query users error!!");
        }
        return false;
    }

    public List<User> queryByStartName(String startName) {
        try {
            sql = "select * from users where username like '" + startName + "%'";
            ps = conn.prepareStatement(sql);
            ResultSet flag = ps.executeQuery();
            // System.out.println(flag);
            ArrayList<User> users = new ArrayList<>();
            while (flag.next()) {
                User user = new User(flag.getString(1), flag.getString(2));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            System.out.println("queryByStartName error!!");
        }
        return null;
    }

    public boolean deleteByName(String username) {

        try {
            sql = "delete from users where username = '" + username + "'";
            ps = conn.prepareStatement(sql);
            return ps.execute();
        } catch (Exception e) {
            System.out.println("deleteByName users error!!");
        }
        return false;
    }


    /**
     * @author Firefly
     * 测试使用
     */
    public boolean deleteByStart(String startname) {
        try {
            sql = "select * from users where username like '" + startname + "%'";
            // System.out.println(sql);
            ps = conn.prepareStatement(sql);
            ResultSet flag = ps.executeQuery();
            // System.out.println(sql);
            //System.out.println(flag);
            PersonDAO personDAO = new PersonDAO(conn);
            while (flag.next()) {
                //先要删除外检
                personDAO.deleteByPersonname(flag.getString(1));
                String sql1 = "delete from users where username = '" + flag.getString(1) + "'";
                //   System.out.println(sql1);
                ps = conn.prepareStatement(sql1);
                ps.execute();
            }
            personDAO.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("del users error!!");
        }
        return false;
    }


}
