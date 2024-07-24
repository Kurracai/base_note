/*
 * @date: Do not edit
 * @Author: Kurra
 * @Description: TO DO
 */
package exercieseFolder.jvm;

import java.lang.ref.WeakReference;


public class TestWeakReference {
    public static void main(TestA[] args) throws InterruptedException {
        WeakReference<TestA> testWeak = new WeakReference<TestA>(new TestA("123"));
        System.gc();
        Thread.sleep(1000);
        if(testWeak.get()!=null){
            System.out.println("无法被回收");
        }else{
            System.out.println("已经被回收");
        }
    }
    
}
