package cn.sepiggy.concurrency.ch02;

public class SleepInterrupt implements Runnable {
    public void run() {
        try {
            System.out.println("in run() - about to sleep for 20 seconds");
            Thread.sleep(20000);
            System.out.println("in run() - woke up");
        } catch (InterruptedException e) {
            System.out.println("in run() - interrupted while sleeping");
            // 处理完中断异常后，返回到 run（）方法入口，
            // 如果没有 return，线程不会实际被中断，它会继续打印下面的信息
            return;
        }
        System.out.println("in run() - leaving normally");
    }


    public static void main(String[] args) {
        SleepInterrupt si = new SleepInterrupt();
        Thread t = new Thread(si);
        t.start();
        // 主线程休眠 2 秒，从而确保刚才启动的线程有机会执行一段时间
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("in main() - interrupting other thread");
        // 中断线程 t
        t.interrupt();
        System.out.println("in main() - leaving");
    }
}





