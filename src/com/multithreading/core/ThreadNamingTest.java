package com.multithreading.core;

public class ThreadNamingTest {
	private static class SimpleTask extends Thread {
		public SimpleTask(String name) {
			super(name);
		}

		@Override
		public void run() {
			System.out.println("Simple Thread Run By - " + Thread.currentThread().getName());
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleTask thread1 = new SimpleTask("Simple Thread");
		thread1.start();

		Thread thread2 = new Thread(() -> System.out.println("Runnable Thread Run By - " + Thread.currentThread().getName()), "Runnable Thread");
		thread2.start();
	}
}