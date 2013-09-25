package br.com.instaweb.jenkins.monitor.events;

import br.com.instaweb.jenkins.monitor.bean.JenkinsJob;

public class BuildErrorEvent {

	private JenkinsJob curent;

	public BuildErrorEvent(JenkinsJob current) {
		this.curent = current;
	}

	public JenkinsJob getCurent() {
		return curent;
	}
	

}
