package com.multithreading.concurrent.executorFramework;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TerminationBreakByExecutorThreads {
	private static class SimpleRunnable implements Runnable {
		private volatile boolean cancel = false;

		@Override
		public void run() {
			while(!cancel) {
				System.out.println(Thread.currentThread().getName() + " - SimpleRunnable inside Loop.");

				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}

			System.out.println(Thread.currentThread().getName() + " - SimpleRunnable Exits Loop.");
		}

		public void cancel() {
			cancel = true;
		}
	}

	private static class SimpleCallable implements Callable<Integer> {
		@Override
		public Integer call() {
			while(true) {
				System.out.println(Thread.currentThread().getName() + " - SimpleCallable inside Loop.");

				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();

		SimpleRunnable runnable = new SimpleRunnable();
		Future<?> runnableFuture = executor.submit(runnable);

		Future<Integer> callableFuture = executor.submit(new SimpleCallable());

		TimeUnit.SECONDS.sleep(5);

		runnable.cancel();
		callableFuture.cancel(true);

		//executor.shutdownNow();
		executor.shutdown();
	}
}