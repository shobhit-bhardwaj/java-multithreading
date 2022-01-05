package com.multithreading.implementation;

import java.util.concurrent.TimeUnit;

class CyclicBarrierCustom {
	private int initialParties;
	private int parties;
	private Runnable runnable;

	public CyclicBarrierCustom(int parties, Runnable runnable) {
		this.initialParties = parties;
		this.parties = parties;
		this.runnable = runnable;
	}

	public synchronized void await() throws InterruptedException {
		parties--;

		if (parties == 0) {
			parties = initialParties;
			new Thread(runnable).start();
			this.notifyAll();
		} else
			this.wait();

	}
}

public class CyclicBarrierCustomTest {
	private static class Viewer extends Thread {
		private CyclicBarrierCustom barrier;

		public Viewer(CyclicBarrierCustom barrier) {
			this.barrier = barrier;
		}

		public void run() {
			try {
				System.out.println("Viewer Waiting For Movie");

				barrier.await();

				System.out.println("Viewer is now Watching Movie");
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Runnable barrierAction = () -> System.out.println("Movie is Started.");
		CyclicBarrierCustom barrier = new CyclicBarrierCustom(5, barrierAction);

		for (int i = 0; i < 10; i++) {
			TimeUnit.SECONDS.sleep(1);

			new Viewer(barrier).start();
		}
	}
}