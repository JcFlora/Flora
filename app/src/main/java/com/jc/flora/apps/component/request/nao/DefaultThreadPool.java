package com.jc.flora.apps.component.request.nao;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Shijincheng on 2018/4/17.
 */

public class DefaultThreadPool {

    private static final int THREAD_POOL_SIZE = 6;
    // 最大线程数量
    private static final int THREAD_POOL_MAX_SIZE = 10;
    // 阻塞队列最大任务数量
    private static final int BLOCKING_QUEUE_SIZE = 20;

    /**
     * 缓冲BaseRequest任务队列
     */
    private static ArrayBlockingQueue<Runnable> sBlockingQueue = new ArrayBlockingQueue<>(
            BLOCKING_QUEUE_SIZE);

    /** 线程池，目前是十个线程 */
    private static AbstractExecutorService sPool = new ThreadPoolExecutor(
            THREAD_POOL_SIZE, THREAD_POOL_MAX_SIZE, 15L, TimeUnit.SECONDS,
            sBlockingQueue, new ThreadPoolExecutor.DiscardOldestPolicy());

    private static DefaultThreadPool sInstance = null;

    public static synchronized DefaultThreadPool getInstance() {
        if (sInstance == null) {
            sInstance = new DefaultThreadPool();
        }
        return sInstance;
    }

    public void removeAllTask() {
        sBlockingQueue.clear();
    }

    public void removeTaskFromQueue(Object obj) {
        sBlockingQueue.remove(obj);
    }

    /**
     * 关闭，并等待任务执行完成，不接受新任务
     */
    public void shutdown() {
        if (sPool != null) {
            sPool.shutdown();
        }
    }

    /**
     * 关闭，立即关闭，并挂起所有正在执行的线程，不接受新任务
     */
    public void shutdownRightnow() {
        if (sPool != null) {
            sPool.shutdownNow();
            try {
                // 设置超时极短，强制关闭所有任务
                sPool.awaitTermination(1, TimeUnit.MICROSECONDS);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行任务
     *
     * @param r
     */
    public void execute(final Runnable r) {
        if (r != null) {
            try {
                sPool.execute(r);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
}