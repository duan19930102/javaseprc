package current;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultThreadPool2 implements ThreadPool {
    //最大的线程池数量
    private static final int MAX_WORKERS_NUMBER = 10;
    //最小的线程池数量
    private static final int MIN_WORKERS_NUMBER = 3;
    //默认的线程数量
    private static final int DEFAULT_WORKERS_NUMBER = 5;

    //工作的列表
    private final LinkedList<Job> jobs = new LinkedList<Job>();
    //工作者的列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());

    //工作者的数量
    private int workernum = DEFAULT_WORKERS_NUMBER;
    private AtomicLong threadNum = new AtomicLong();
    public DefaultThreadPool2(int num) {
        workernum = DEFAULT_WORKERS_NUMBER;
        if(num>MAX_WORKERS_NUMBER) {
            workernum = MAX_WORKERS_NUMBER;
        }

        if(num<MIN_WORKERS_NUMBER) {
            workernum = MIN_WORKERS_NUMBER;
        }
        initializeWorker(workernum);
    }
    //初始化线程工作者
    private void initializeWorker(int workernum) {
        for(int i=0;i<workernum;i++) {
            Worker worker = new Worker();
            Thread thread = new Thread(worker,"ThreadPool-Worker-"+threadNum.incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void execute(Job job) {
        if(job!=null) {
            synchronized (jobs) {
                jobs.addLast(job);
            }
        }

    }

    @Override
    public void shutdown() {
        for(Worker worker:workers) {
            worker.shutdown();
        }
    }

    @Override
    public void addWorkerNumber(int num) {
        synchronized (jobs) {
            if(num+this.workernum >MAX_WORKERS_NUMBER) {
                num = MAX_WORKERS_NUMBER - workernum;
            }
            initializeWorker(num);
            this.workernum += num;
        }



    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs) {
            if(num>this.workernum) {
                throw new IllegalArgumentException("beyond worknum");
            }

            //按照数量停止Worker
            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if(workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }
            this.workernum-=count;
        }
    }

    @Override
    public void getJobSize() {
        //return jobs.size();
    }

    class Worker implements Runnable{
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
                            //感知到外部对WorkerThread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    jobs.removeFirst();
                }

                if(job!=null) {
                    try{
                        job.run();
                    }catch (Exception ex) {

                    }
                }
            }
        }

        public void shutdown(){
            running = false;
        }

    }
}
