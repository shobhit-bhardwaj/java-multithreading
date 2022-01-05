package com.multithreading.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTryLock {
	private static class SimpleTask {
		private ReentrantLock lock = new ReentrantLock( );

		public void task() {
			try {
				System.out.println("Try to Entering lock section by - " + Thread.currentThread().getName());
				boolean available = lock.tryLock();

				if(available) {
					System.out.println("Entered in lock section by - " + Thread.currentThread().getName());
					TimeUnit.SECONDS.sleep(5);
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

			System.out.println("Returning Method by - " + Thread.currentThread().getName());
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleTask task = new SimpleTask();
		ExecutorService service = Executors.newCachedThreadPool();

		service.execute(() -> task.task());
		TimeUnit.SECONDS.sleep(3);
		//TimeUnit.SECONDS.sleep(7);
		service.execute(() -> task.task());

		service.shutdown();
	}
}