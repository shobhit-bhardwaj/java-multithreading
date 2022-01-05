package com.multithreading.core;

import java.util.concurrent.TimeUnit;

public class SimpleThreadCreationByClass {
	private static class SimpleThread extends Thread {
		private int delay;

		public SimpleThread(String name, int delay) {
			super(name);

			this.delay = delay;
		}

		@Override
		public void run() {
			try {
				System.out.println("Thread run() Start By - " + Thread.currentThread().getName());
				TimeUnit.SECONDS.sleep(delay);
				System.out.println("Thread run() End By - " + Thread.currentThread().getName());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleThread thread1 = new SimpleThread("Simple Thread 1", 1);
		thread1.start();

		TimeUnit.SECONDS.sleep(3);

		Thread thread2 = new Thread(() -> System.out.println("This is Runnable Thread"), "Runnable Thread 2");
		thread2.start();
	}
}