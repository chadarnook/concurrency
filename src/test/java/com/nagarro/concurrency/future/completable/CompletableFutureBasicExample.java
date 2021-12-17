package com.nagarro.concurrency.future.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class CompletableFutureBasicExample {
	
	@Test
	public void thenRunAsyncCompletableFutureTask() {
		CompletableFuture<Void> chain = CompletableFuture.supplyAsync(() -> {
			System.out.println("Executing firstStep....");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Task1";
		}).thenRunAsync(() -> {
			System.out.println("Executing secondStep....");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			System.out.println("Confirmed without return value and no access to previous step result and on a different thread");
		});
		
		try {
			chain.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void thenRunCompletableFutureTask() {
		CompletableFuture<Void> chain = CompletableFuture.supplyAsync(() -> {
			System.out.println("Executing firstStep....");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Task1";
		}).thenRun(() -> {
			System.out.println("Executing secondStep....");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			System.out.println("Confirmed without return value and no access to previous step result");
		});
		
		try {
			chain.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void thenAcceptCompletableFutureTask() {
		CompletableFuture<Void> chain = CompletableFuture.supplyAsync(() -> {
			System.out.println("Executing firstStep....");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Task1";
		}).thenAccept(action -> {
			System.out.println("Executing secondStep....");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			System.out.println(action + " Confirmed without return value");
		});
		
		try {
			chain.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void thenApplyCompletableFutureTask() {
		CompletableFuture<String> chain = CompletableFuture.supplyAsync(() -> {
			System.out.println("Executing firstStep....");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Task1";
		}).thenApply(action -> {
			System.out.println("Executing secondStep....");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return action + " Task2";
		}).thenApply(action -> {
			System.out.println("Executing thirdStep....");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return action + " Confirmed";
		});
		
		try {
			String result = chain.get();
			System.out.println(result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void thenApplyCompletableFutureTaskExplicit() {
		CompletableFuture<String> firstStep = CompletableFuture.supplyAsync(() -> {
			System.out.println("Executing firstStep....");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Task1";
		});
		
		CompletableFuture<String> secondStep = firstStep.thenApply(action -> {
			System.out.println("Executing secondStep....");
			return action + " Confirmed";
		});
		
		String result;
		try {
			result = secondStep.get();
			System.out.println(result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void blockingCompletableFutureReturnTask() {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			System.out.println("Executing task....");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return "Task finished";
		});
		
		try {
			String result = future.get();
			System.out.println(result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void blockingCompletableFutureTask() {
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			System.out.println("Executing task....");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Task finished");
		});
		
		try {
			future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void simpleBlockingCompletableFuture() {
		CompletableFuture<String> completableFuture = new CompletableFuture<String>();
		completableFuture.complete("Completed");
		String result;
		try {
			result = completableFuture.get();
			System.out.println(result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}
