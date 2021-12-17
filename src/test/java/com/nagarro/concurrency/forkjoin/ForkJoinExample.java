package com.nagarro.concurrency.forkjoin;

import java.util.concurrent.ForkJoinPool;

import org.junit.Test;

public class ForkJoinExample {
	
	@Test
	public void forkRecursiveTask() {
		CustomRecursiveTask crt = new CustomRecursiveTask(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25});
		crt.fork(); //executed on same thread or thread from pool
		System.out.println(crt.join());
	}
	
	@Test
	public void invokeRecursiveTask() {
		CustomRecursiveTask crt = new CustomRecursiveTask(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25});
		System.out.println(ForkJoinPool.commonPool().invoke(crt));
	}
	
	@Test
	public void submitRecursiveTask() {
		CustomRecursiveTask crt = new CustomRecursiveTask(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25});
		ForkJoinPool.commonPool().submit(crt);
		System.out.println(crt.join());
	}
	
	@Test
	public void recursiveTask() {
		CustomRecursiveTask crt = new CustomRecursiveTask(new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25});
		System.out.println(crt.compute()); //executed in same thread
	}
	
	@Test
	public void recursiveAction() {
		CustomRecursiveAction cra = new CustomRecursiveAction("ThisIsAnExample");
		cra.compute();
	}

}
