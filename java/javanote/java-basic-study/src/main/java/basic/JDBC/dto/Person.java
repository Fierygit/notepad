package basic.JDBC.dto;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/18 22:20
 */

public class Person {

    String username;
    String name;
    int age;
    String teleno;

    public Person() {

    }

    public Person(String username, String name, int age, String teleno) {
        this.username = username;
        this.name = name;
        this.age = age;
        this.teleno = teleno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTeleno() {
        return teleno;
    }

    public void setTeleno(String teleno) {
        this.teleno = teleno;
    }
}
