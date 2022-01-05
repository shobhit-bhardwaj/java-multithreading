package com.multithreading.implementation;

import java.util.concurrent.TimeUnit;

class CountDownLatchCustom {
	private int count;

	public CountDownLatchCustom(int count) {
		this.count = count;
	}

	public synchronized void await() throws InterruptedException {
		//if(count > 0)
			this.wait();
	}

	public synchronized void countDown() {
		count--;

		if(count == 0)
			this.notifyAll();
	}
}

public class CountDownLatchCustomTest {

	private static class Worker implements Runnable {
		private CountDownLatchCustom latch;

		public Worker(CountDownLatchCustom latch) {
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				System.out.println("Started Worker Method");

				latch.await();

				System.out.println("Performed Worker Method");
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static class Person implements Runnable {
		private CountDownLatchCustom latch;

		public Person(CountDownLatchCustom latch) {
			this.latch = latch;
		}

		@Override
		public void run() {
			System.out.println("Decrementing CountDown");
			latch.countDown();
		}
	}

	public static void main(String[] args) throws Exception {
		CountDownLatchCustom latch = new CountDownLatchCustom(3);

		//	Starting Worker Thread
		new Thread(new Worker(latch)).start();

		//	Decrementing CountDown
		TimeUnit.SECONDS.sleep(1);
		new Thread(new Person(latch)).start();

		TimeUnit.SECONDS.sleep(1);
		new Thread(new Person(latch)).start();

		TimeUnit.SECONDS.sleep(1);
		new Thread(new Person(latch)).start();
	}
}