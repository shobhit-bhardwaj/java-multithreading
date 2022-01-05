package com.multithreading.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {
	private static class DelayedTask implements Delayed {
		private long delay;
		private String message;

		public DelayedTask(long delay, String message) {
			this.delay = delay + System.currentTimeMillis();
			this.message = message;
		}

		@Override
		public int compareTo(Delayed delayed) {
			DelayedTask delayedTask = (DelayedTask) delayed;

			if(this.delay > delayedTask.delay)
				return 1;
			else if(this.delay < delayedTask.delay)
				return -1;

			return 0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(delay - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}

		@Override
		public String toString() {
			return "DelayedTask [delay=" + delay + ", message=" + message + "]";
		}
	}

	public static void main(String[] args) throws Exception {
		BlockingQueue<DelayedTask> queue = new DelayQueue<>();

		System.out.println("Putting First Task");
		queue.put(new DelayedTask(3000, "First Task"));

		System.out.println("Putting Second Task");
		queue.put(new DelayedTask(10000, "Second Task"));

		System.out.println("Putting Third Task");
		queue.put(new DelayedTask(5000, "Third Task"));

		while(!queue.isEmpty()) {
			System.out.println(queue.take());
		}
	}
}