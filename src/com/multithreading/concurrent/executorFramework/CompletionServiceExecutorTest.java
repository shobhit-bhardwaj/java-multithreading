package com.multithreading.concurrent.executorFramework;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletionServiceExecutorTest {
	private static class SimpleRunnable implements Runnable {
		private int delay;

		public SimpleRunnable(int delay) {
			this.delay = delay;
		}

		@Override
		public void run() {
			try {
				TimeUnit.MILLISECONDS.sleep(delay);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static class SimpleCallable implements Callable<Integer> {
		private int a;
		private int b;
		private int delay;

		public SimpleCallable(int a, int b, int delay) {
			this.a = a;
			this.b = b;
			this.delay = delay;
		}

		@Override
		public Integer call() throws Exception {
			TimeUnit.MILLISECONDS.sleep(delay);

			return a + b;
		}
	}

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();

		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executorService);
		completionService.submit(new SimpleCallable(2, 3, 2000));
		completionService.submit(new SimpleCallable(3, 4, 500));
		completionService.submit(new SimpleCallable(4, 5, 1000));

		// Future<?> future = executorService.submit(new SimpleRunnable(700));
		completionService.submit(new SimpleRunnable(700), 200);

		try {
			// System.out.println("Future Result - " + future.get());
			for (int i = 0; i < 4; i++) {
				System.out.println("Result - " + completionService.take().get());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		executorService.shutdown();
	}
}