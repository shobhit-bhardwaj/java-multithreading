package com.multithreading.studentlibrary;

import java.util.Random;

public class Student implements Runnable {
	private int id;
	private Book[] books;
	private volatile boolean stop;
	private Random random;

	public Student(int id, Book[] books) {
		this.id = id;
		this.books = books;
		this.random = new Random();
	}

	@Override
	public void run() {
		while(!stop) {
			try {
				int bookId = random.nextInt(Constant.NO_OF_BOOKS);
				books[bookId].readBook(this);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	@Override
	public String toString() {
		return "Student" + id;
	}
}