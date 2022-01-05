package com.multithreading.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityBlockingQueueTest {
	private static class Person implements Comparable<Person> {
		private String name;
		private int age;

		public Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		@Override
		public int compareTo(Person person) {
			return Integer.compare(this.age, person.age);
		}

		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age + "]"; 
		}
	}

	public static void main(String[] args) throws Exception {
		BlockingQueue<Person> queue = new PriorityBlockingQueue<>();

		queue.put(new Person("Shobhit", 30));
		queue.put(new Person("Ramesh", 36));
		queue.put(new Person("Rajesh", 33));
		queue.put(new Person("Deepak", 26));
		queue.put(new Person("Abhinav", 28));

		TimeUnit.SECONDS.sleep(3);

		while(!queue.isEmpty()) {
			System.out.println(queue.take());
			TimeUnit.SECONDS.sleep(1);
		}
	}
}