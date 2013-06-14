package br.com.instaweb.jenkins.monitor.ui;

import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;

import br.com.instaweb.jenkins.monitor.bean.BeanFactory;
import br.com.instaweb.jenkins.monitor.service.JenkinsService;
import br.com.instaweb.jenkins.monitor.tasks.status.BuildStateChangedEvent;
import br.com.instaweb.jenkins.monitor.tasks.status.CheckStatusTask;
import br.com.instaweb.jenkins.monitor.ui.tray.TrayManager;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class JenkinsMonitor {

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
		icon = manager.createTrayIcon(service.getCurrentBuild().icon());
		icon.addMouseListener(new TrayIconMouseListener());
		eventBus.register(new TrayIconUpdater());
		Timer timer = new java.util.Timer(getClass().getSimpleName());
		timer.scheduleAtFixedRate(BeanFactory.getBean(CheckStatusTask.class), 0, 5000);
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
			manager.replaceTrayIcon(icon, event.getCurrentResult().getIcon());
		 }
	}
	

}
