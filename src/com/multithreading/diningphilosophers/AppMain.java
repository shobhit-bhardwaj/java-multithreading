package com.multithreading.diningphilosophers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AppMain {
	public static void main(String[] args) {
		try {
			ChopStick[] chopSticks = new ChopStick[Constant.NO_OF_CHOPSTICKS];
			Philosopher[] philosophers = new Philosopher[Constant.NO_OF_PHILOSOPHER];

			for(int i=0; i<Constant.NO_OF_CHOPSTICKS; i++)
				chopSticks[i] = new ChopStick(i);

			ExecutorService service = Executors.newFixedThreadPool(Constant.NO_OF_PHILOSOPHER);

			for(int i=0; i<Constant.NO_OF_PHILOSOPHER; i++) {
				philosophers[i] = new Philosopher(i, chopSticks[i], chopSticks[(i+1) % Constant.NO_OF_CHOPSTICKS]);
				service.execute(philosophers[i]);
			}

			TimeUnit.SECONDS.sleep(Constant.TOTAL_SIMULATION_TIME);
			System.out.println("Stopping Simulation");

			for(int i=0; i<Constant.NO_OF_PHILOSOPHER; i++)
				philosophers[i].setFull(true);

			TimeUnit.SECONDS.sleep(1);
			service.shutdown();
			TimeUnit.SECONDS.sleep(1);

			for(int i=0; i<Constant.NO_OF_PHILOSOPHER; i++)
				System.out.println(philosophers[i] + " - had eat - " + philosophers[i].getEatCounter());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}