package com.nagarro.concurrency.future.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class CompletableFutureErrorHandlingExample {
	
	@Test
	public void handleChainCompletableFuture() {
				
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			int random = (int) (Math.random() * 10);
			System.out.println("First task: " + random);
			if(random > 5) {
				throw new IllegalArgumentException();
			}
			return random;
		}).handle((res, e) -> {
			if(e != null) {
				System.out.println("Error handled for first task");
				return -1;
			}
			return res;
		}).thenApply(result -> {
			int random = (int) (Math.random() * 10);
			System.out.println("Second task: " + random);
			if(random > 5) {
				throw new IllegalArgumentException();
			}
			return result + random;
		}).handle((res, e) -> {
			if(e != null) {
				System.out.println("Error handled for second task");
				return -2;
			}
			return res;
		}).thenApply(result -> {
			int random = (int) (Math.random() * 10);
			System.out.println("Third task: " + random);
			if(random > 5) {
				throw new IllegalArgumentException();
			}
			return result + random;
		}).handle((res, e) -> {
			if(e != null) {
				System.out.println("Error handled for third task");
				return -3;
			}
			return res;
		});
		
		try {
			System.out.println(future.get());
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	public void handleCompletableFuture() {
		int age = -1;
		
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			if(age < 0) {
				throw new IllegalArgumentException();
			}
			if(age > 18) {
				return "Adult";
			} else {
				return "Child";
			}
		}).handle((res,e) -> {
			System.out.println("Always goes into handle method...");
			if(e != null) {
				System.out.println("Invalid age");
				return "Error";
			}
			return res;
		});
		
		try {
			System.out.println(future.get());
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	public void exceptionallyChainCompletableFuture() {
				
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			int random = (int) (Math.random() * 10);
			System.out.println("First task: " + random);
			if(random > 5) {
				throw new IllegalArgumentException();
			}
			return random;
		}).exceptionally(e -> {
			System.out.println("Error handled for first task");
			return -1;
		}).thenApply(result -> {
			int random = (int) (Math.random() * 10);
			System.out.println("Second task: " + random);
			if(random > 5) {
				throw new IllegalArgumentException();
			}
			return result + random;
		}).exceptionally(e -> {
			System.out.println("Error handled for second task");
			return -2;
		}).thenApply(result -> {
			int random = (int) (Math.random() * 10);
			System.out.println("Third task: " + random);
			if(random > 5) {
				throw new IllegalArgumentException();
			}
			return result + random;
		}).exceptionally(e -> {
			System.out.println("Error handled for third task");
			return -3;
		});
		
		try {
			System.out.println(future.get());
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}
	}
	
	@Test
	public void exceptionallyCompletableFuture() {
		int age = -1;
				
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			if(age < 0) {
				throw new IllegalArgumentException();
			}
			if(age > 18) {
				return "Adult";
			} else {
				return "Child";
			}
		}).exceptionally(e -> {
			System.out.println("Invalid age");
			return "Error";
		});
		
		try {
			System.out.println(future.get());
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}
	}

}
