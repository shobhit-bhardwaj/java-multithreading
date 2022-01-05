package com.multithreading.concurrent.executorFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class CacheThreadPoolTest {
	private static class MyThreadFactory implements ThreadFactory {
		private static int counter = 1;
		private static String name = "Thread-";

		@Override
		public Thread newThread(Runnable runnable) {
			Thread thread = new Thread(runnable, name + counter++);
			return thread;
		}
	}

	private static class SimpleTask implements Runnable {
		@Override
		public void run() {
			try {
				for(int i=0; i<5; i++) {
					TimeUnit.MILLISECONDS.sleep(800);
					System.out.println("Counter - " + (i + 1) + " - By Thread - " + Thread.currentThread().getName());
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool(new MyThreadFactory());

		for(int i=0; i<3; i++)
			executor.submit(new SimpleTask());

		TimeUnit.SECONDS.sleep(5);

		for(int i=0; i<2; i++)
			executor.submit(new SimpleTask());

		executor.shutdown();
	}
}