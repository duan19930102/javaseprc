package test;

public class IntergerTest {
    public static void main(String[] args) {
        Integer a = new Integer(1);
        Integer b = new Integer(1);

        Integer c = 1;
        Integer d = 2;

        Long g = 3L;


        System.err.println(a==b);
        System.err.println(g==(c+d));
        System.err.println(g.equals(a+b));
    }
}
