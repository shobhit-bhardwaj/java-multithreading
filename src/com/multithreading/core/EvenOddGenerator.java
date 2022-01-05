package com.multithreading.core;

public class EvenOddGenerator {
	private static class Buffer {
		public boolean even;

		public synchronized void printEven(int number) {
			try {
				while(even) {
					this.wait();
				}

				System.out.println("Even - " + number);
				even = true;
				notifyAll();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public synchronized void printOdd(int number) {
			try {
				while(!even) {
					this.wait();
				}

				System.out.println("Odd - " + number);
				even = false;
				notifyAll();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		Buffer buffer = new Buffer();

		for(int i=1; i<=20; i++) {
			final int x = i;

			if(x%2 == 0)
				new Thread(() -> buffer.printEven(x)).start();
			else
				new Thread(() -> buffer.printOdd(x)).start();
		}
	}
}