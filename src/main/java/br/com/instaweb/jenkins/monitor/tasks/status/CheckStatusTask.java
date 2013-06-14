package br.com.instaweb.jenkins.monitor.tasks.status;

import java.util.TimerTask;

import br.com.instaweb.jenkins.monitor.bean.BuildInfo;
import br.com.instaweb.jenkins.monitor.bean.BuildState;
import br.com.instaweb.jenkins.monitor.service.JenkinsService;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;

public class CheckStatusTask extends TimerTask{

	private EventBus eventBus;
	private JenkinsService service;
	private BuildState lastBuild = BuildState.unknown;
	
	@Inject
	public CheckStatusTask(EventBus eventBus, JenkinsService client) {
		this.eventBus = eventBus;
		this.service = client;
	}

	@Override
	public void run() {
		BuildInfo info = service.getCurrentBuild();
		if(buildStatusHasChanged(info)){
			eventBus.post(new BuildStateChangedEvent(info.getState(), lastBuild));
		}
		lastBuild = info.getState();
	}
	
	private boolean buildStatusHasChanged(BuildInfo info){
		return info.getState() != lastBuild;
	}

}
