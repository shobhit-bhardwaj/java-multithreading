package com.multithreading.studentlibrary;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Book {
	private int id;
	private Lock lock;

	public Book(int id) {
		this.id = id;
		this.lock = new ReentrantLock();
	}

	public boolean readBook(Student student) throws InterruptedException {
		boolean isLock = lock.tryLock(1, TimeUnit.SECONDS);
		if(isLock) {
			System.out.println(student + " - Started Reading Book - " + this);
			TimeUnit.SECONDS.sleep(Constant.READING_TIME);
			System.out.println(student + " - Completed Book - " + this);

			lock.unlock();
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "Book" + id;
	}
}