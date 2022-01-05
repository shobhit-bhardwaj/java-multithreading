package com.multithreading.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
	private static class SimpleTask {
		private Semaphore semaphore = new Semaphore(3);

		public void task() {
			try {
				semaphore.acquire();
				System.out.println("Semaphore acquire by Thread - " + Thread.currentThread().getName());

				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			} finally {
				semaphore.release();
				System.out.println("Semaphore release by Thread - " + Thread.currentThread().getName());
			}
		}
	}

	public static void main(String[] args) {
		SimpleTask task = new SimpleTask();
		ExecutorService service = Executors.newCachedThreadPool();

		for(int i=0; i<10; i++)
			service.execute(() -> task.task());

		service.shutdown();
	}
}