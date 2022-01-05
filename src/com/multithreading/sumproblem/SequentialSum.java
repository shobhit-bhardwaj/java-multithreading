package com.multithreading.sumproblem;

public class SequentialSum {
	public int sum(int[] numbers) {
		int sum = 0;

		for(int num : numbers)
			sum += num;

		return sum;
	}
}