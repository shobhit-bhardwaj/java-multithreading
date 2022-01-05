package com.multithreading.core;

/*
 * Problem with this Approach is, Two Thread can Read and Write at Same Time.
 */

/*
class SimpleTask extends Thread {
	private static int counter = 0;

	@Override
	public void run() {
		for(int i=1; i<=1000; i++) {
			counter++;
		}
	}

	public static int getCounter() {
		return counter;
	}
}
*/

public class SynchronizedMethod1Test {
	private static class SimpleTask extends Thread {
		private static int counter = 0;

		public static synchronized void increment() {
			counter++;
		}

		@Override
		public void run() {
			for(int i=1; i<=10000; i++) {
				increment();
			}
		}

		public static int getCounter() {
			return counter;
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleTask task1 = new SimpleTask();
		SimpleTask task2 = new SimpleTask();

		task1.start();
		task2.start();

		task1.join();
		task2.join();

		System.out.println("Counter - " + SimpleTask.getCounter());
	}
}