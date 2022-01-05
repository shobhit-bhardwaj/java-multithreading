package com.multithreading.studentlibrary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AppMain {
	public static void main(String[] args) {
		try {
			Book[] books = new Book[Constant.NO_OF_BOOKS];
			for(int i=0; i<Constant.NO_OF_BOOKS; i++)
				books[i] = new Book(i);

			ExecutorService service = Executors.newFixedThreadPool(Constant.NO_OF_STUDENTS);

			Student[] students = new Student[Constant.NO_OF_STUDENTS];
			for(int i=0; i<Constant.NO_OF_STUDENTS; i++) {
				students[i] = new Student(i, books);
				service.execute(students[i]);
			}

			TimeUnit.SECONDS.sleep(Constant.TOTAL_SIMULATION_TIME);
			System.out.println("Stopping Simulation");

			for(int i=0; i<Constant.NO_OF_STUDENTS; i++)
				students[i].setStop(true);

			service.shutdown();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}