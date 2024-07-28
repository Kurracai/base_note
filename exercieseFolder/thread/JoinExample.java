package exercieseFolder.thread;

import java.util.concurrent.locks.LockSupport;

public class JoinExample {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new JoinThreadExample();
        t1.start();
        Thread main = Thread.currentThread();
        main.sleep(1000);
        t1.interrupt();
        System.out.println("Main thread is done");
    }
    private static class JoinThreadExample extends Thread {
        @Override
        public void run() {
            try {
                System.out.println("231121");
                sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("do something");
            }
            System.out.println("888454444");

        }
    }
}
