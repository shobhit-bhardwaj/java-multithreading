package com.multithreading.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockHoldCountQueueLenght {
	private static class SimpleTask {
		private ReentrantLock lock = new ReentrantLock( );

		public void task() {
			try {
				lock.lock();
				System.out.println(Thread.currentThread().getName() + " - acquiring lock, getHoldCount - " + lock.getHoldCount());

				lock.lock();
				System.out.println(Thread.currentThread().getName() + " - acquiring lock, getHoldCount - " + lock.getHoldCount());

				TimeUnit.SECONDS.sleep(1);
				System.out.println("getQueueLength - " + lock.getQueueLength());

				TimeUnit.SECONDS.sleep(3);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				lock.unlock();
				System.out.println(Thread.currentThread().getName() + " - releasing lock, getHoldCount - " + lock.getHoldCount());

				lock.unlock();
				System.out.println(Thread.currentThread().getName() + " - releasing lock, getHoldCount - " + lock.getHoldCount());

				System.out.println("hasQueuedThreads - " + lock.hasQueuedThreads());
			}
		}
	}

	public static void main(String[] args) {
		SimpleTask task = new SimpleTask();
		ExecutorService service = Executors.newCachedThreadPool();

		for(int i=0; i<3; i++) {
			service.execute(() -> task.task());
		}

		service.shutdown();
	}
}