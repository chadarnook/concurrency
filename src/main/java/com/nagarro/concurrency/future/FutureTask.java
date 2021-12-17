package com.nagarro.concurrency.future;

import java.util.concurrent.Callable;

public class FutureTask implements Callable<String> {

	private String context;
	
	public FutureTask(String context) {
		super();
		this.context = context;
	}

	@Override
	public String call() throws Exception {
		System.out.println("Executing task....");
		Thread.sleep(2000);
		return context.toUpperCase();
	}

}
