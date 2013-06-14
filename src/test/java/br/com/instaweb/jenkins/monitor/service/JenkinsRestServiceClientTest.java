package br.com.instaweb.jenkins.monitor.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.instaweb.jenkins.monitor.utils.Resources;

public class JenkinsRestServiceClientTest {

	private JenkinsRestServiceClient service;
	private Client client;
	private WebTarget target; 
	private Resources resources;
	private Builder request;
	
	@Before
	public void setUp(){
		client = mock(Client.class);
		target = mock(WebTarget.class);
		request = mock(Builder.class);
		when(client.target(Mockito.anyString())).thenReturn(target);
		when(target.request()).thenReturn(request);
		resources = mock(Resources.class);
		service = new JenkinsRestServiceClient(client, resources);
	}
	
	@Test
	public void getCurrentBuildInfo() throws Exception {
		service.getCurrentBuild();
		verify(request, times(1)).get((Class<?>) Mockito.any());
		
	}
	
	
}
