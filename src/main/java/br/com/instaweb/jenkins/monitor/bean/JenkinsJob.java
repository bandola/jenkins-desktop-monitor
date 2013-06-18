package br.com.instaweb.jenkins.monitor.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import br.com.instaweb.jenkins.monitor.ui.tray.Icon;

@JsonIgnoreProperties(ignoreUnknown = true)	
public class JenkinsJob {

	@JsonProperty("lastBuild")
	private Build lastBuild;
	
	@JsonProperty("color")
	private BuildState state;

	public JenkinsJob(){
	}

	public JenkinsJob(BuildState state){
		this.state = state;
	}
	
	public BuildState getState() {
		return state;
	}
	
	public Icon icon(){
		return state.getIcon();
	}
	
	public Build getLastBuild() {
		return lastBuild;
	}
	
	public void setState(BuildState state) {
		this.state = state;
	}
	
	public void setLastBuild(Build lastBuild) {
		this.lastBuild = lastBuild;
	}
	
	public int lastBuildNumber(){
		return lastBuild == null ? -1 : lastBuild.getNumber(); 
	}
	
	
}
