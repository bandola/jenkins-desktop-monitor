package br.com.instaweb.jenkins.monitor.ui;

import java.awt.Desktop;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.swing.SwingUtilities;

import br.com.instaweb.jenkins.monitor.bean.JenkinsJob;
import br.com.instaweb.jenkins.monitor.service.JenkinsPoller;
import br.com.instaweb.jenkins.monitor.service.JenkinsService;
import br.com.instaweb.jenkins.monitor.tasks.status.BuildErrorEvent;
import br.com.instaweb.jenkins.monitor.tasks.status.BuildStateChangedEvent;
import br.com.instaweb.jenkins.monitor.ui.tray.Icon;
import br.com.instaweb.jenkins.monitor.ui.tray.TrayManager;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

public class JenkinsMonitor{
	
	private TrayManager manager;
	private EventBus eventBus;
	private TrayIcon icon;
	private JenkinsPoller poller;
	
	@Inject
	public JenkinsMonitor(TrayManager manager, JenkinsService service, JenkinsPoller poller, EventBus eventBus){
		this.manager = manager;
		this.eventBus = eventBus;
		this.poller = poller;
		init();
	} 
	
	private void init(){
		icon = manager.createTrayIcon(Icon.LOADING);
		SwingUtilities.invokeLater(poller);
		eventBus.register(new TrayIconUpdater());
		eventBus.register(new BuildNotifier());
	}
	
	class TrayIconUpdater{
		 @Subscribe 
		 public void buildStatusChanged(BuildStateChangedEvent event) {
			manager.replaceTrayIcon(icon, event.getCurrent().getIcon());
		 }
	}

	class BuildNotifier{
		@Subscribe 
		public void buildError(BuildErrorEvent event) {
			final JenkinsJob currentJob = event.getCurent();
			String title = String.format("Build #%s Failed", currentJob.lastBuildNumber());
			
			icon.displayMessage(title, "Click here to see the build log", MessageType.INFO);
			icon.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					openWebpage(currentJob.getLastBuild().getUrl());
				}
			});
			
		}
	}
	
	public static void openWebpage(URI uri) throws IOException {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            desktop.browse(uri);
	    }
	}

	public static void openWebpage(URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (Exception e) {
	        throw new IllegalArgumentException("Could not open the url " + url, e);
	    }
	}

}
