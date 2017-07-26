package cn.sepiggy.concurrency.ch02;

public class PendingInterrupt {
    public static void main(String[] args) {
        // 如果输入了参数，则在 main 线程中中断当前线程（亦即 main 线程）
        if (args.length > 0) {
            Thread.currentThread().interrupt();
        }
        // 获取当前时间
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(2000);
            System.out.println("was NOT interrupted");
        } catch (InterruptedException x) {
            System.out.println("was interrupted");
        }
        // 计算中间代码执行的时间
        System.out.println("elapsedTime=" + (System.currentTimeMillis() - startTime));
    }
}
