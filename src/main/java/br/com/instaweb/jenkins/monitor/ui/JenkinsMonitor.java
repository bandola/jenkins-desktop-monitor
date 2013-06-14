package br.com.instaweb.jenkins.monitor.ui;

import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import br.com.instaweb.jenkins.monitor.service.JenkinsPoller;
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
	public JenkinsMonitor(TrayManager manager, JenkinsPoller poller, EventBus eventBus){
		this.manager = manager;
		this.eventBus = eventBus;
		this.poller = poller;
		init();
	} 
	
	private void init(){
		icon = manager.createTrayIcon(Icon.LOADING);
		SwingUtilities.invokeLater(poller);
		icon.addMouseListener(new TrayIconMouseListener());
		eventBus.register(new TrayIconUpdater());
	}
	
	private static class TrayIconMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() >= 2){
				System.exit(0);
			}
		}
	}

	class TrayIconUpdater{
		 @Subscribe 
		 public void buildStatusChanged(BuildStateChangedEvent event) {
			icon.displayMessage(event.getCurrentResult().name(), "Build status changed from " + event.getPrevious() + " to " + event.getCurrentResult(), MessageType.INFO);
			manager.replaceTrayIcon(icon, event.getCurrentResult().getIcon());
		 }
	}

}
