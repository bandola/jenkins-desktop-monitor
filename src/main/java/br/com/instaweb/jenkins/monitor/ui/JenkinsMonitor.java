package br.com.instaweb.jenkins.monitor.ui;

import java.awt.TrayIcon;

import javax.swing.SwingUtilities;

import br.com.instaweb.jenkins.monitor.service.JenkinsPoller;
import br.com.instaweb.jenkins.monitor.service.JenkinsService;
import br.com.instaweb.jenkins.monitor.tasks.status.BuildErrorNotifier;
import br.com.instaweb.jenkins.monitor.tasks.status.TrayIconUpdater;
import br.com.instaweb.jenkins.monitor.ui.tray.Icon;
import br.com.instaweb.jenkins.monitor.ui.tray.TrayManager;

import com.google.common.eventbus.EventBus;
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
		eventBus.register(new TrayIconUpdater(manager, icon));
		eventBus.register(new BuildErrorNotifier(icon));
	}
	
}
