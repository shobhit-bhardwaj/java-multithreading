package com.multithreading.concurrent.executorFramework;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExceptionHandlingInExecutorTest {
	private static class MyThreadFactory implements ThreadFactory {
		private static int counter = 0;

		public Thread newThread(Runnable runnable) {
			counter++;

			Thread thread = new Thread(runnable, "Thread-" + counter);
			if (counter % 2 == 0)
				thread.setUncaughtExceptionHandler(new ThreadUncaughtExceptionHandler("Handler-" + counter));

			return thread;
		}
	}

	private static class ThreadUncaughtExceptionHandler implements UncaughtExceptionHandler {
		private String handlerName;

		public ThreadUncaughtExceptionHandler(String handlerName) {
			this.handlerName = handlerName;
		}

		@Override
		public void uncaughtException(Thread thread, Throwable exception) {
			System.out.println(this + " - " + thread.getName() + " - " + exception);
		}

		@Override
		public String toString() {
			return handlerName + "@" + hashCode();
		}
	}

	private static class SimpleTask implements Runnable {
		@Override
		public void run() {
			throw new RuntimeException();
		}
	}

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool(new MyThreadFactory());
		Thread.setDefaultUncaughtExceptionHandler(new ThreadUncaughtExceptionHandler("DEFAULT_HANDLER"));

		executorService.execute(new SimpleTask());
		executorService.execute(new SimpleTask());
		executorService.execute(new SimpleTask());
		executorService.execute(new SimpleTask());

		executorService.shutdown();
	}
}