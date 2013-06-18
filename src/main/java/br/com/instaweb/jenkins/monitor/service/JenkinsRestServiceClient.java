package br.com.instaweb.jenkins.monitor.service;

import static com.google.common.base.Preconditions.checkState;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import br.com.instaweb.jenkins.monitor.bean.JenkinsJob;
import br.com.instaweb.jenkins.monitor.utils.Resources;

import com.google.inject.Inject;

public class JenkinsRestServiceClient implements JenkinsService{

	private Client client;
	private final String statusUrl;
	private final String buildUrl;
	private final String enableUrl;
	private final String disableUrl;

	@Inject
	public JenkinsRestServiceClient(Client client, Resources resources) {
		this.client = client;
		this.statusUrl = resources.text("/status.txt");
		this.buildUrl = resources.text("/build.txt");
		this.enableUrl = resources.text("/enable.txt");
		this.disableUrl = resources.text("/disable.txt");
	}

	public JenkinsJob getCurrentBuild() {
		WebTarget target = client.target(statusUrl);
		JenkinsJob info = target.request().get(JenkinsJob.class);
		return info;
	}

	@Override
	public void disableBuild() {
		WebTarget target = client.target(disableUrl);
		Response response = target.request().post(null);
		checkState(response.getStatusInfo().getFamily() == Family.SUCCESSFUL, "Could not disable build.");
	}

	@Override
	public void enableBuild() {
		WebTarget target = client.target(enableUrl);
		Response response = target.request().post(null);
		checkState(response.getStatusInfo().getFamily() == Family.SUCCESSFUL, "Could not enable build.");
	}

	@Override
	public void build() {
		WebTarget target = client.target(buildUrl);
		Response response = target.request().post(null);
		checkState(response.getStatusInfo().getFamily() == Family.SUCCESSFUL, "Could not build.");
	}
}
