package com.multithreading.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicDataTypes {
	private AtomicBoolean atomicBoolean = new AtomicBoolean(true);		// By Default false
	private AtomicInteger atomicInteger = new AtomicInteger(15);		// By Default 0
	private AtomicLong atomicLong = new AtomicLong(120);				// By Default 0

	public void atomicBooleanOperations() {
		System.out.println("Initial Value - " + atomicBoolean.get());

		atomicBoolean.set(false);
		System.out.println("After Setting false - " + atomicBoolean.get());

		boolean previousValue = atomicBoolean.getAndSet(true);
		System.out.println("getAndSet() Previous Value - " + previousValue + " | Current Value - " + atomicBoolean.get());

		boolean expectedValue = true;
		boolean updateValue = false;
		boolean success = atomicBoolean.compareAndSet(expectedValue, updateValue);
		System.out.println("Compare and Set Status - " + success + " | Updated Value - " + atomicBoolean.get());
	}

	public void atomicIntegerOperations() {
		System.out.println("Initial Value - " + atomicInteger.get());

		atomicInteger.set(25);
		System.out.println("After Setting 25 - " + atomicInteger.get());

		int previousValue = atomicInteger.getAndSet(35);
		System.out.println("getAndSet() Previous Value - " + previousValue + " | Current Value - " + atomicInteger.get());

		int expectedValue = 35;
		int updateValue = 45;
		boolean success = atomicInteger.compareAndSet(expectedValue, updateValue);
		System.out.println("Compare and Set Status - " + success + " | Updated Value - " + atomicInteger.get());

		previousValue = atomicInteger.getAndIncrement();
		System.out.println("getAndIncrement() Previous Value - " + previousValue + " | Current Value - " + atomicInteger.get());

		previousValue = atomicInteger.getAndDecrement();
		System.out.println("getAndDecrement() Previous Value - " + previousValue + " | Current Value - " + atomicInteger.get());

		previousValue = atomicInteger.incrementAndGet();
		System.out.println("incrementAndGet() Previous Value - " + previousValue + " | Current Value - " + atomicInteger.get());

		previousValue = atomicInteger.decrementAndGet();
		System.out.println("decrementAndGet() Previous Value - " + previousValue + " | Current Value - " + atomicInteger.get());

		previousValue = atomicInteger.getAndAdd(10);
		System.out.println("getAndAdd() Previous Value - " + previousValue + " | Current Value - " + atomicInteger.get());

		previousValue = atomicInteger.addAndGet(10);
		System.out.println("addAndGet() Previous Value - " + previousValue + " | Current Value - " + atomicInteger.get());
	}

	public void atomicLongOperations() {
		System.out.println("Initial Value - " + atomicLong.get());

		atomicLong.set(25);
		System.out.println("After Setting 150 - " + atomicLong.get());

		long previousValue = atomicLong.getAndSet(160);
		System.out.println("getAndSet() Previous Value - " + previousValue + " | Current Value - " + atomicLong.get());

		long expectedValue = 160;
		long updateValue = 170;
		boolean success = atomicLong.compareAndSet(expectedValue, updateValue);
		System.out.println("Compare and Set Status - " + success + " | Updated Value - " + atomicLong.get());

		previousValue = atomicLong.getAndIncrement();
		System.out.println("getAndIncrement() Previous Value - " + previousValue + " | Current Value - " + atomicLong.get());

		previousValue = atomicLong.getAndDecrement();
		System.out.println("getAndDecrement() Previous Value - " + previousValue + " | Current Value - " + atomicLong.get());

		previousValue = atomicLong.incrementAndGet();
		System.out.println("incrementAndGet() Previous Value - " + previousValue + " | Current Value - " + atomicLong.get());

		previousValue = atomicLong.decrementAndGet();
		System.out.println("decrementAndGet() Previous Value - " + previousValue + " | Current Value - " + atomicLong.get());

		previousValue = atomicLong.getAndAdd(10);
		System.out.println("getAndAdd() Previous Value - " + previousValue + " | Current Value - " + atomicLong.get());

		previousValue = atomicLong.addAndGet(10);
		System.out.println("addAndGet() Previous Value - " + previousValue + " | Current Value - " + atomicLong.get());
	}

	public static void main(String[] args) {
		AtomicDataTypes object = new AtomicDataTypes();

		System.out.println("--- AtomicBoolean Operations ---");
		object.atomicBooleanOperations();
		System.out.println();

		System.out.println("--- AtomicInteger Operations ---");
		object.atomicIntegerOperations();
		System.out.println();

		System.out.println("--- AtomicLong Operations ---");
		object.atomicLongOperations();
	}
}