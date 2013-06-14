package br.com.instaweb.jenkins.monitor;

import static com.google.inject.Scopes.SINGLETON;

import java.util.TimerTask;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.jackson.JacksonFeature;

import br.com.instaweb.jenkins.monitor.service.JenkinsPollerTask;
import br.com.instaweb.jenkins.monitor.service.JenkinsRestServiceClient;
import br.com.instaweb.jenkins.monitor.service.JenkinsService;
import br.com.instaweb.jenkins.monitor.tasks.status.CheckStatusTask;
import br.com.instaweb.jenkins.monitor.ui.JenkinsMonitor;
import br.com.instaweb.jenkins.monitor.ui.tray.AWTTrayManager;
import br.com.instaweb.jenkins.monitor.ui.tray.TrayManager;
import br.com.instaweb.jenkins.monitor.utils.Resources;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
    	
		Client client = ClientBuilder.newBuilder()
				.register(JacksonFeature.class)
				.build();	
		
        bind(TrayManager.class).to(AWTTrayManager.class).in(SINGLETON);
        bind(Resources.class).in(SINGLETON);
        bind(CheckStatusTask.class).in(SINGLETON);
        bind(JenkinsService.class).to(JenkinsRestServiceClient.class).in(SINGLETON);
        bind(JenkinsMonitor.class).in(SINGLETON);
        bind(TimerTask.class).annotatedWith(JenkinsPollerTask.class).to(CheckStatusTask.class);
        bind(EventBus.class).in(SINGLETON);
        bind(Client.class).toInstance(client);
    }

}
