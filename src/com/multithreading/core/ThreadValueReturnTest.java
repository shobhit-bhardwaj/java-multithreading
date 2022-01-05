package com.multithreading.core;

import java.util.concurrent.TimeUnit;

public class ThreadValueReturnTest {
	private static class SimpleTask extends Thread {
		private int a;
		private int b;
		private long delay;
		private int sum;

		private volatile boolean done = false;

		public SimpleTask(String name, int a, int b, long delay) {
			super(name);

			this.a = a;
			this.b = b;
			this.delay = delay;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " has Started Working.");

			try {
				TimeUnit.MILLISECONDS.sleep(delay);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			sum = a + b;

			done = true;

			synchronized (this) {
				System.out.println(Thread.currentThread().getName() + " notifying Thread");
				this.notify();
			}

			System.out.println(Thread.currentThread().getName() + " has Done.");
		}

		public int getSum() {
			while (!done) {
				synchronized (this) {
					System.out.println(Thread.currentThread().getName() + " Waiting to be Processed");

					try {
						this.wait();
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					System.out.println(Thread.currentThread().getName() + " Processed");
				}
			}

			return sum;
		}
	}

	public static void main(String[] args) {
		SimpleTask task1 = new SimpleTask("Thread-1", 2, 3, 100);
		SimpleTask task2 = new SimpleTask("Thread-2", 4, 5, 3000);
		SimpleTask task3 = new SimpleTask("Thread-3", 6, 8, 1000);

		task1.start();
		task2.start();
		task3.start();

		System.out.println("Result 1 - " + task1.getSum());
		System.out.println("Result 2 - " + task2.getSum());
		System.out.println("Result 3 - " + task3.getSum());
	}
}