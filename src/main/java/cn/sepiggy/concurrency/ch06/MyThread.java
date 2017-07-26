package cn.sepiggy.concurrency.ch06;

class MyThread extends Thread {
    private int ticket = 5;

    public MyThread() {
        super();
    }

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 10; i++) {
            if (ticket > 0) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println(this.getName() + ": ticket = " + ticket--);
            }
        }
    }
}
