package basic.JDBC.main;


import basic.JDBC.dto.Person;
import basic.JDBC.dto.User;
import basic.JDBC.service.PersonService;
import basic.JDBC.service.UserService;
import basic.JDBC.util.ConnectUtil;
import basic.JDBC.util.Util;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/18 22:20
 */


public class Test {

    public static void main(String[] args) {

        System.out.println("start!!!!!!!!!!!!!!!!!");
        ConnectUtil connectUtil = new ConnectUtil();
        System.out.println("connecting.....................");

        PersonService personService = new PersonService(connectUtil.getConnect());
        UserService userService = new UserService(connectUtil.getConnect());


        System.out.println("table users");
        Util.printUsers(userService.getAll());
        System.out.println("\ntable person");
        Util.printPerson(personService.getAll());

        User user1 = new User("ly","123456");
        User user2 = new User("liming","345678");
        User user3 = new User("Test","11111");
        User user4 = new User("test1","12345");
        System.out.println("\nadd four user!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.addUser(user4);

        System.out.println("table users");
        Util.printUsers(userService.getAll());


        Person person1 = new Person("ly","雷力",0,null);
        Person person2 = new Person("liming","李明",25,null);
        Person person3 = new Person("Test","20",0,"13388449933");

        System.out.println("\nadd three person!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        personService.addPerson(person1);
        personService.addPerson(person2);
        personService.addPerson(person3);

        System.out.println("\ntable person");
        Util.printPerson(personService.getAll());


        System.out.println("\nupdate person!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        Person person21 = new Person("ly","王五",0,null);
        Person person22 = new Person("test2","测试用户2",0,null);
        Person person23 = new Person("test1","测试用户1",33,null);
        Person person24 = new Person("Test","张三",23,"18877009966");
        Person person25 = new Person("admin","admin",0,null);

        personService.addPerson(person21);
        personService.addPerson(person22);
        personService.addPerson(person23);
        personService.addPerson(person24);
        personService.addPerson(person25);


        System.out.println("\ntable person");
        Util.printPerson(personService.getAll());

        System.out.println("\ndelete person!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


        userService.deleteByStart("Test");


        System.out.println("table users");
        Util.printUsers(userService.getAll());
        System.out.println("\ntable person");
        Util.printPerson(personService.getAll());

        userService.close();
        personService.close();
    }
}
