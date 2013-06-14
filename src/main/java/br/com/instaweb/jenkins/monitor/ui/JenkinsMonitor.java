package br.com.instaweb.jenkins.monitor.ui;

import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;

import javax.swing.SwingUtilities;

import br.com.instaweb.jenkins.monitor.bean.BeanFactory;
import br.com.instaweb.jenkins.monitor.service.JenkinsService;
import br.com.instaweb.jenkins.monitor.tasks.status.BuildStateChangedEvent;
import br.com.instaweb.jenkins.monitor.tasks.status.CheckStatusTask;
import br.com.instaweb.jenkins.monitor.ui.tray.Icon;
import br.com.instaweb.jenkins.monitor.ui.tray.TrayManager;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class JenkinsMonitor {

	private static int FIVE_SECONDS = 5 * 1000;
	private TrayManager manager;
	private EventBus eventBus;
	private TrayIcon icon;
	private JenkinsService service;
	
	public JenkinsMonitor(TrayManager manager, JenkinsService service, EventBus eventBus){
		this.manager = manager;
		this.service = service;
		this.eventBus = eventBus;
		init();
	} 
	
	private void init(){
		icon = manager.createTrayIcon(Icon.LOADING);
		SwingUtilities.invokeLater(new JenkinsPoller());
		icon.addMouseListener(new TrayIconMouseListener());
		eventBus.register(new TrayIconUpdater());
	}
	
	private class JenkinsPoller implements Runnable{
		@Override
		public void run() {
			JenkinsService service = JenkinsMonitor.this.service;
			TrayIcon icon = JenkinsMonitor.this.icon;
			JenkinsMonitor.this.manager.replaceTrayIcon(icon, service.getCurrentBuild().icon());
			Timer timer = new java.util.Timer(getClass().getSimpleName());
			timer.scheduleAtFixedRate(BeanFactory.getBean(CheckStatusTask.class), 0, FIVE_SECONDS);
		}
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
