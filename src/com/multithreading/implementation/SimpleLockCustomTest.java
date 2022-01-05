package com.multithreading.implementation;

import java.util.concurrent.TimeUnit;

class SimpleLockCustom {
	private boolean isLocked;

	public synchronized void lock() throws InterruptedException {
		while(isLocked) {
			wait();
		}

		isLocked = true;
	}

	public synchronized void unlock() {
		isLocked = false;

		notifyAll();
	}
}

public class SimpleLockCustomTest {

	private static class SimpleTask {
		private SimpleLockCustom lock = new SimpleLockCustom();

		public void actionMethod() {
			try {
				System.out.println("Entering to Critical Section by Thread - " + Thread.currentThread().getName());
				lock.lock();
				System.out.println("Entered by - " + Thread.currentThread().getName());
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			} finally {
				System.out.println("Exited by " + Thread.currentThread().getName());
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		SimpleTask task = new SimpleTask();

		new Thread(() -> task.actionMethod()).start();
		new Thread(() -> task.actionMethod()).start();
		new Thread(() -> task.actionMethod()).start();
	}
}