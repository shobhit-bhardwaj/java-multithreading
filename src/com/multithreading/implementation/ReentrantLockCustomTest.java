package com.multithreading.implementation;

class ReentrantLockCustom {
	private boolean isLocked;
	private Thread lockedBy;
	private int lockCounter;

	public synchronized void lock() throws InterruptedException {
		Thread callingThread = Thread.currentThread();
		
		while(isLocked && lockedBy != callingThread) {
			wait();
		}
		isLocked = true;
		lockCounter ++;
		lockedBy = callingThread;
	}

	public synchronized void unlock() {
		if(Thread.currentThread() == lockedBy) {
			lockCounter --;
			if(lockCounter == 0) {
				isLocked = false;
				notify();
			}
		}
	}
}

class ReentrantLockTask extends Thread {
	private static ReentrantLockCustom lock = new ReentrantLockCustom();
	private static int counter = 0;

	private void incrementOuter() {
		try {
			lock.lock();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		incrementInner();
		lock.unlock();
	}

	private synchronized void incrementInner() {
		try {
			lock.lock();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		counter ++;
		lock.unlock();
	}

	@Override
	public void run() {
		for(int i=0; i<1000; i++)
			incrementOuter();
	}

	public static int getCounter() {
		return counter;
	}
}

public class ReentrantLockCustomTest {
	public static void main(String[] args) {
		Thread thread1 = new ReentrantLockTask();
		Thread thread2 = new ReentrantLockTask();

		thread1.start();
		thread2.start();

		try {
			thread1.join();
			thread2.join();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("Counter - "+ReentrantLockTask.getCounter());
	}
}