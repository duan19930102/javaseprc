package current;

public interface ThreadPool {
    //执行一个工作
    void execute(Job job);
    //关闭线程池
    void shutdown();
    //增加工作者
    void addWorkerNumber(int num);
    //减少工作者
    void removeWorker(int num);
    //得到正在等待执行的任务量
    void getJobSize();

}
