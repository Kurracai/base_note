package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TeacherService s = new TeacherService();
        ITeacher proxyInstance = (ITeacher) (new TeacherProxy(s).getProxyInstance());
        proxyInstance.teach();
    }

}
