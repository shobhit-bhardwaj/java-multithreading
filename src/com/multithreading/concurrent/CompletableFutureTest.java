package com.multithreading.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CompletableFutureTest {
	private static class SimpleRunnableTask implements Runnable {
		@Override
		public void run() {
			try {
				System.out.println("Inside Run");
				TimeUnit.SECONDS.sleep(2);
				System.out.println("Exiting Run");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private static class SimpleCallableTask implements Supplier<String> {
		@Override
		public String get() {
			try {
				System.out.println("Inside Supplier Get");
				TimeUnit.SECONDS.sleep(2);
				System.out.println("Exiting Supplier Get");
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return "DONE";
		}
	}

	private static String getA() {
		try {
			System.out.println("Inside Method getA");
			TimeUnit.SECONDS.sleep(5);
			System.out.println("Exiting Method getA");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "A";
	}

	private static String getB() {
		try {
			System.out.println("Inside Method getB");
			TimeUnit.SECONDS.sleep(3);
			System.out.println("Exiting Method getB");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "B";
	}

	private static String getC() {
		try {
			System.out.println("Inside Method getC");
			TimeUnit.SECONDS.sleep(7);
			System.out.println("Exiting Method getC");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "C";
	}

	private static void test1() {
		try {
			CompletableFuture<String> a = CompletableFuture.supplyAsync(() -> getA());
			CompletableFuture<String> b = CompletableFuture.supplyAsync(() -> getB());
			CompletableFuture<Void> c = CompletableFuture.runAsync(() -> getC());

			CompletableFuture.allOf(a, b, c).join();

			System.out.println("Return From Method getA - " + a.get());
			System.out.println("Return From Method getB - " + b.get());
			System.out.println("Return From Method getC - " + c.get());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void test2() throws Exception {
		CompletableFuture<Void> runFuture = CompletableFuture.runAsync(new SimpleRunnableTask());
		System.out.println("Getting Value from runAsync - " + runFuture.get());

		CompletableFuture<String> callFuture = CompletableFuture.supplyAsync(new SimpleCallableTask());
		System.out.println("Getting Value from supplyAsync - " + callFuture.get());
	}

	private static void test3() throws Exception {
		CompletableFuture<String> textFuture = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return "Shobhit";
		})
		.thenApply(text -> "Welcome, " + text)
		.thenApply(text -> text + " to the Java World");

		System.out.println(textFuture.get());
	}

	private static void test4() throws Exception {
		CompletableFuture<Void> productFuture = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return "This is a Product";
		})
		.thenAccept(product -> System.out.println(product))
		.thenAccept(text -> System.out.println(text))
		.thenApply(text -> "Future Product")
		.thenAccept(text -> System.out.println(text))
		.thenRun(() -> System.out.println("Completed"));

		productFuture.get();
	}

	private static void test5() throws Exception {
		CompletableFuture<String> userFuture = CompletableFuture.supplyAsync(() -> {
			try {
				System.out.println("Retriving User");
				TimeUnit.SECONDS.sleep(2);
				System.out.println("Retrive USER1");
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return "USER1";
		});

		CompletableFuture<Integer> ageFuture = CompletableFuture.supplyAsync(() -> {
			try {
				System.out.println("Retriving Age");
				TimeUnit.SECONDS.sleep(2);
				System.out.println("Retrive Age 30");
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return 30;
		});

		CompletableFuture<Integer> userAgeFuture = userFuture.thenCompose(user -> {
			System.out.println(user);

			return ageFuture;
		});

		System.out.println(userAgeFuture.get());
	}

	private static void test6() throws Exception {
		CompletableFuture<Integer> weightFuture = CompletableFuture.supplyAsync(() -> {
			try {
				System.out.println("Calculating Weight");
				TimeUnit.SECONDS.sleep(2);
				System.out.println("Weight is 80");
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return 80;
		});

		CompletableFuture<Integer> heightFuture = CompletableFuture.supplyAsync(() -> {
			try {
				System.out.println("Calculating Height");
				TimeUnit.SECONDS.sleep(2);
				System.out.println("Height is 180");
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			return 180;
		});

		CompletableFuture<Double> BMIFuture = weightFuture.thenCombine(heightFuture, (weight, height) -> {
			double heightMeter = height/100;
			return weight / (heightMeter * heightMeter);
		});

		System.out.println("BMI - " + BMIFuture.get());
	}

	private static void test7() throws Exception {
		CompletableFuture<Integer> ageFuture = CompletableFuture
				.supplyAsync(() -> 0)
				.thenApply(age -> {
					if(age <= 0)
						throw new RuntimeException();
					return age;
				})
				.exceptionally(ex -> {
					System.out.println(ex.getMessage());
					return 10;
				});

		System.out.println(ageFuture.get());
	}

	private static void test8() throws Exception {
		CompletableFuture<Integer> ageFuture = CompletableFuture
				.supplyAsync(() -> 12)
				.thenApply(age -> {
					if(age <= 0)
						throw new RuntimeException();
					return age;
				})
				.handle((age, ex) -> {
					if(ex != null) {
						System.out.println(ex.getMessage());
						return 10;
					}

					return age;
				});

		System.out.println(ageFuture.get());
	}

	public static void main(String[] args) throws Exception {
		test1();
		//test2();
		//test3();
		//test4();
		//test5();
		//test6();
		//test7();
		//test8();
	}
}