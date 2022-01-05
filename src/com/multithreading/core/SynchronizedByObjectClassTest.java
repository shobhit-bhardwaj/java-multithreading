package com.multithreading.core;

import java.util.concurrent.TimeUnit;

public class SynchronizedByObjectClassTest {
	private static class BufferSynchronized {
		//private static Object object = new Object();
		//private Object object = new Object();

		public void testMethod() throws Exception {
			//synchronized (object) {
			//synchronized (this) {
			synchronized (BufferSynchronized.class) {
				System.out.println(Thread.currentThread().getName() + " calling testMethod() Start");
				TimeUnit.SECONDS.sleep(3);
				System.out.println(Thread.currentThread().getName() + " calling testMethod() End");
			}
		}
	}

	private static class SimpleTask extends Thread {
		private BufferSynchronized buffer;

		public SimpleTask(String name, BufferSynchronized buffer) {
			super(name);
			this.buffer = buffer;
		}

		@Override
		public void run() {
			try {
				buffer.testMethod();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		BufferSynchronized buffer1 = new BufferSynchronized();
		SimpleTask task1 = new SimpleTask("Thread-1", buffer1);
		SimpleTask task2 = new SimpleTask("Thread-2", buffer1);
		task1.start();
		task2.start();

		BufferSynchronized buffer2 = new BufferSynchronized();
		SimpleTask task3 = new SimpleTask("Thread-3", buffer2);
		SimpleTask task4 = new SimpleTask("Thread-4", buffer2);
		task3.start();
		task4.start();
	}
}