package com.multithreading.concurrent.executorFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TerminatingExecutorRunnableThread {
	private static class SimpleRunnable implements Runnable {
		private volatile boolean shutdown = false;

		@Override
		public void run() {
			try {
				while(true) {
					System.out.println("SimpleThread running by Thread - " + Thread.currentThread().getName());
					TimeUnit.SECONDS.sleep(1);

					if(shutdown) {
						System.out.println("Shutdown is called for Thread - " + Thread.currentThread().getName());
						break;
					}
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}

		public void cancel() {
			shutdown = true;
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleRunnable runnable1 = new SimpleRunnable();
		SimpleRunnable runnable2 = new SimpleRunnable();

		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(runnable1);
		service.execute(runnable2);

		TimeUnit.SECONDS.sleep(5);

		runnable1.cancel();
		runnable2.cancel();

		service.shutdown();
	}
}