package com.multithreading.core;

import java.util.concurrent.TimeUnit;

public class NotifyAndNotifyAllDifference {
	private static class SimpleTask {
		public synchronized void waitMethod() {
			try {
				System.out.println(Thread.currentThread().getName()+" - waitMethod Start.");
				wait();
				System.out.println(Thread.currentThread().getName()+" - waitMethod End.");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public synchronized void notifyMethod() {
			notify();
			System.out.println(Thread.currentThread().getName()+" - notifyMethod Called.");
		}

		public synchronized void notifyAllMethod() {
			notifyAll();
			System.out.println(Thread.currentThread().getName()+" - notifyAllMethod Called.");
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleTask task = new SimpleTask();

		new Thread(() -> task.waitMethod(), "Thread 1").start();
		new Thread(() -> task.waitMethod(), "Thread 2").start();
		new Thread(() -> task.waitMethod(), "Thread 3").start();

		TimeUnit.SECONDS.sleep(3);

		new Thread(() -> task.notifyMethod(), "Thread 4").start();
		//new Thread(() -> task.notifyAllMethod(), "Thread 4").start();
	}
}