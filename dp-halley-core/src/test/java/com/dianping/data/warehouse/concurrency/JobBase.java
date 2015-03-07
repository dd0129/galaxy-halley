package com.dianping.data.warehouse.concurrency;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public abstract class JobBase implements Runnable, Job {
    private Thread job = null;
	private Thread timer = null;
 
	public abstract int getJobTimeout();
 
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// Start the timer
		this.timer = new Timer();
		this.timer.start();
        Date date = new Date();
		System.out.println("Started the timer:" + date);
		
		// Create an instance of this job
		JobBase tmp = null;
		try {
			tmp = (JobBase) this.getClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		// Start the job
		if(tmp != null) {
			this.job = new Thread(tmp);
			this.job.start();
			System.out.println("Started the job");
		} else {
			System.out.println("Could not start the job");
		}
		
		// Wait for the running job
		while(job != null && job.isAlive()) {
		}
		
		// Stop the timer if still running
		if(timer != null && timer.isAlive()) {
			timer.stop();
			System.out.println("Stop the timer");
		}
		
		// Remove the job and timer;
		job = null;
		timer = null;
		System.out.println("Removed the job");
	}
 
	private class Timer extends Thread {
		public void run() {
			try {
				sleep(getJobTimeout());
				if (job != null && job.isAlive()) {
					job.stop();

					System.out.println("The job was killed due to a timeout");
				}
			} catch (Exception e) {
                System.out.println("exception occur");
				e.printStackTrace();
			}
		}
	}
}
 
 
