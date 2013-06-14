package br.com.instaweb.jenkins.monitor;

import static javax.swing.SwingUtilities.invokeLater;
import br.com.instaweb.jenkins.monitor.ui.JenkinsMonitor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mycila.inject.jsr250.Jsr250;

public class AppRunner {
	
	private static Injector injector; 
	public static void main(String... args) throws Exception {
		injector = Guice.createInjector(new Module(), Jsr250.newJsr250Module());
		invokeLater(startJenkinsMonitor());
	}
 
	private static Runnable startJenkinsMonitor() {
		return new Runnable() {
			@Override
			public void run() {
				injector.getInstance(JenkinsMonitor.class);
			}
		};
	}
	
}
