package com.multithreading.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTryLockTimeout {
	private static class SimpleTask {
		private ReentrantLock lock = new ReentrantLock( );

		public void task() {
			try {
				System.out.println("Try to acquire lock for 5 Seconds by - " + Thread.currentThread().getName());
				boolean available = lock.tryLock(5, TimeUnit.SECONDS);

				if(available) {
					System.out.println("Entered in lock section by - " + Thread.currentThread().getName());
					TimeUnit.SECONDS.sleep(7);
				} else {
					System.out.println("Lock is not Available for - " + Thread.currentThread().getName());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if(lock.isHeldByCurrentThread()) {
					lock.unlock();
					System.out.println("Returning lock section by - " + Thread.currentThread().getName());
				}
			}

			System.out.println("Returning myMethod by - " + Thread.currentThread().getName());
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleTask task = new SimpleTask();
		ExecutorService service = Executors.newCachedThreadPool();

		service.execute(() -> task.task());
		TimeUnit.SECONDS.sleep(1);
		//TimeUnit.SECONDS.sleep(3);
		service.execute(() -> task.task());

		service.shutdown();
	}
}