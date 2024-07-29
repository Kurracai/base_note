package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ITeacher s = new TeacherService();
        ITeacher proxyInstance = (ITeacher) (new TeacherProxy(s).getNewProxyInstance());
        proxyInstance.teach();
    }

}
