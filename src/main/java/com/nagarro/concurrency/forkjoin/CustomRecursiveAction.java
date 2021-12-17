package com.nagarro.concurrency.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class CustomRecursiveAction extends RecursiveAction {
	
	private static final long serialVersionUID = 1L;
	
	private String workload = "";
	private static final int THRESHOLD = 4;

	public CustomRecursiveAction(String workload) {
		this.workload = workload;
	}

	@Override
	protected void compute() {
		if(workload.length() > THRESHOLD) {
			ForkJoinTask.invokeAll(createSubtasks());
		} else {
			process(workload);
		}
	}
	
	private List<CustomRecursiveAction> createSubtasks() {
		List<CustomRecursiveAction> subtasks = new ArrayList<CustomRecursiveAction>();
		
		String partOne = workload.substring(0, workload.length() / 2);
        String partTwo = workload.substring(workload.length() / 2, workload.length());
        
        subtasks.add(new CustomRecursiveAction(partOne));
        subtasks.add(new CustomRecursiveAction(partTwo));

        return subtasks;
	}
	
	private void process(String work) {
		System.out.println("Processing: " + work);
		String result = work.toUpperCase();
		System.out.println(Thread.currentThread().getName() + " Processed without return value: " + result);
	}
	

}
