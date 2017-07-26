package cn.sepiggy.concurrency.ch05;

public class Volatile implements Runnable {
    // value 变量没有被标记为 volatile
    private int value;
    // missedIt 变量被标记为 volatile
    private volatile boolean missedIt;
    // creationTime 不需要声明为 volatile，因为代码执行中它没有发生变化
    private long creationTime;

    public Volatile() {
        value = 10;
        missedIt = false;
        // 获取当前时间，亦即调用 Volatile 构造函数时的时间
        creationTime = System.currentTimeMillis();
    }

    public void run() {
        print("entering run()");

        // 循环检查 value 的值是否不同
        while (value < 20) {
            // 如果 missedIt 的值被修改为 true，则通过 break 退出循环
            if (missedIt) {
                // 进入同步代码块前，将 value 的值赋给 currValue
                int currValue = value;
                // 在一个任意对象上执行同步语句，目的是为了让该线程在进入和离开同步代码块时，
                // 将该线程中的所有变量的私有拷贝与共享内存中的原始值进行比较，
                // 从而发现没有用 volatile 标记的变量所发生的变化
                Object lock = new Object();
                synchronized (lock) {
                    // 不做任何事
                }
                // 离开同步代码块后，将此时 value 的值赋给 valueAfterSync
                int valueAfterSync = value;
                print("in run() - see value=" + currValue + ", but rumor has it that it changed!");
                print("in run() - valueAfterSync=" + valueAfterSync);
                break;
            }
        }
        print("leaving run()");
    }

    public void workMethod() throws InterruptedException {
        print("entering workMethod()");
        print("in workMethod() - about to sleep for 2 seconds");
        Thread.sleep(2000);
        // 仅在此改变 value 的值
        value = 50;
        print("in workMethod() - just set value=" + value);
        print("in workMethod() - about to sleep for 5 seconds");
        Thread.sleep(5000);
        // 仅在此改变 missedIt 的值
        missedIt = true;
        print("in workMethod() - just set missedIt=" + missedIt);
        print("in workMethod() - about to sleep for 3 seconds");
        Thread.sleep(3000);
        print("leaving workMethod()");
    }

    /*
    * 该方法的功能是在要打印的 msg 信息前打印出程序执行到此所化去的时间，以及打印 msg 的代码所在的线程
    */
    private void print(String msg) {
        // 使用 java.text 包的功能，可以简化这个方法，但是这里没有利用这一点
        long interval = System.currentTimeMillis() - creationTime;
        String tmpStr = " " + (interval / 1000.0) + "000";
        int pos = tmpStr.indexOf(".");
        String secStr = tmpStr.substring(pos - 2, pos + 4);
        String nameStr = " " + Thread.currentThread().getName();
        nameStr = nameStr.substring(nameStr.length() - 8, nameStr.length());
        System.out.println(secStr + " " + nameStr + ":" + msg);
    }

    public static void main(String[] args) {
        try {
            // 通过该构造函数可以获取实时时钟的当前时间
            Volatile vol = new Volatile();

            // 稍停 100ms，以让实时时钟稍稍超前获取时间，使 print（）中创建的消息打印的时间值大于 0
            Thread.sleep(100);

            Thread t = new Thread(vol);
            t.start();

            // 休眠 100ms，让刚刚启动的线程有时间运行
            Thread.sleep(100);
            //workMethod 方法在 main 线程中运行
            vol.workMethod();
        } catch (InterruptedException x) {
            System.err.println("one of the sleeps was interrupted");
        }
    }
}
