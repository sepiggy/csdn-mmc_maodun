package cn.sepiggy.concurrency.ch06;

public class ThreadDemo {

    public static void main(String[] args) {
        new MyThread("t1").start();
        new MyThread("t2").start();
        new MyThread("t3").start();
    }

}
