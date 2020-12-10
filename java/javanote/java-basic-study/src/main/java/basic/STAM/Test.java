package basic.STAM;//package Basic.STAM;
//
//
//
//class Test{
//
//
//
//    public static void main(String[] args) throws Exception{
//
//    // 不要使用SimpleEmail,会出现乱码问题
//        HtmlEmail email = new HtmlEmail();
//        try {
//        // 这里是SMTP发送服务器的名字：，普通qq号只能是smtp.qq.com
//        email.setHostName("smtp.qq.com");
//        email.setSmtpPort(465);
//        //开启 SSL 加密
//        email.setSSLOnConnect(true);
//        // 字符编码集的设置
//        email.setCharset("utf-8");
//        // 收件人的邮箱
//        email.addTo("507662857@qq.com");
//        // 发送人的邮箱
//        email.setFrom("531109985@qq.com", "firefly");
//
//        // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和得到的授权码
//        email.setAuthentication("xxxxx@qq.com", "授权码");
//        email.setSubject("java 不是一种好东西");
//        // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
//        email.setMsg("放弃java吧");
//        // 发送
//        email.send();
//
//        System.out.println ( "邮件发送成功!" );
//    } catch (Exception e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//        System.out.println ( "邮件发送失败!" );
//    }
//}
//}
//
//
//
//
