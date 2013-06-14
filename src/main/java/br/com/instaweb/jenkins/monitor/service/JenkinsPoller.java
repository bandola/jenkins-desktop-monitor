package br.com.instaweb.jenkins.monitor.service;

import java.util.Timer;
import java.util.TimerTask;

import com.google.inject.Inject;

public class JenkinsPoller implements Runnable {

	private static int FIVE_SECONDS = 5 * 1000;
	private final TimerTask task;

	@Inject
	public JenkinsPoller(@JenkinsPollerTask TimerTask pollTask) {
		this.task = pollTask;
	}

	@Override
	public void run() {
		Timer timer = new java.util.Timer(getClass().getSimpleName());
		timer.scheduleAtFixedRate(task, 0, FIVE_SECONDS);
	}


}
