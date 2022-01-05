package com.multithreading.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {
	private static class SimpleTask {
		private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

		private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
		private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

		public void readTask() {
			try {
				System.out.println("ReadLock - Try to Entering readLock section by - " + Thread.currentThread().getName());
				readLock.lock();
				System.out.println("ReadLock - Entered in readLock section by - " + Thread.currentThread().getName());
				
				TimeUnit.SECONDS.sleep(3);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				readLock.unlock();
				System.out.println("ReadLock - Returning readLock section by - " + Thread.currentThread().getName());
			}
		}

		public void writeTask() {
			try {
				System.out.println("writeLock - Try to Entering writeLock section by - " + Thread.currentThread().getName());
				writeLock.lock();
				System.out.println("writeLock - Entered in writeLock section by - " + Thread.currentThread().getName());
				
				TimeUnit.SECONDS.sleep(3);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				writeLock.unlock();
				System.out.println("writeLock - Returning writeLock section by - " + Thread.currentThread().getName());
			}
		}
	}

	public static void main(String[] args) throws Exception {
		/*
		 * 1. If multiple reads occurred, all are permitted to read the data.
		 * 
		 * 2. If read operation is performing by multiple threads, then write
		 *    have to wait until all reads are performed.
		 * 
		 * 3. If write operation is performing, then read have to wait until
		 *    write is performed completely.
		 */

		SimpleTask task = new SimpleTask();
		ExecutorService service = Executors.newCachedThreadPool();

		service.execute(() -> task.readTask());
		TimeUnit.SECONDS.sleep(1);

		service.execute(() -> task.readTask());
		TimeUnit.SECONDS.sleep(1);

		service.execute(() -> task.writeTask());
		TimeUnit.SECONDS.sleep(1);

		service.execute(() -> task.readTask());
		TimeUnit.SECONDS.sleep(1);

		service.execute(() -> task.writeTask());

		service.shutdown();
	}
}