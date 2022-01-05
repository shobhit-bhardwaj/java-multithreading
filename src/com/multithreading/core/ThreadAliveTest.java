package com.multithreading.core;

import java.util.concurrent.TimeUnit;

public class ThreadAliveTest {
	private static class SimpleTask extends Thread {
		private int delay;

		public SimpleTask(String name, int delay) {
			super(name);
			this.delay = delay;
		}

		@Override
		public void run() {
			for(int i=1; i<=10; i++) {
				System.out.println(Thread.currentThread().getName() + " - counter - " + i);
				try {
					TimeUnit.MILLISECONDS.sleep(delay);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleTask task1 = new SimpleTask("Thread-1", 500);
		SimpleTask task2 = new SimpleTask("Thread-2", 800);

		boolean aliveThread1 = task1.isAlive();
		boolean aliveThread2 = task2.isAlive();
		System.out.println("Initially Thread-1 Alive - " + aliveThread1);
		System.out.println("Initially Thread-2 Alive - " + aliveThread2);

		task1.start();
		task2.start();

		while(true) {
			TimeUnit.SECONDS.sleep(1);

			aliveThread1 = task1.isAlive();
			aliveThread2 = task2.isAlive();

			System.out.println("Thread-1 Alive Status - " + aliveThread1);
			System.out.println("Thread-2 Alive Status - " + aliveThread2);

			if(aliveThread1 == false && aliveThread2 == false)
				break;
		}
	}
}