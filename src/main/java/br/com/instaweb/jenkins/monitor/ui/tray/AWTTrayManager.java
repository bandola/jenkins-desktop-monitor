package br.com.instaweb.jenkins.monitor.ui.tray;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.instaweb.jenkins.monitor.service.JenkinsService;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;


public class AWTTrayManager implements TrayManager {
	
	private JenkinsService service;
	private EventBus eventBus;
	
	@Inject
	public AWTTrayManager(JenkinsService service, EventBus eventBus){
		this.service = service;
		this.eventBus = eventBus;
	}
	
	@Override
	public TrayIcon createTrayIcon(Icon icon) {
		try {
			SystemTray tray = SystemTray.getSystemTray();
			TrayIcon trayIcon = new TrayIcon(icon.getImage());
			trayIcon.setPopupMenu(createPopupMenu());
			tray.add(trayIcon);
			return trayIcon;
		} catch (Exception e) {
			throw new RuntimeException("Could not create Trayicon");
		}
	}


	@Override
	public void replaceTrayIcon(TrayIcon icon, Icon replacement) {
		checkNotNull(icon, "TrayIcon was not provided.");
		checkNotNull(replacement, "no icon replacement provided");
		icon.setImage(replacement.getImage());
	}
	
	private PopupMenu createPopupMenu() {
		PopupMenu menu = new PopupMenu(); 
		menu.add(createMenuItem("disable-build", disableBuildAction()));
		menu.add(createMenuItem("enable-build", enableBuildAction()));
		menu.add(new MenuItem("------"));
		menu.add(createMenuItem("build", buildAction()));
		menu.add(createMenuItem("exit", exitAction()));
		return menu;
	}
	
	private ActionListener exitAction() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
	}

	private ActionListener buildAction() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				service.build();
			}
		};
	}

	private ActionListener enableBuildAction() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				service.enableBuild();
			}
		};
	}

	private ActionListener disableBuildAction(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				service.disableBuild();
			}
		};
	}
	
	private MenuItem createMenuItem(String label, ActionListener listener){
		MenuItem menuItem = new MenuItem(label);
		menuItem.addActionListener(listener);
		return menuItem;
	}
}
