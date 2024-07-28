package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("7777");
        Thread thread1 = new MyThread1();
        Thread thread2 = new MyThread2();
        thread1.start();
        thread2.start();
        Thread.sleep(5000);
        System.out.println(thread1.getState());
        thread1.interrupt();
        Thread.sleep(10000);
        System.out.println(thread1.getState());
    }

    private static final Object lock = new Object();
    private static  boolean flag = true;
    private static class MyThread1 extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                try {
                    System.out.println("Thread 1 waiting");
                    lock.wait();
                    System.out.println("Thread 1 running");
                }catch (InterruptedException e) {
                    System.out.println("1 faile");
                    System.out.println("22+"+Thread.currentThread().getState());
                }
                System.out.println("7769");
                System.out.println("22+"+Thread.currentThread().getState());
            }
        }
    }
    private static class MyThread2 extends Thread {
        @Override
        public void run() {
            synchronized (lock) {
                while (true) {
                    try{
                        if(flag){
                            System.out.println("Thread 2 running");
                            flag=false;}
                    }catch(Exception e){

                    }

                }
            }

        }
    }
}
