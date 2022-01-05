package com.multithreading.concurrent.executorFramework;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolTest {
	private static class SimpleTask implements Runnable {
		private int delay;

		public SimpleTask(int delay) {
			this.delay = delay;
		}

		@Override
		public void run() {
			try {
				System.out.println(Thread.currentThread().getName() + " - SimpleTask Start Time - " + new Date());
				TimeUnit.SECONDS.sleep(delay);
				System.out.println(Thread.currentThread().getName() + " - SimpleTask End Time - " + new Date());
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Main Method Start Time - " + new Date());

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

		/*
		 * Start this task after specified delay time like 5 seconds.
		 */
		executor.schedule(new SimpleTask(3), 5, TimeUnit.SECONDS);

		/*
		 * Start periodic tasks after an initial delay like 5 seconds
		 * and fixed gap between two tasks is 4 seconds.
		 */
		executor.scheduleAtFixedRate(new SimpleTask(3), 5, 4, TimeUnit.SECONDS);

		/*
		 * Start periodic tasks after an initial delay like 5 seconds and second
		 * task will be start after 2 seconds when first task will complete.
		 */
		executor.scheduleWithFixedDelay(new SimpleTask(3), 5, 2, TimeUnit.SECONDS);
	}
}