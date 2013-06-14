package br.com.instaweb.jenkins.monitor.bean;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import br.com.instaweb.jenkins.monitor.ui.tray.Icon;

@JsonIgnoreProperties(ignoreUnknown = true)	
public class BuildInfo {

	public int number;
	
	@JsonProperty("color")
	private BuildState state;
	
	public BuildInfo(){
		
	}
	public BuildInfo(int number, BuildState state){
		this.number = number;
		this.state = state;
	}
	
	public BuildState getState() {
		return state;
	}
	
	public Icon icon(){
		return state.getIcon();
	}
	
	
}
