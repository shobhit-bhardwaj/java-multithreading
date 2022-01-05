package com.multithreading.diningphilosophers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChopStick {
	private int id;
	private Lock lock;

	public ChopStick(int id) {
		this.id = id;
		lock = new ReentrantLock();
	}

	public boolean pickUp(Philosopher philosopher, State state) throws InterruptedException {
		boolean isLock = lock.tryLock(100, TimeUnit.MILLISECONDS);
		if(isLock) {
			System.out.println(philosopher + " - picked the " + state + " ChopStik - " + this);
			return true;
		}

		System.out.println(philosopher + " - did not get the ChopStick");
		return false;
	}

	public void putDown(Philosopher philosopher, State state) {
		lock.unlock();
		System.out.println(philosopher + " - put down the " + state + " ChopStik - " + this);
	}

	@Override
	public String toString() {
		return "chopstick" +id;
	}
}