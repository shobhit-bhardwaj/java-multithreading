package com.multithreading.core;

import java.util.concurrent.TimeUnit;

public class VolatileTest {
	private static volatile int MY_VALUE = 0;
	//private static int MY_VALUE = 0;

	private static class ChangeMaker extends Thread {
		@Override
		public void run() {
			int local_value = MY_VALUE;

			while(MY_VALUE < 5) {
				System.out.println("Incrementing MY_VALUE to - " + (local_value + 1));
				MY_VALUE = ++local_value;

				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private static class ChangeListener extends Thread {
		@Override
		public void run() {
			int local_value = MY_VALUE;

			while(local_value < 5) {
				if(local_value != MY_VALUE) {
					System.out.println("Got Changed for MY_VALUE - " + MY_VALUE);
					local_value = MY_VALUE;
				}
			}
		}
	}

	public static void main(String[] args) {
		new ChangeListener().start();
		new ChangeMaker().start();
	}
}