package br.com.instaweb.jenkins.monitor.ui.tray;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.SystemTray;
import java.awt.TrayIcon;


public class AWTTrayManager implements TrayManager {
	
	@Override
	public TrayIcon createTrayIcon(Icon icon) {
		try {
			SystemTray tray = SystemTray.getSystemTray();
			TrayIcon trayIcon = new TrayIcon(icon.getImage());
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
}
