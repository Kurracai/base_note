package exercieseFolder.jvm;
public class TestA {
    public void print() {
        System.out.println("Hello World");
    }
    public static void main(TestA[] args) {
        java.lang.String a = new java.lang.String("print");
        System.out.println(a==a.intern());
    }
}