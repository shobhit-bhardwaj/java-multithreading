package com.multithreading.core;

public class OneTwoThreeGenerator {
	private static class BufferOneTwoThree {
		private int semaphore = 1;

		public synchronized void printOne(int number) {
			if(semaphore == 2 || semaphore == 3) {
				try {
					wait();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			System.out.println("Print One - " + number);
			semaphore = 2;
			notifyAll();
		}

		public synchronized void printTwo(int number) {
			if(semaphore == 1 || semaphore == 3) {
				try {
					wait();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			System.out.println("Print Two - " + number);
			semaphore = 3;
			notifyAll();
		}

		public synchronized void printThree(int number) {
			if(semaphore == 1 || semaphore == 2) {
				try {
					wait();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			System.out.println("Print Three - " + number);
			semaphore = 1;
			notifyAll();
		}
	}

	public static void main(String[] args) {
		BufferOneTwoThree buffer = new BufferOneTwoThree();

		for(int i=1; i<=30; i++) {
			final int x = i;

			new Thread(() -> buffer.printOne(x)).start();
			new Thread(() -> buffer.printTwo(x)).start();
			new Thread(() -> buffer.printThree(x)).start();
		}
	}
}