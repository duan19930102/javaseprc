package current;

public class Job implements Runnable{
    @Override
    public void run() {
        System.out.println("打印工作");
    }
}
