package com.multithreading.diningphilosophers;

import java.util.concurrent.TimeUnit;

public class Philosopher implements Runnable {
	private int id;
	private ChopStick leftChopStick;
	private ChopStick rightChopStick;
	private int eatCounter;
	private volatile boolean isFull;

	public Philosopher(int id, ChopStick leftChopStick, ChopStick rightChopStick) {
		this.id = id;
		this.leftChopStick = leftChopStick;
		this.rightChopStick = rightChopStick;
	}

	@Override
	public void run() {
		try {
			while(!isFull) {
				thinking();

				if(leftChopStick.pickUp(this, State.LEFT)) {
					if(rightChopStick.pickUp(this, State.RIGHT)) {
						eat();

						rightChopStick.putDown(this, State.RIGHT);
					}

					leftChopStick.putDown(this, State.LEFT);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void thinking() {
		try {
			System.out.println(this + " Thinking...");
			TimeUnit.SECONDS.sleep(Constant.THINKING_TIME);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private void eat() {
		try {
			System.out.println(this + " Eating Meal...");
			TimeUnit.SECONDS.sleep(Constant.EATING_TIME);
			eatCounter ++;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public int getEatCounter() {
		return eatCounter;
	}

	@Override
	public String toString() {
		return "philosopher" + id;
	}
}