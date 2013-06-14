package br.com.instaweb.jenkins.monitor.ui;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.awt.TrayIcon;

import org.junit.Before;
import org.junit.Test;

import br.com.instaweb.jenkins.monitor.ui.tray.AWTTrayManager;
import br.com.instaweb.jenkins.monitor.ui.tray.Icon;

public class AWTTrayManagerTest {

	private AWTTrayManager manager;
	
	@Before
	public void setUp(){
		manager = new AWTTrayManager();
	}
	
	@Test
	public void canCreateTrayIcon() throws Exception {
		TrayIcon icon = manager.createTrayIcon(Icon.GREEN_ANIMATED);
		assertThat("TrayIcon should be created", icon, notNullValue());
		assertThat("Icon image was not created correctly", icon.getImage(), equalTo(Icon.GREEN_ANIMATED.getImage()));
	}
	
	@Test
	public void createTrayIconAndReplaceImage() throws Exception {
		TrayIcon icon = manager.createTrayIcon(Icon.RED);
		assertThat("Icon image was not created correctly", icon.getImage(), equalTo(Icon.RED.getImage()));
		manager.replaceTrayIcon(icon, Icon.RED_ANIMATED);
		assertThat("Icon was not replaced properly", icon.getImage(), equalTo(Icon.RED_ANIMATED.getImage()));
	}
	
}
