package com.multithreading.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueTest {
	private static class ProducerThread implements Runnable {
		private SynchronousQueue<Integer> queue;
		private int value;

		public ProducerThread(SynchronousQueue<Integer> queue, int value) {
			this.queue = queue;
			this.value = value;
		}

		@Override
		public void run() {
			try {
				queue.put(value);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private static class ConsumerThread implements Runnable {
		private SynchronousQueue<Integer> queue;

		public ConsumerThread(SynchronousQueue<Integer> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				int value = queue.take();
				System.out.println("Value - " + value);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ExecutorService service = Executors.newCachedThreadPool();
		SynchronousQueue<Integer> queue = new SynchronousQueue<>();

		for(int i=0; i<5; i++) {
			System.out.println("Starting Producer Thread with Value - " + (i+1));
			service.execute(new ProducerThread(queue, (i+1)));
		}

		TimeUnit.SECONDS.sleep(3);
		System.out.println("Starting Consumer Threads");

		for(int i=0; i<5; i++) {
			service.execute(new ConsumerThread(queue));
			TimeUnit.SECONDS.sleep(1);
		}

		service.shutdown();
	}
}