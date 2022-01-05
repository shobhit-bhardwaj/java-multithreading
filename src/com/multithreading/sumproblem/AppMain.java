package com.multithreading.sumproblem;

import java.util.Random;

public class AppMain {
	public static void main(String[] args) {
		Random random = new Random();

		int length = 1_00_00_000;
		int[] numbers = new int[length];
		for(int i=0; i<length; i++)
			numbers[i] = random.nextInt(100);

		/*
		 * Sequential Sum
		 */
		long startTime = System.currentTimeMillis();
		int sum = new SequentialSum().sum(numbers);
		System.out.println("Sequential Sum - " + sum);

		long endTime = System.currentTimeMillis();
		System.out.println("Time Taken by Sequential Sum - " + (endTime - startTime));
		System.out.println();

		/*
		 * Parallel Sum
		 */
		int noOfProcessors = Runtime.getRuntime().availableProcessors();
		System.out.println("No of Processors - " + noOfProcessors);

		startTime = System.currentTimeMillis();
		sum = new ParallelSum().sum(numbers, noOfProcessors);
		System.out.println("Parallel Sum - " + sum);

		endTime = System.currentTimeMillis();
		System.out.println("Time Taken by Parallel Sum - " + (endTime - startTime));
	}
}