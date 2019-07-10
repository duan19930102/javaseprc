package current;



import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultThreadPool implements ThreadPool {
    //最大的线程池数量
    private static final int MAX_WORKER_NUMBERS = 10;
    //线程池默认的数量
    private static final int DEFAULT_WOKER_NUMERS = 5;
    //线程池最小的数量
    private static final int MIN_WORDER_NUMBERS = 3;

    //工作列表
    private final LinkedList<Job> jobs = new LinkedList<Job>();
    //工作者列表
    private final List<Worker> workerList = Collections.synchronizedList(new ArrayList<Worker>());
    //线程的编号
    private int workernum = DEFAULT_WOKER_NUMERS;
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool(int num){
        workernum = num;
        if(num >= MAX_WORKER_NUMBERS) {
            workernum = MAX_WORKER_NUMBERS;
        }
        if(num <= MIN_WORDER_NUMBERS) {
            workernum = MIN_WORDER_NUMBERS;
        }

        initializeWorkers(workernum);
    }

    private void initializeWorkers(int workernum) {
        for(int i=0;i<workernum;i++) {
            Worker worker = new Worker();
            workerList.add(worker);
            Thread thread = new Thread(worker,"ThreadPool-Worker-"+threadNum.incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void execute(Job job) {
        if(job != null ) {
            //添加一个工作，然后进行通知
            synchronized (jobs){
                jobs.add(job);
                jobs.notifyAll();
            }
        }
    }

    @Override
    public void shutdown() {
        for(Worker worker:workerList) {
            worker.shutdown();
        }
    }

    @Override
    public void addWorkerNumber(int num) {
        synchronized (jobs) {
            if(num+this.workernum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - this.workernum;
            }
            initializeWorkers(num);
            this.workernum += num;
        }

    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs) {
            if(num >= this.workernum) {
                throw new IllegalArgumentException("beyond workNum");
            }
            int count = 0;
            while (count < num) {
                Worker worker = workerList.get(count);
                if(workerList.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }
            this.workernum -= count;
        }
    }

    @Override
    public void getJobSize() {

    }

    class Worker implements Runnable {
        private volatile boolean running = true;
        @Override
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    //取出一个
                    job = jobs.removeFirst();
                }

                if(job !=null ) {
                    try {
                        job.run();
                    } catch (Exception ex) {

                    }
                }

            }


        }

        public void shutdown() {
            running = false;
        }
    }
}
