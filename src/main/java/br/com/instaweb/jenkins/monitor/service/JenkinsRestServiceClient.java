package br.com.instaweb.jenkins.monitor.service;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import br.com.instaweb.jenkins.monitor.bean.BuildInfo;
import br.com.instaweb.jenkins.monitor.utils.Resources;

public class JenkinsRestServiceClient implements JenkinsService{

	private Client client;
	private final String url;
	private static Logger logger = Logger.getLogger(JenkinsRestServiceClient.class.getName());

	public JenkinsRestServiceClient(Client client, Resources resources) {
		this.client = client;
		this.url = resources.text("/url.txt");
	}

	public BuildInfo getCurrentBuild() {
		logger.info(String.format("Starting request to %s", url));
		WebTarget target = client.target(url);
		BuildInfo info = target.request().get(BuildInfo.class);
		logger.info(String.format("Request completed. Result: %s", info));
		return info;
	}
	
}
