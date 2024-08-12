/*
 * @date: Do not edit
 * @Author: Kurra
 * @Description: TO DO
 */
package exercieseFolder.thread;

import java.util.concurrent.locks.LockSupport;

public class TestPark {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("Thread is about to park.");
            // 如果中断标记为 true，park 会立即返回
            LockSupport.park();
            // 检查中断状态
            if (Thread.interrupted()) {
                System.out.println("Thread was interrupted.");
            } else {
                System.out.println("Thread resumed without interruption.");
            }
        });

        thread.start();
        
        // 确保线程进入 park 状态
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 设置中断标记为 true
        thread.interrupt();
        thread.join();
    }
}