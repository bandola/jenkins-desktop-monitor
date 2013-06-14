package br.com.instaweb.jenkins.monitor.ui;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import br.com.instaweb.jenkins.monitor.service.JenkinsPoller;
import br.com.instaweb.jenkins.monitor.service.JenkinsService;
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
	private JenkinsService service;
	
	@Inject
	public JenkinsMonitor(TrayManager manager, JenkinsService service, JenkinsPoller poller, EventBus eventBus){
		this.manager = manager;
		this.eventBus = eventBus;
		this.poller = poller;
		this.service = service;
		init();
	} 
	
	private void init(){
		icon = manager.createTrayIcon(Icon.LOADING);
		SwingUtilities.invokeLater(poller);
		icon.addMouseListener(new TrayIconMouseListener());
		eventBus.register(new TrayIconUpdater());
		
		PopupMenu menu = new PopupMenu(); 
		MenuItem disableBuild = new MenuItem("disable-build");
		disableBuild.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				service.disableBuild();
			}
		});

		MenuItem enable = new MenuItem("enable-build");
		enable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				service.enableBuild();
			}
		});
		
		MenuItem build = new MenuItem("build");
		build.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				service.build();
			}
		});

		menu.add(disableBuild);
		menu.add(enable);
		menu.add(new MenuItem("------"));
		menu.add(build);
		icon.setPopupMenu(menu);
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
