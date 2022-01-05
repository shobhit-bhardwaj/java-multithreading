package com.multithreading.core;

import java.util.concurrent.TimeUnit;

public class ThreadJoinTest {
	private static class SimpleTask extends Thread {
		private int delay;

		public SimpleTask(String name, int delay) {
			super(name);

			this.delay = delay;
		}

		@Override
		public void run() {
			try {
				System.out.println("Starting Thread - " + Thread.currentThread().getName());

				TimeUnit.SECONDS.sleep(delay);

				System.out.println("Ending Thread - " + Thread.currentThread().getName());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Main Thread Start");

		SimpleTask thread1 = new SimpleTask("Thread 1", 4);
		SimpleTask thread2 = new SimpleTask("Thread 2", 2);
		SimpleTask thread3 = new SimpleTask("Thread 3", 7);

		thread1.start();
		thread2.start();
		thread3.start();

		System.out.println("Joining Thread 1");
		thread1.join();

		System.out.println("Joining Thread 2");
		thread2.join();

		System.out.println("Joining Thread 3");
		thread3.join();

		System.out.println("Main Thread End");
	}
}