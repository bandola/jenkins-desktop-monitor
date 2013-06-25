package br.com.instaweb.jenkins.monitor.tasks.status;

import br.com.instaweb.jenkins.monitor.bean.BuildState;
import br.com.instaweb.jenkins.monitor.bean.JenkinsJob;
import br.com.instaweb.jenkins.monitor.service.JenkinsService;
import br.com.instaweb.jenkins.monitor.tasks.Task;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;

public class CheckStatusTask implements Task{

	private EventBus eventBus;
	private JenkinsService service;
	private BuildState lastBuild = BuildState.unknown;
	private int lastFailedBuildNumber = -1; 
	
	@Inject
	public CheckStatusTask(EventBus eventBus, JenkinsService client) {
		this.eventBus = eventBus;
		this.service = client;
	}

	@Override
	public void run() {
		JenkinsJob currentJob = service.getCurrentBuild();
		if(buildStatusHasChanged(currentJob)){
			eventBus.post(new BuildStateChangedEvent(currentJob.getState(), lastBuild));
		}
		
		if(lastFailedBuildNumber != currentJob.lastBuildNumber() && (currentJob.getState() == BuildState.red)){
			eventBus.post(new BuildErrorEvent(currentJob));
			lastFailedBuildNumber = currentJob.lastBuildNumber();
		}
		
		lastBuild = currentJob.getState();

	}
	
	private boolean buildStatusHasChanged(JenkinsJob info){
		return info.getState() != lastBuild;
	}

}
