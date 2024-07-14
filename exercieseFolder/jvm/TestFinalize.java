/*
 * @date: Do not edit
 * @Author: Kurra
 * @Description: TO DO
 */
package exercieseFolder.jvm;

public class TestFinalize {
    private static TestFinalize STATIC_FINAL=null;
    @Override
    protected void finalize() throws Throwable{
        super.finalize();
        System.out.println("finalize执行了");
        TestFinalize.STATIC_FINAL = this; //再次搞一个引用指向自己
    }
    public static void main(String[] args) throws InterruptedException {
        STATIC_FINAL = new TestFinalize(); //引用指向对象
        STATIC_FINAL = null;
        System.gc();
        Thread.sleep(1000);
        if(STATIC_FINAL!=null){
            System.out.println("活");
        }
        if(STATIC_FINAL==null){
            System.out.println("死");
        }
        STATIC_FINAL = null; //将引用置空
        System.gc();
        Thread.sleep(1000);
        if(STATIC_FINAL!=null){
            System.out.println("活");
        }
        if(STATIC_FINAL==null){
            System.out.println("死");
        }
    }
}
