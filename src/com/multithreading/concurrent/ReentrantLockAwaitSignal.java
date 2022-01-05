package com.multithreading.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockAwaitSignal {
	private static class SimpleTask {
		private ReentrantLock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();

		public void awaitMethod() {
			try {
				System.out.println("Starting awaitMethod");
				lock.lock();

				condition.await();

				System.out.println("awaitMethod Completed");
				lock.unlock();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public void signalMethod() {
			try {
				System.out.println("Starting signalMethod");
				TimeUnit.SECONDS.sleep(2);
				lock.lock();

				condition.signal();

				System.out.println("signalMethod Completed");
				lock.unlock();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleTask task = new SimpleTask();

		Thread thread1 = new Thread(() ->task.awaitMethod());
		thread1.start();

		TimeUnit.SECONDS.sleep(3);

		Thread thread2 = new Thread(() ->task.signalMethod());
		thread2.start();
	}
}