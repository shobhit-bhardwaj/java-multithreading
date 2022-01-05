package com.multithreading.core;

import java.util.concurrent.TimeUnit;

public class TerminatingThreadByCancelMethod {
	private static class SimpleTask extends Thread {
		private int delay;

		private volatile boolean shutdown = false;

		public SimpleTask(String name, int delay) {
			super(name);

			this.delay = delay;
		}

		@Override
		public void run() {
			try {
				for(int i=1;; i++) {
					System.out.println(Thread.currentThread().getName() + " - Counter - " + i);
					TimeUnit.MILLISECONDS.sleep(delay);

					if(shutdown) {
						System.out.println("Shutdown is true for - " + Thread.currentThread().getName());
						break;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public void cancel() {
			System.out.println("cancel() is called for - " + Thread.currentThread().getName());
			shutdown = true;
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleTask task1 = new SimpleTask("Thread-1", 500);
		SimpleTask task2 = new SimpleTask("Thread-2", 700);
		SimpleTask task3 = new SimpleTask("Thread-3", 800);

		task1.start();
		task2.start();
		task3.start();

		TimeUnit.SECONDS.sleep(5);

		task1.cancel();
		task2.cancel();
		task3.cancel();
	}
}