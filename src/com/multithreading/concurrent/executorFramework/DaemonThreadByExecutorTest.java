package com.multithreading.concurrent.executorFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class DaemonThreadByExecutorTest {
	private static class MyThreadFactory implements ThreadFactory {
		private static int counter = 0;

		@Override
		public Thread newThread(Runnable runnable) {
			counter++;

			if(counter%2 == 0)
				return new Thread(runnable);
			else {
				Thread thread = new Thread(runnable);
				thread.setDaemon(true);

				return thread;
			}
		}
	}

	private static class SimpleTask implements Runnable {
		private int delay;

		public SimpleTask(int delay) {
			this.delay = delay;
		}

		@Override
		public void run() {
			try {
				System.out.println("SimpleTask called by Thread - " + Thread.currentThread().getName() + " - Having daemon - " + Thread.currentThread().isDaemon());
				TimeUnit.SECONDS.sleep(delay);
				System.out.println("SimpleTask Exited by Thread - " + Thread.currentThread().getName() + " - Having daemon - " + Thread.currentThread().isDaemon());
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool(new MyThreadFactory());

		service.execute(new SimpleTask(5));
		service.execute(new SimpleTask(2));

		service.shutdown();
	}
}