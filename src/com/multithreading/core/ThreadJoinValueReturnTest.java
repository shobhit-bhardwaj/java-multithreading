package com.multithreading.core;

import java.util.concurrent.TimeUnit;

public class ThreadJoinValueReturnTest {
	private static class SimpleTask implements Runnable {
		private int a;
		private int b;
		private int delay;

		private int sum;

		public SimpleTask(int a, int b, int delay) {
			this.a = a;
			this.b = b;
			this.delay = delay;
		}

		@Override
		public void run() {
			try {
				System.out.println(Thread.currentThread().getName() + " has Started Working.");
				TimeUnit.MILLISECONDS.sleep(delay);

				sum = a + b;

				System.out.println(Thread.currentThread().getName() + " has Done.");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public int getSum() {
			return sum;
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleTask task1 = new SimpleTask(2, 3, 2000);
		SimpleTask task2 = new SimpleTask(3, 6, 500);
		SimpleTask task3 = new SimpleTask(7, 9, 1000);

		Thread thread1 = new Thread(task1, "MyThread-1");
		Thread thread2 = new Thread(task2, "MyThread-2");
		Thread thread3 = new Thread(task3, "MyThread-3");

		thread1.start();
		thread2.start();
		thread3.start();

		thread1.join();
		thread2.join();
		thread3.join();

		System.out.println("Sum Result MyThread-1 - " + task1.getSum());
		System.out.println("Sum Result MyThread-2 - " + task2.getSum());
		System.out.println("Sum Result MyThread-3 - " + task3.getSum());
	}
}