package com.nagarro.concurrency.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class FutureExample {
	
	private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
	private static ExecutorService multiThreadExecutor = Executors.newFixedThreadPool(2);
	
	@Test
	public void singleFutureTask() {
    	Future<String> future1 = singleThreadExecutor.submit(new FutureTask("context1"));
    	String result;
		try {
			result = future1.get();
			System.out.println(result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		singleThreadExecutor.shutdown();
    }
	
	@Test
    public void multipleFutureTask() {
    	Future<String> future1 = singleThreadExecutor.submit(new FutureTask("context1"));
        Future<String> future2 = singleThreadExecutor.submit(new FutureTask("context2"));
        String result;
		try {
			result = future1.get();
			System.out.println(result);
			result = future2.get();
			System.out.println(result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		singleThreadExecutor.shutdown();
    }
	
	@Test
    public void multipleMultiThreadFutureTask() {
    	Future<String> future1 = multiThreadExecutor.submit(new FutureTask("context1"));
        Future<String> future2 = multiThreadExecutor.submit(new FutureTask("context2"));
        String result;
		try {
			result = future1.get();
			System.out.println(result);
			result = future2.get();
			System.out.println(result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		multiThreadExecutor.shutdown();
    }
}
