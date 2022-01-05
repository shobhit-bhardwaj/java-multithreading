package com.multithreading.concurrent.executorFramework;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorThreadsAwaitTermination {
	private static class SimpleRunnable implements Runnable {
		@Override
		public void run() {
			System.out.println("SimpleRunnable Thread Started by Thread - " + Thread.currentThread().getName());

			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			System.out.println("SimpleRunnable Thread Ended by Thread - " + Thread.currentThread().getName());
		}
	}

	private static class SimpleCallable implements Callable<Integer> {
		@Override
		public Integer call() {
			System.out.println("SimpleCallable Thread Started by Thread - " + Thread.currentThread().getName());

			try {
				TimeUnit.SECONDS.sleep(3);
				//TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			System.out.println("SimpleCallable Thread Ended by Thread - " + Thread.currentThread().getName());
			return 1;
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleRunnable runnable = new SimpleRunnable();

		SimpleCallable callable = new SimpleCallable();

		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(runnable);
		Future<Integer> future = service.submit(callable);

		service.shutdown();
		boolean terminated = service.awaitTermination(7, TimeUnit.SECONDS);

		System.out.println("awaitTermination - " + terminated);
	}
}