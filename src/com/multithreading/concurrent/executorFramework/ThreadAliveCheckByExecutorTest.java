package com.multithreading.concurrent.executorFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadAliveCheckByExecutorTest {
	private static class SimpleTask implements Runnable {
		private int delay;

		public SimpleTask(int delay) {
			this.delay = delay;
		}

		@Override
		public void run() {
			try {
				System.out.println("SimpleTask Thread running by Thread - " + Thread.currentThread().getName());
				TimeUnit.SECONDS.sleep(delay);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleTask task1 = new SimpleTask(4);
		SimpleTask task2 = new SimpleTask(7);

		ExecutorService service = Executors.newCachedThreadPool();
		Future<?> future1 = service.submit(task1);
		Future<Integer> future2 = service.submit(task2, 100);

		while(true) {
			TimeUnit.MILLISECONDS.sleep(500);

			boolean done1 = future1.isDone();
			boolean done2 = future2.isDone();

			System.out.println("First Task Done - " + done1);
			System.out.println("Second Task Done - " + done2);

			if(done1 && done2)
				break;
		}

		System.out.println("Both Task Completed.");
		System.out.println("First Task Returns - " + future1.get());
		System.out.println("Second Task Returns - " + future2.get());

		service.shutdown();
	}
}