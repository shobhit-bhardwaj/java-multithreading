package com.multithreading.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BlockingQueueProducerConsumerProblem {
	private static class ProducerThread implements Runnable {
		private BlockingQueue<Integer> blockingQueue;

		public ProducerThread(BlockingQueue<Integer> blockingQueue) {
			this.blockingQueue = blockingQueue;
		}

		@Override
		public void run() {
			int counter = 0;

			while(true) {
				counter++;
				try {
					blockingQueue.put(counter);
					System.out.println("Put Value in Queue - " + counter);
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private static class ConsumerThread implements Runnable {
		private BlockingQueue<Integer> blockingQueue;

		public ConsumerThread(BlockingQueue<Integer> blockingQueue) {
			this.blockingQueue = blockingQueue;
		}

		@Override
		public void run() {
			while(true) {
				try {
					int value = blockingQueue.take();
					System.out.println("Take Value from Queue - " + value);
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(5);

		ExecutorService service = Executors.newCachedThreadPool();

		service.execute(new ProducerThread(blockingQueue));
		TimeUnit.SECONDS.sleep(5);
		service.execute(new ConsumerThread(blockingQueue));

		service.shutdown();
	}
}