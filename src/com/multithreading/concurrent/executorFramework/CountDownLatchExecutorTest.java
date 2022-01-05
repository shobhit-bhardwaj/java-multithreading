package com.multithreading.concurrent.executorFramework;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchExecutorTest {
	private static class SimpleTask implements Runnable {
		private CountDownLatch countDownLatch;
		private int delay;

		public SimpleTask(CountDownLatch countDownLatch, int delay) {
			this.countDownLatch = countDownLatch;
			this.delay = delay;
		}

		@Override
		public void run() {
			try {
				System.out.println("SimpleTask Started by Thread - " + Thread.currentThread().getName());
				TimeUnit.SECONDS.sleep(delay);

				System.out.println("Countdown Decresed after delay - " + delay + " - by Thread - " + Thread.currentThread().getName());
				countDownLatch.countDown();
				System.out.println("New Countdown - " + countDownLatch.getCount());
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		CountDownLatch countDownLatch = new CountDownLatch(3);

		SimpleTask task1 = new SimpleTask(countDownLatch, 3);
		SimpleTask task2 = new SimpleTask(countDownLatch, 5);
		SimpleTask task3 = new SimpleTask(countDownLatch, 7);

		ExecutorService service = Executors.newCachedThreadPool();
		service .execute(task1);
		service .execute(task2);
		service .execute(task3);

		countDownLatch.await();
		//countDownLatch.await(6, TimeUnit.SECONDS);

		System.out.println("All Tasks Completed Successfully.");

		service.shutdown();
	}
}