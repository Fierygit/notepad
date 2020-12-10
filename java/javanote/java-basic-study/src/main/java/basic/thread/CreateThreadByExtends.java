package basic.thread;

class CreateThreadByExtends extends Thread {
    private Thread t;
    private String name;

    public CreateThreadByExtends(String name) {
        this.name = name;
        System.out.println(name + "creating!!!");

    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(name + " " + i);
            if (i == 1) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        println("fff");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void println(String string) {
        // TODO Auto-generated method stub
        System.out.println(string);
    }

    public void start() {
        System.out.println("Starting " + name);
        if (t == null) {
            t = new Thread(this, name);
            t.start();
        }
    }

    public static void main(String[] args) {
        CreateThreadByExtends t1 = new CreateThreadByExtends("Firefly");
        t1.start();
        CreateThreadByExtends t2 = new CreateThreadByExtends("five");
        t2.start();

    }


}

