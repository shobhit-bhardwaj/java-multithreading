package com.multithreading.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TerminatingThreadByInterruptMethod {
	private static class SimpleTask extends Thread {

		public SimpleTask(String name) {
			super(name);
		}

		@Override
		public void run() {
			for(int i=1;; i++) {
				System.out.println(Thread.currentThread().getName() + " - Calling doAnotherTask().");
				doAnotherTask();

				if(Thread.interrupted()) {
					System.out.println(Thread.currentThread().getName() + " - is Interrupted Now.");
					break;
				}
			}

			System.out.println(Thread.currentThread().getName() + " - is Done.");
		}

		public void doAnotherTask() {
			List<Integer> intList = new ArrayList<>();
			Random randomGenerator = new Random();
			for(int i=0; i<100000; i++)
				intList.add(randomGenerator.nextInt(100000));
			Collections.sort(intList);
		}
	}

	public static void main(String[] args) throws Exception {
		SimpleTask task1 = new SimpleTask("Thread-1");
		SimpleTask task2 = new SimpleTask("Thread-2");
		SimpleTask task3 = new SimpleTask("Thread-3");

		task1.start();
		task2.start();
		task3.start();

		TimeUnit.SECONDS.sleep(5);

		System.out.println("Interrupting Thread-1");
		task1.interrupt();

		System.out.println("Interrupting Thread-2");
		task2.interrupt();

		System.out.println("Interrupting Thread-3");
		task3.interrupt();
	}
}