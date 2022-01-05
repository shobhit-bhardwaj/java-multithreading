package com.multithreading.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

class BlockingQueueCustom {
	private int limit;
	private List<Integer> queue;

	public BlockingQueueCustom(int limit) {
		this.limit = limit;
		this.queue = new ArrayList<>();
	}

	public synchronized void enqueue(int value) {
		try {
			while (queue.size() == limit)
				this.wait();

			if (queue.size() == 0)
				this.notifyAll();

			queue.add(value);
			System.out.println("Adding Value - " + value + " | Queue - " + queue);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public synchronized int dequeue() {
		try {
			while (queue.size() == 0)
				this.wait();

			if (queue.size() == limit)
				this.notifyAll();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		Integer remove = queue.remove(0);
		System.out.println("Removed - " + remove + " | Queue - " + queue);

		return remove;
	}
}

public class BlockingQueueCustomTest {
	public static void main(String[] args) throws InterruptedException {
		BlockingQueueCustom blockingQueue = new BlockingQueueCustom(3);

		for(int i=0; i<10; i++) {
			final int x = i;
			new Thread(() -> blockingQueue.enqueue(x)).start();
		}

		TimeUnit.SECONDS.sleep(3);

		for(int i=0; i<10; i++) {
			TimeUnit.SECONDS.sleep(1);
			new Thread(() -> System.out.println("Removed - " + blockingQueue.dequeue())).start();
		}
	}
}