package br.com.instaweb.jenkins.monitor.ui.tray;

import java.awt.TrayIcon;


public interface TrayManager {

	TrayIcon createTrayIcon(Icon icon);
	void replaceTrayIcon(TrayIcon icon, Icon replacement);
	 
}
