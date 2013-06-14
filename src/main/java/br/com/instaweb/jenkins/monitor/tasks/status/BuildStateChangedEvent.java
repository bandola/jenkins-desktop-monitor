package br.com.instaweb.jenkins.monitor.tasks.status;

import br.com.instaweb.jenkins.monitor.bean.BuildState;

public class BuildStateChangedEvent {

	private final BuildState current;
	private final BuildState previous;

	public BuildStateChangedEvent(BuildState result, BuildState previous) {
		this.current = result;
		this.previous = previous; 
	}

	public BuildState getCurrentResult() {
		return current;
	}
	
	public BuildState getPrevious() {
		return previous;
	}

}
