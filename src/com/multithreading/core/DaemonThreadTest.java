package com.multithreading.core;

import java.util.concurrent.TimeUnit;

public class DaemonThreadTest {
	private static class SimpleTask extends Thread {
		private int delay;

		public SimpleTask(int delay) {
			this.delay = delay;
		}

		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();
			String isDaemon = Thread.currentThread().isDaemon() ? "DAEMON" : "MAIN";
			System.out.println(threadName + " Nature - " + isDaemon);

			for(int i=1; i<=10; i++) {
				try {
					System.out.println(isDaemon + " - " + threadName + " - Counter - " + i);
					TimeUnit.SECONDS.sleep(delay);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			System.out.println(isDaemon + " - " + threadName + " - Exiting");
		}
	}

	public static void main(String[] args) {
		SimpleTask task1 = new SimpleTask(1);
		SimpleTask task2 = new SimpleTask(2);
		task2.setDaemon(true);

		task1.start();
		task2.start();
	}
}