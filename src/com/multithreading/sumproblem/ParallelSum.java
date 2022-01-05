package com.multithreading.sumproblem;

public class ParallelSum {
	private static class SumTask extends Thread {
		private int[] numbers;
		private int low;
		private int high;
		private int partialSum;

		public SumTask(int[] numbers, int low, int high) {
			this.numbers = numbers;
			this.low = low;
			this.high = high;
		}

		@Override
		public void run() {
			partialSum = 0;

			for(int i=low; i<high; i++)
				partialSum += numbers[i];
		}

		public int getPartialSum() {
			return partialSum;
		}
	}

	public int sum(int[] numbers, int noOfProcessors) {
		int sum = 0;
		SumTask[] sumTasks = new SumTask[noOfProcessors];
		int steps = (int) Math.ceil(numbers.length / noOfProcessors);

		try {
			for(int i=0; i<noOfProcessors; i++) {
				sumTasks[i] = new SumTask(numbers, i * steps, (i+1) * steps);
				sumTasks[i].start();
				sumTasks[i].join();
			}

			for(int i=0; i<noOfProcessors; i++)
				sum += sumTasks[i].getPartialSum();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return sum;
	}
}