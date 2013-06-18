package br.com.instaweb.jenkins.monitor.service;

import br.com.instaweb.jenkins.monitor.bean.JenkinsJob;

public interface JenkinsService {
	 
	JenkinsJob getCurrentBuild();
	
	void disableBuild();

	void enableBuild();
	
	void build();

}
