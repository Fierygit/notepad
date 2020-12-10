package basic.JDBC.service;



import basic.JDBC.dao.PersonDAO;
import basic.JDBC.dao.UserDAO;
import basic.JDBC.dto.User;

import java.sql.Connection;
import java.util.List;


/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/22 8:36
 */

public class UserService {
    Connection connection;
    private UserDAO userDAO;
    private PersonDAO personDAO;

    public UserService(Connection connection) {
        this.connection = connection;
        userDAO = new UserDAO(connection);
        personDAO = new PersonDAO(connection);
    }

    public List<User> getAll() {
        return userDAO.getAll();
    }

    public boolean addUser(User user) {
        return userDAO.addUser(user);
    }


    public void deleteByStart(String startname) {

        List<User> users = userDAO.queryByStartName(startname);
        for (User user : users) {
            //首先删除外键
            personDAO.deleteByPersonname(user.getUsername());
            userDAO.deleteByName(user.getUsername());
        }

    }

    public void close() {
        userDAO.close();
        personDAO.close();
    }


}
