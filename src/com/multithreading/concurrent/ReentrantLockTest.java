package com.multithreading.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
	private static class SimpleTask {
		private ReentrantLock lock = new ReentrantLock( );

		public void task() {
			try {
				System.out.println("Try to Entering lock section by - " + Thread.currentThread().getName());
				lock.lock();
				System.out.println("Entered in lock section by - " + Thread.currentThread().getName());

				TimeUnit.SECONDS.sleep(3);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				lock.unlock();
				System.out.println("Returning lock section by - " + Thread.currentThread().getName());
			}
		}
	}

	public static void main(String[] args) {
		SimpleTask task = new SimpleTask();
		ExecutorService service = Executors.newCachedThreadPool();

		for(int i=0; i<5; i++) {
			service.execute(() -> task.task());
		}

		service.shutdown();
	}
}