package basic.Javassit;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/18 13:21
 */

public class Emp {

        int name;
        int num;
        public Emp(){}

    public Emp(int name, int num) {
        this.name = name;
        this.num = num;
    }

    public int getName() {
        System.out.println("this is in getName");
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
