package br.com.instaweb.jenkins.monitor.tasks;

import java.util.TimerTask;

public interface TaskScheduler {

	void scheduleTask(TimerTask task);
}
