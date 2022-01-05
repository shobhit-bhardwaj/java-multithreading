package com.multithreading.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SynchronizedMultipleBlockTest {
	private Random random = new Random();

	private Object lock1 = new Object();
	private Object lock2 = new Object();

	private List<Integer> list1 = new ArrayList<>();
	private List<Integer> list2 = new ArrayList<>();

	public void fillList1() {
		synchronized (lock1) {
			try {
				TimeUnit.MILLISECONDS.sleep(1);
				list1.add(random.nextInt(100));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void fillList2() {
		synchronized (lock2) {
			try {
				TimeUnit.MILLISECONDS.sleep(1);
				list2.add(random.nextInt(100));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void process() {
		for(int i=1; i<=1000; i++) {
			fillList1();
			fillList2();
		}
	}

	public static void main(String[] args) throws Exception {
		SynchronizedMultipleBlockTest test = new SynchronizedMultipleBlockTest();

		long startTime = System.currentTimeMillis();

		Thread thread1 = new Thread(() -> test.process());
		thread1.start();

		Thread thread2 = new Thread(() -> test.process());
		thread2.start();

		thread1.join();
		thread2.join();

		long endTime = System.currentTimeMillis();

		System.out.println("Time Taken - " + (endTime - startTime));

		System.out.println("List Sizes - " + test.list1.size() + " - " + test.list1.size());
	}
}