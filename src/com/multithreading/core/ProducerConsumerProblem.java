package com.multithreading.core;

public class ProducerConsumerProblem {
	private static class Buffer {
		public boolean produced;

		public synchronized void produce(int number) {
			try {
				while(produced) {
					this.wait();
				}

				System.out.println("Produce - " + number);
				produced = true;
				notifyAll();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		public synchronized void consume(int number) {
			try {
				while(!produced) {
					this.wait();
				}

				System.out.println("Consume - " + number);
				produced = false;
				notifyAll();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Buffer buffer = new Buffer();

		for(int i=1; i<=10; i++) {
			int x = i;

			new Thread(() -> buffer.produce(x)).start();
			new Thread(() -> buffer.consume(x)).start();
		}
	}
}