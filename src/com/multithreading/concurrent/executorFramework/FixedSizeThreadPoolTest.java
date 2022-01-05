package com.multithreading.concurrent.executorFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedSizeThreadPoolTest {
	private static class SimpleTask implements Runnable {
		@Override
		public void run() {
			try {
				for(int i=0; i<5; i++) {
					TimeUnit.SECONDS.sleep(1);
					System.out.println("Counter - " + (i + 1) + " - by Thread - " + Thread.currentThread().getName());
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(3);

		for(int i=0; i<5; i++)
			executor.submit(new SimpleTask());

		executor.shutdown();
	}
}