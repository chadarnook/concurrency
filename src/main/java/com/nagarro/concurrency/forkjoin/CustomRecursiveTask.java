package com.nagarro.concurrency.forkjoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CustomRecursiveTask extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 1L;
	private static final int THRESHOLD = 5;

	private int[] array;

	public CustomRecursiveTask(int[] array) {
		this.array = array;
	}

	@Override
	protected Integer compute() {
		System.out.println(Thread.currentThread().getName() + " computing: " + array);
		if(array.length > THRESHOLD) {
//			int result = 0;
//			Collection<CustomRecursiveTask> dividedTask = ForkJoinTask.invokeAll(createSubtasks());
//			for(CustomRecursiveTask task : dividedTask) {
//				result += task.join();
//			}
//			return result;
			return ForkJoinTask.invokeAll(createSubtasks()).stream().mapToInt(ForkJoinTask::join).sum();
		} else {
			return process(array);
		}
	}
	
	private Collection<CustomRecursiveTask> createSubtasks() {
		List<CustomRecursiveTask> tasks = new ArrayList<>();
		tasks.add(new CustomRecursiveTask(Arrays.copyOfRange(array, 0, array.length / 2)));
		tasks.add(new CustomRecursiveTask(Arrays.copyOfRange(array, array.length / 2, array.length)));
		return tasks;
	}
	
	private Integer process(int[] array) {
		return Arrays.stream(array).filter(a -> a % 5 == 0).map(a -> a * 10).sum();
	}
}
