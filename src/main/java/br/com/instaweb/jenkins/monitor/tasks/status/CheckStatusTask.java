package br.com.instaweb.jenkins.monitor.tasks.status;

import br.com.instaweb.jenkins.monitor.bean.BuildState;
import br.com.instaweb.jenkins.monitor.bean.JenkinsJob;
import br.com.instaweb.jenkins.monitor.events.BuildErrorEvent;
import br.com.instaweb.jenkins.monitor.events.BuildStateChangedEvent;
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
		JenkinsJob currentJob = null;
		try{
			currentJob = checkJenkinsStatus();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			dispatchBuildStateEvents(currentJob);
			updateLastBuildState(currentJob);
		}
	}
	
	private void updateLastBuildState(JenkinsJob currentJob) {
		lastBuild = currentJob == null ? BuildState.unknown : currentJob.getState();		
	}

	private void dispatchBuildStateEvents(JenkinsJob currentJob) {
		if(buildStatusHasChanged(currentJob)){
			eventBus.post(new BuildStateChangedEvent(currentJob.getState(), lastBuild));
		}
		
		if(lastFailedBuildNumber != currentJob.lastBuildNumber() && (currentJob.getState() == BuildState.red)){
			eventBus.post(new BuildErrorEvent(currentJob));
			lastFailedBuildNumber = currentJob.lastBuildNumber();
		}
	}

	private JenkinsJob checkJenkinsStatus() {
		JenkinsJob currentJob = service.getCurrentBuild();
		return currentJob;
	}

	private boolean buildStatusHasChanged(JenkinsJob currentJob){
		BuildState state = (currentJob == null ? BuildState.unknown : currentJob.getState());
		return state != lastBuild;
	}
}
