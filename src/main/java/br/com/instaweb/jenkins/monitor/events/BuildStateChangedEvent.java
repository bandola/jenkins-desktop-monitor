package br.com.instaweb.jenkins.monitor.events;

import br.com.instaweb.jenkins.monitor.bean.BuildState;

public class BuildStateChangedEvent {

	private final BuildState previous;
	private BuildState current;

	public BuildStateChangedEvent(BuildState current, BuildState previous) {
		this.current = current;
		this.previous = previous;
	}

	public BuildState getPrevious() {
		return previous;
	}

	public BuildState getCurrent() {
		return current;
	}
}
