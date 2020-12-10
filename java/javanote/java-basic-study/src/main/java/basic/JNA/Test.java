//package Basic.JNA;
//
///**
// * @author Firefly
// * @version 1.0
// * @date 2019/10/22 12:46
// * Java Native Access
// */
//
//import java.awt.AWTException;
//import java.awt.Robot;
//
//
///**
// * Created by lenovo on 2017/4/27.
// * 使用winID来获得窗口的类型和标题，然后发送消息或者其他操作
// *
// */
//public class Test {
//    public static void main(String[] args) {
//
//        HWND hwnd = User32.INSTANCE.FindWindow
//                (null, "TIM"); // 第一个参数是Windows窗体的窗体类，第二个参数是窗体的标题。不熟悉windows编程的需要先找一些Windows窗体数据结构的知识来看看，还有windows消息循环处理，其他的东西不用看太多。
//        if (hwnd == null) {
//            System.out.println("QQ is not running");
//        }
//        else{
//
//            User32 temp = User32.INSTANCE;
//
//            temp.ShowWindow(hwnd, 9 );        // SW_RESTORE
//            User32.INSTANCE.SetForegroundWindow(hwnd);   // bring to front
//
//            //User32.INSTANCE.GetForegroundWindow() //获取现在前台窗口
//            WinDef.RECT qqwin_rect = new  WinDef.RECT();
//            User32.INSTANCE.GetWindowRect(hwnd, qqwin_rect);
//
//            int qqx = qqwin_rect.left;
//            int qqy = qqwin_rect.top;
//
//
//            int qqwin_width = qqwin_rect.right-qqwin_rect.left;
//            int qqwin_height = qqwin_rect.bottom-qqwin_rect.top;
//
//            User32.INSTANCE.MoveWindow(hwnd, qqx, qqy, qqwin_width, qqwin_height, true);
//            for(int i = qqx; i < 800; i +=10) {
//                User32.INSTANCE.MoveWindow(hwnd, i, 100, qqwin_width, qqwin_height, true);   // bring to front
//                try {
//                    Thread.sleep(80);
//                }catch(Exception e){}
//            }
//
//            User32.INSTANCE.GetWindowRect(hwnd, qqwin_rect);
//            qqx = qqwin_rect.left;
//            qqy = qqwin_rect.top;
//
//            Robot robot;
//            try {
//                robot = new Robot();
//                robot.mouseMove(qqx, qqy);
//            } catch (AWTException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//
//            //User32.INSTANCE.PostMessage(hwnd, WinUser.WM_CLOSE, null, null);  // can be WM_QUIT in some occasio
//        }
//    }
//
//
//
//}