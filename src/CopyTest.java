public class CopyTest {

    public static void main(String[] args) {
        Student stud = new Student("duanzc","matth");
        System.err.println("original object" +stud.getName()+" "+stud.getSub().getName());

        //拷贝对象
        Student clonestudent = (Student) stud.clone();
        System.err.println("clone object" + clonestudent.getName()+" " + clonestudent.getSub().getName());
        System.err.println("拷贝对象和原始对象是否一致"+(stud==clonestudent));
        System.err.println("拷贝对象和原始对象的name是否一致"+(stud.getName()==clonestudent.getName()));
        System.err.println("拷贝对象和原始对象的subject是否一致"+(stud.getSub()==clonestudent.getSub()));

        stud.setName("duanzca");
        System.err.println("拷贝对象和原始对象的name是否一致"+(stud.getName()==clonestudent.getName()));
        System.err.println("拷贝对象和原始对象的subject是否一致"+(stud.getSub()==clonestudent.getSub()));

    }
}
