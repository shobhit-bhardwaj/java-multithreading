package com.multithreading.concurrent.executorFramework;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TerminatingAllExecutorThreads {
	private static class SimpleRunnable implements Runnable {
		@Override
		public void run() {
			try {
				while(true) {
					System.out.println("SimpleRunnable Thread running by Thread - " + Thread.currentThread().getName());
					TimeUnit.SECONDS.sleep(1);
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static class SimpleCallable implements Callable<Integer> {
		@Override
		public Integer call() {
			try {
				while(true) {
					System.out.println("SimpleCallable Thread running by Thread - " + Thread.currentThread().getName());
					TimeUnit.SECONDS.sleep(1);
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			return 1;
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleRunnable runnable1 = new SimpleRunnable();
		SimpleRunnable runnable2 = new SimpleRunnable();

		SimpleCallable callable1 = new SimpleCallable();
		SimpleCallable callable2 = new SimpleCallable();

		ExecutorService service = Executors.newCachedThreadPool();
		service.submit(runnable1);
		service.submit(runnable2);
		Future<Integer> future1 = service.submit(callable1);
		Future<Integer> future2 = service.submit(callable2);

		TimeUnit.SECONDS.sleep(5);

		service.shutdownNow();
	}
}