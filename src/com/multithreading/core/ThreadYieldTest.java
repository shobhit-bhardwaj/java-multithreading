package com.multithreading.core;

public class ThreadYieldTest {
	private static class SimpleTask extends Thread {
		public SimpleTask(String name) {
			super(name);
		}

		@Override
		public void run() {
			for(int i=1; i<=10; i++) {
				Thread.yield();
				System.out.println(Thread.currentThread().getName() + " - Thread, counter - " + i);
			}
		}
	}

	public static void main(String[] args) {
		SimpleTask task1 = new SimpleTask("Thread-1");
		task1.setPriority(Thread.MIN_PRIORITY);

		SimpleTask task2 = new SimpleTask("Thread-2");
		task2.setPriority(Thread.MAX_PRIORITY);

		task1.start();
		task2.start();
	}
}