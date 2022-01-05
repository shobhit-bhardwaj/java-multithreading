package com.multithreading.core;

public class SynchronizedMethod2Test {

	private static class Resource {
		private int counter = 0;

		/*
		 * Problem with this Approach is, Two Thread can Read and Write at Same Time.
		 */
		/*public void increment() {
			counter++;
		}*/

		public synchronized void increment() {
			counter++;
		}

		public int getCounter() {
			return counter;
		}
	}

	private static class SimpleTask extends Thread {
		private Resource resource;

		public SimpleTask(Resource resource) {
			this.resource = resource;
		}

		@Override
		public void run() {
			for(int i=0; i<10000; i++)
				resource.increment();
		}
	}

	public static void main(String[] args) throws Exception {
		Resource resource = new Resource();

		SimpleTask thread1 = new SimpleTask(resource);
		SimpleTask thread2 = new SimpleTask(resource);

		thread1.start();
		thread2.start();

		thread1.join();
		thread2.join();

		System.out.println("Counter - " + resource.getCounter());
	}
}