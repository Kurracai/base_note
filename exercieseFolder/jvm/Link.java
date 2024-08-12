/*
 * @date: Do not edit
 * @Author: Kurra
 * @Description: TO DO
 */
package exercieseFolder.jvm;
public class Link{
    static class InnerLink {
        InnerLink next;
        public String name;
        public InnerLink(){}
    }
    public static void main(String[] args) {
        InnerLink l1 = new InnerLink();
        l1.name="222";
        InnerLink l2 = new InnerLink();
        l2.name = "9999";
        l1.next = l2;
        l2 = null;
        System.out.println(l1.next.name);
    }
}