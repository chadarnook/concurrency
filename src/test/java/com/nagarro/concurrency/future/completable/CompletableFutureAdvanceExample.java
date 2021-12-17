package com.nagarro.concurrency.future.completable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.Test;

public class CompletableFutureAdvanceExample {
	
	@Test
	public void anyOfCompletableFuture() {
		List<String> links = new ArrayList<>();
		for(int i=0;i<5;i++) {
			links.add("" + Math.random());
		}
		
		List<CompletableFuture<String>> downloadFutures = links.stream()
				.map(link -> downloadPage(link))
				.collect(Collectors.toList());
		
		CompletableFuture<Object> allFutures = CompletableFuture.anyOf(downloadFutures.toArray(new CompletableFuture[links.size()]));
		
		try {
			System.out.println("First future completed - " + allFutures.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void allOfCompletableFuture() {
		List<String> links = new ArrayList<>();
		for(int i=0;i<5;i++) {
			links.add("" + Math.random());
		}
		
		List<CompletableFuture<String>> downloadFutures = links.stream()
				.map(link -> downloadPage(link))
				.collect(Collectors.toList());
		
		CompletableFuture<Void> allFutures = CompletableFuture.allOf(downloadFutures.toArray(new CompletableFuture[links.size()]));

		// When all the Futures are completed, call `future.join()` to get their results and collect the results in a list -
		CompletableFuture<List<String>> allPageContentsFuture = allFutures.thenApply(v -> {
			   return downloadFutures.stream()
			           .map(pageContentFuture -> pageContentFuture.join())
			           .collect(Collectors.toList());
			});
		
		CompletableFuture<Long> countFuture = allPageContentsFuture.thenApply(pageContents -> {
		    return pageContents.stream()
		            .count();
		});

		try {
			System.out.println("Downloaded pages " + countFuture.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void thenCombineCompletableFuture() {
		String user = "Chad";
		
		CompletableFuture<Double> balanceFuture = getDesposits(user).thenCombine(getWithdraws(user), (deposits, withdraws) -> {
			return deposits - withdraws;
		});
		
		try {
			System.out.println(balanceFuture.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void thenComposeCompletableFuture() {
		Integer id = 20;
		
		
		CompletableFuture<Double> flattenedFuture = getUser(id).thenCompose(user -> getBalance(user));
		try {
			double balance = flattenedFuture.get();
			System.out.println(balance);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void thenComposeNotFlattenedCompletableFuture() {
		Integer id = 20;
		
		//Not Flattened
		CompletableFuture<CompletableFuture<Double>> future = getUser(id).thenApply(user -> getBalance(user));
		
		try {
			CompletableFuture<Double> firtFuture = future.get();
			double balance = firtFuture.get();
			System.out.println(balance);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private CompletableFuture<String> downloadPage(String link) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				long seconds = (int) (Math.random() * 10);
				System.out.println(seconds + " - downloading: " + link);
				TimeUnit.SECONDS.sleep(seconds);
				System.out.println("Download completed");
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return link;
		});
	}
	
	private CompletableFuture<Double> getDesposits(String user) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				long seconds = (int) (Math.random() * 10);
				System.out.println(seconds + " - Getting deposits for user: " + user);
				TimeUnit.SECONDS.sleep(seconds);
				System.out.println("Deposits completed");
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return 205.99;
		});
	} 
	
	private CompletableFuture<Double> getWithdraws(String user) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				long seconds = (int) (Math.random() * 10);
				System.out.println(seconds + " - Getting withdraws for user: " + user);
				TimeUnit.SECONDS.sleep(seconds);
				System.out.println("Withdraws completed");
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			return 55.99;
		});
	} 
	
	private CompletableFuture<String> getUser(int id) {
		return CompletableFuture.supplyAsync(() -> {
			System.out.println("Getting user with id: " + id);
			return "Chad";
		});
	}
	
	private CompletableFuture<Double> getBalance(String user) {
		return CompletableFuture.supplyAsync(() -> {
			System.out.println("Getting balance for user: " + user);
			return 99.99;
		});
	}
	
}
