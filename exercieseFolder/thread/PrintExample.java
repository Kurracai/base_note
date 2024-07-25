package exercieseFolder.thread;

public class PrintExample {
    // 交替打印方法1：使用volatile关键字和Object.wait()和Object.notifyAll()方法
    // 该方法的缺点是不能保证打印的顺序，因为两个线程都有可能先抢到锁，导致打印的顺序不确定。
    private static volatile int x = 0;
    private static Object lock = new Object();
    public static void main(String[] args) {
        TestThread1 t1 = new TestThread1();
        TestThread2 t2 = new TestThread2();
        TestThread3 t3 = new TestThread3();
        t1.start();
        t2.start();
        t3.start();
    }
    private static class TestThread1 extends Thread {

        @Override
        public void run() {
            synchronized (lock) {
            while(true){
                if(x==0){
                    System.out.println("A");
                    x=1;
                    lock.notifyAll();
                }else{
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted");
                    }
                }
            }}
        }
    }
    private static class TestThread2 extends Thread {

        @Override
        public void run() {
            synchronized (lock) {
            while(true){
                if(x==1){
                    System.out.println("B");
                    x=2;
                    lock.notifyAll();
                }else{
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted");
                    }
                }
            }}
        }
    }
    private static class TestThread3 extends Thread {

        @Override
        public void run() {
            synchronized (lock) {
            while(true){
                if(x==2){
                    System.out.println("C");
                    x=0;
                    lock.notifyAll();
                }else{
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Thread interrupted");
                    }
                }
            }}
        }
    }
}
