/*
 * @date: Do not edit
 * @Author: Kurra
 * @Description: TO DO
 */
package exercieseFolder.jvm;
/*
 * @date: Do not edit
 * @Author: Kurra
 * @Description: TO DO
 */

import java.lang.ref.WeakReference;


public class TestReference {
    public static void main(String[] args) throws InterruptedException {
        String testWeak = new String("123");
        System.gc();
        Thread.sleep(1000);
        if(testWeak!=null){
            System.out.println("无法被回收");
        }else{
            System.out.println("已经被回收");
        }
    }
    
}
