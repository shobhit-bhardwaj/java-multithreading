package com.multithreading.core;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadExceptionHandlingTest {
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
		Thread.setDefaultUncaughtExceptionHandler(new ThreadUncaughtExceptionHandler("DEFAULT_HANDLER"));
		Thread task1 = new Thread(new SimpleTask(), "Thread-1");
		task1.setUncaughtExceptionHandler(new ThreadUncaughtExceptionHandler("Handler-1"));

		Thread task2 = new Thread(new SimpleTask(), "Thread-2");
		Thread task3 = new Thread(new SimpleTask(), "Thread-3");
		Thread task4 = new Thread(new SimpleTask(), "Thread-4");
		task4.setUncaughtExceptionHandler(new ThreadUncaughtExceptionHandler("Handler-4"));

		task1.start();
		task2.start();
		task3.start();
		task4.start();
	}
}