package br.com.instaweb.jenkins.monitor.tasks.status;

public class PullRequestEvent {

	private boolean hasPullRequests = false;
	
	public PullRequestEvent(boolean hasPullRequests){
		this.hasPullRequests = hasPullRequests;
	}
	
	public boolean hasPullRequests() {
		return hasPullRequests;
	}
}
