package br.com.instaweb.jenkins.monitor.tasks.status;

import java.awt.TrayIcon;

import br.com.instaweb.jenkins.monitor.events.BuildStateChangedEvent;
import br.com.instaweb.jenkins.monitor.ui.tray.TrayManager;

import com.google.common.eventbus.Subscribe;

public class TrayIconUpdater {

	private TrayIcon icon;
	private TrayManager manager;
	
	public TrayIconUpdater(TrayManager manager, TrayIcon icon){
		this.manager = manager;
		this.icon = icon;
	}
	
	 @Subscribe 
	 public void buildStatusChanged(BuildStateChangedEvent event) {
		manager.replaceTrayIcon(icon, event.getCurrent().getIcon());
	 }
}
