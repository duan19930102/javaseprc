public class Student implements Cloneable {
    private String name;
    private Subject sub;

    public Student(String s,String subject){
        name = s;
        sub = new Subject(subject);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject getSub() {
        return sub;
    }

    public void setSub(Subject sub) {
        this.sub = sub;
    }

    @Override
    protected Object clone() {
        Student s = new Student(name,sub.getName());
        return s;
    }
}
