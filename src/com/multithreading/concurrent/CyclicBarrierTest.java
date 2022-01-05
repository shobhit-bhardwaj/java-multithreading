package com.multithreading.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

class ViewerThread extends Thread {
	private String movieName;
	private CyclicBarrier movieBarrier;

	public ViewerThread(String movieName, CyclicBarrier movieBarrier) {
		this.movieName = movieName;
		this.movieBarrier = movieBarrier;
	}

	@Override
	public void run() {
		System.out.println("Waiting for Viewers (" + movieName + "). Currently We Have - " + (movieBarrier.getNumberWaiting() + 1) + ", Required - " + movieBarrier.getParties());
		try {
			movieBarrier.await();
		} catch (InterruptedException | BrokenBarrierException ex) {
			ex.printStackTrace();
		}
	}
}

class MovieShow extends Thread {
	private String movieName;

	public MovieShow(String movieName) {
		this.movieName = movieName;
	}

	@Override
	public void run() {
		System.out.println("Showing Movie - " + this.movieName);
	}
}

public class CyclicBarrierTest {
	public static void main(String[] args) throws Exception {
		//	Barrier for Batman (Show will Start for Every 5 Users)
		String movieName = "The Batman Begins";
		MovieShow batman = new MovieShow(movieName);
		CyclicBarrier batmanBarrier = new CyclicBarrier(5, batman);

		ViewerThread thread;
		for(int i=0; i<10; i++) {
			TimeUnit.MILLISECONDS.sleep(400);
			thread = new ViewerThread(movieName, batmanBarrier);
			thread.start();
		}

		//	Barrier for SpiderMan (Show will Start for Every 7 Users)
		movieName = "The Amazing SpiderMan";
		MovieShow spiderMan = new MovieShow(movieName);
		CyclicBarrier spiderManBarrier = new CyclicBarrier(7, spiderMan);

		for(int i=0; i<14; i++) {
			TimeUnit.MILLISECONDS.sleep(400);
			thread = new ViewerThread(movieName, spiderManBarrier);
			thread.start();
		}
	}
}