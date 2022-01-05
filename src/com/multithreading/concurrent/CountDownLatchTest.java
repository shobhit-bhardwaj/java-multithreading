package com.multithreading.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class WorkerThread extends Thread {
	private String activityName;
	private CountDownLatch activityLatch;

	public WorkerThread(String activityName, CountDownLatch activityLatch) {
		this.activityName = activityName;
		this.activityLatch = activityLatch;
	}

	@Override
	public void run() {
		try {
			activityLatch.countDown();
			System.out.println("Waiting for Worker (" + activityName + "). Currently We Have Remaining - " + activityLatch.getCount());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

class WorkActivity extends Thread {
	private String activityName;
	private CountDownLatch activityLatch;

	public WorkActivity(String activityName, CountDownLatch activityLatch) {
		this.activityName = activityName;
		this.activityLatch = activityLatch;
	}

	@Override
	public void run() {
		try {
			System.out.println("Waiting for Workers, Activity - " + this.activityName);
			activityLatch.await();
			//activityLatch.await(1, TimeUnit.SECONDS);
			System.out.println("Starting Activity - " + this.activityName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

public class CountDownLatchTest {
	public static void main(String[] args) throws Exception {
		String activityName = "Lifting Boxes";
		CountDownLatch activityLatch = new CountDownLatch(5);
		WorkActivity lifting = new WorkActivity(activityName, activityLatch);
		lifting.start();

		WorkerThread thread;
		for(int i=0; i<5; i++) {
			TimeUnit.MILLISECONDS.sleep(400);
			thread = new WorkerThread(activityName, activityLatch);
			thread.start();
		}
	}
}