package br.com.instaweb.jenkins.monitor.service;

import br.com.instaweb.jenkins.monitor.bean.BuildInfo;

public interface JenkinsService {
	 
	BuildInfo getCurrentBuild();
	
	void disableBuild();

	void enableBuild();
	
	void build();

}
