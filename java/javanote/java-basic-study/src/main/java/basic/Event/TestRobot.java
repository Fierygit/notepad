package basic.Event;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/21 9:10
 */

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;


/**
 * @author Firefly
 * 结合
 */

public class TestRobot {

    public static void main(String[] args) throws AWTException {
        Robot robot = new Robot();
        Random random = new Random();
        Double d = 0.2 + random.nextDouble() * 0.2;
        System.out.println(d);
        System.out.println("keypress k!!!!");
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.mouseMove(700, 600);
        robot.delay(3000);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

    }


}


