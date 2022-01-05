package com.multithreading.concurrent;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {
	private static class SimpleTask implements Runnable {
		private Exchanger<String> exchanger;
		private String value;

		public SimpleTask(Exchanger<String> exchanger, String value) {
			this.exchanger = exchanger;
			this.value = value;
		}

		@Override
		public void run() {
			try {
				System.out.println("Thread - " + Thread.currentThread().getName() + " - Previous Value - " + value);

				String newValue = exchanger.exchange(value);
				System.out.println("Thread - " + Thread.currentThread().getName() + " - New Value - " + newValue);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ExecutorService service = Executors.newCachedThreadPool();
		Exchanger<String> exchanger = new Exchanger<>();

		service.execute(new SimpleTask(exchanger, "A"));

		TimeUnit.SECONDS.sleep(3);

		service.execute(new SimpleTask(exchanger, "B"));

		service.shutdown();
	}
}