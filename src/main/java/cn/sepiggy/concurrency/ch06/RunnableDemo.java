package cn.sepiggy.concurrency.ch06;

public class RunnableDemo {

    public static void main(String[] args) {
        MyThread my = new MyThread();
        new Thread(my).start();
        new Thread(my).start();
        new Thread(my).start();
    }

}
