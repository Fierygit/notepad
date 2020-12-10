package basic.JDBC.dto;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/18 21:53
 * data transite object
 */

public class User {

    private String username;
    private String pass;

    public User() {

    }

    public User(String username, String pass) {
        this.username = username;
        this.pass = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
