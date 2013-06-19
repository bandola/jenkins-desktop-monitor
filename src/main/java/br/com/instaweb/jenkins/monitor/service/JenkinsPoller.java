package br.com.instaweb.jenkins.monitor.service;

import java.util.Timer;
import java.util.TimerTask;

import br.com.instaweb.jenkins.monitor.tasks.Task;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class JenkinsPoller implements Runnable {

	private final Task task;
	private Integer interval;

	@Inject
	public JenkinsPoller(@Named("jenkinsPollerTask") Task pollTask, @Named("taskInterval") Integer interval) {
		this.task = pollTask;
		this.interval = interval;
	}

	@Override
	public void run() {
		Timer timer = new java.util.Timer(getClass().getSimpleName());
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				try{
					task.execute();
				}catch (Exception e) {
					System.err.println("Could not execute task " + task.toString() + ": " + e.getMessage());
				}
			}
		}, 0, interval);
	}

}
