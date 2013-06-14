package br.com.instaweb.jenkins.monitor.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import br.com.instaweb.jenkins.monitor.bean.BuildInfo;
import br.com.instaweb.jenkins.monitor.utils.Resources;

public class JenkinsRestServiceClient implements JenkinsService{

	private Client client;
	private Resources resources;

	public JenkinsRestServiceClient(Client client, Resources resources) {
		this.client = client;
		this.resources = resources;
	}

	public BuildInfo getCurrentBuild() {
		WebTarget target = client.target(resources.text("/url.txt"));
		return target.request().get(BuildInfo.class);
	}
	
}
