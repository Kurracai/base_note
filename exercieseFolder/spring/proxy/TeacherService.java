package exercieseFolder.spring.proxy;

public class TeacherService implements ITeacher {

    @Override
    public void teach() {
        System.out.println("I am teaching");
    }
}
