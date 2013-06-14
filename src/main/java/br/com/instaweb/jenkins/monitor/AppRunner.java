package br.com.instaweb.jenkins.monitor;

import static javax.swing.SwingUtilities.invokeLater;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.jackson.JacksonFeature;

import br.com.instaweb.jenkins.monitor.bean.BeanFactory;
import br.com.instaweb.jenkins.monitor.service.JenkinsRestServiceClient;
import br.com.instaweb.jenkins.monitor.tasks.status.CheckStatusTask;
import br.com.instaweb.jenkins.monitor.ui.JenkinsMonitor;
import br.com.instaweb.jenkins.monitor.ui.tray.AWTTrayManager;
import br.com.instaweb.jenkins.monitor.utils.Resources;

import com.google.common.eventbus.EventBus;

public class AppRunner {
	
	public static void main(String... args) throws Exception {
		registerBeans();
		invokeLater(startJenkinsMonitor());
	}
 
	private static void registerBeans() {
		BeanFactory.register(AWTTrayManager.class);
		BeanFactory.register(Resources.class);
		BeanFactory.register(CheckStatusTask.class);
		BeanFactory.register(JenkinsRestServiceClient.class);
		BeanFactory.register(JenkinsMonitor.class);
		BeanFactory.register(EventBus.class);
		
		Client client = ClientBuilder.newBuilder()
				.register(JacksonFeature.class)
				.build();	
		BeanFactory.register(client);
	}
	
	private static Runnable startJenkinsMonitor() {
		return new Runnable() {
			@Override
			public void run() {
				BeanFactory.getBean(JenkinsMonitor.class);
			}
		};
	}
	
}
