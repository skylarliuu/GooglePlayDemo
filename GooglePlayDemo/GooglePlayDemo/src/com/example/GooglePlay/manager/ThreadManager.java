package com.example.GooglePlay.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

/**线程池管理者
 * @author Administrator
 *
 */
public class ThreadManager {

	private static ThreadPool mThreadPool;
	
	public static ThreadPool getThreadPool(){
		if(mThreadPool == null){
			synchronized (ThreadManager.class) {
				if(mThreadPool == null){
					
					int cpuNum = Runtime.getRuntime().availableProcessors();//cpu个数
					//int threadNum = cpuNum * 2 + 1;
					int threadNum = 10;
					mThreadPool = new ThreadPool(threadNum,threadNum,1L);
				}
			}
		}
		
		return mThreadPool;
	}
	
	
	public static class ThreadPool{
		private int corePoolSize;
		private int maximumPoolSize;
		private long keepAliveTime;
		
		private ThreadPoolExecutor executor;
		
		private ThreadPool(int corePoolSize,int maximumPoolSize,long keepAliveTime){
			this.corePoolSize = corePoolSize;
			this.keepAliveTime = keepAliveTime;
			this.maximumPoolSize = maximumPoolSize;
		}
		
		public void execute(Runnable r){
			if(r == null){
				return ;
			}
			
			
			//队列已满,而且当前线程数已经超过最大线程数时的异常处理策略
			if (executor == null) {
				executor = new ThreadPoolExecutor(corePoolSize, //核心线程数
						maximumPoolSize, //最大线程数
						keepAliveTime, //线程休息时间
						TimeUnit.SECONDS,//时间单位
						new LinkedBlockingQueue<Runnable>(),//线程队列
						Executors.defaultThreadFactory(),//线程工厂
						new AbortPolicy());
			}
			
			executor.execute(r);
		}
		
		//从线程队列中移除
		public void remove(Runnable task){
			executor.getQueue().remove(task);
		}
		
		
	}
	
}
