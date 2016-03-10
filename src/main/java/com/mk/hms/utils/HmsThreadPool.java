package com.mk.hms.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 默认线程池
 * @author hdy
 *
 */
public class HmsThreadPool {
	private static final Logger log = LoggerFactory.getLogger(HmsThreadPool.class);
    
    private static int maxThreadNum = 10;

    private static int minThreadNum = 5;

    private static int idelTIme = 600;

    private static int queueNum = 1000;

    private static HmsThreadPool defaultPool = new HmsThreadPool(minThreadNum, maxThreadNum, queueNum);

    private ThreadPoolExecutor threadPool = null;

    /**
     * 
     * @param minThread 最小线程数
     * @param maxThread 最大线程数
     * @param queueNum 队列大小
     */
    public HmsThreadPool(int minThread, int maxThread, int queueNum) {
        threadPool = new ThreadPoolExecutor(minThread, maxThread, idelTIme,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(queueNum));
    }

    /**
     * 执行线程任务
     * 
     * @param task 线程任务
     */
    public void execute(ThreadTask task) {
        while (true) {
            try {
                threadPool.execute(task);
                break;
            } catch (RejectedExecutionException e) {
                try {
                    Thread.sleep(10);
                } catch (Exception ex) {
                    log.warn(ex.getMessage());
                }
            }
        }
    }

    /**
     * 
     * @return 线程池的任务是否执行完成？
     */
    public boolean isFinished() {
        if (threadPool.getActiveCount() == 0) {
            return true;
        }
        return false;
    }
    
    /**
     * 直到线程池的任务都执行完成。
     */
    public void untilFinished() {
        while (!this.isFinished()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                log.debug(e.getMessage(), e);
            }
        }
    }

    /**
     * 终止线程池
     */
    public void shutdown() {
        threadPool.shutdown();
    }

    /**
     * 
     * @return 默认的线程池
     */
    public static HmsThreadPool getDefaultPool() {
        return defaultPool;
    }

}
