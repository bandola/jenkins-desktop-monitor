package br.com.instaweb.jenkins.monitor.tasks.status;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import br.com.instaweb.jenkins.monitor.tasks.Task;

import com.google.common.base.Charsets;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.mycila.inject.internal.guava.io.Files;

public class PullRequestsTask implements Task{

	private EventBus eventBus;
	private Client client;
	
	@Inject
	public PullRequestsTask(EventBus eventBus, Client client) {
		this.eventBus = eventBus;
		this.client = client;
	}

	@Override
	public void run() {
		try {
			System.out.println("pull request task");
			List<String> auth = Files.readLines(new File("C:\\tmp\\github-auth.txt"), Charsets.UTF_8);
			WebTarget target = client.target(auth.get(0));
			Response response = target.request().header("Authorization", auth.get(1)).get();
			String responseString = response.readEntity(String.class);
			eventBus.post(new PullRequestEvent(!responseString.equals("[]")));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
}
