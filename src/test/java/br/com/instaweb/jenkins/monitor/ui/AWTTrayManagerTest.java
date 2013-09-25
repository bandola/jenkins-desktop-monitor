package br.com.instaweb.jenkins.monitor.ui;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;

import br.com.instaweb.jenkins.monitor.service.JenkinsService;
import br.com.instaweb.jenkins.monitor.ui.tray.AWTTrayManager;
import br.com.instaweb.jenkins.monitor.ui.tray.Icon;

public class AWTTrayManagerTest {

	private static final ActionEvent IRRELEVANT = null;
	private AWTTrayManager manager;
	private JenkinsService service;
	
	@Before
	public void setUp(){
		service = mock(JenkinsService.class);
		manager = new AWTTrayManager(service, new EventBus());
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
	
	@Test
	public void canBuild() throws Exception {
		TrayIcon icon = manager.createTrayIcon(Icon.GREEN);
		PopupMenu menu = icon.getPopupMenu();

		verify(service, never()).build();
		clickMenuItem(getMenuItem(menu, "build"));
		verify(service, times(1)).build();
		
	}

	@Test
	public void canEnableBuild() throws Exception {
		TrayIcon icon = manager.createTrayIcon(Icon.GREEN);
		PopupMenu menu = icon.getPopupMenu();
		
		verify(service, never()).enableBuild();
		clickMenuItem(getMenuItem(menu, "enable-build"));
		verify(service, times(1)).enableBuild();
		
	}

	@Test
	public void canDisableBuild() throws Exception {
		TrayIcon icon = manager.createTrayIcon(Icon.GREEN);
		PopupMenu menu = icon.getPopupMenu();
		
		verify(service, never()).disableBuild();
		clickMenuItem(getMenuItem(menu, "disable-build"));
		verify(service, times(1)).disableBuild();
		
	}

	private MenuItem getMenuItem(PopupMenu menu, String label){
		for(int i = 0; i < menu.getItemCount(); i++){
			MenuItem item = menu.getItem(i);
			if(label.equals(item.getLabel())){
				return item;
			}
		}
		fail(String.format("MenuItem not found with label %s", label));
		return null;
	}
	
	private void clickMenuItem(MenuItem item){
		ActionListener[] listeners = item.getActionListeners();
		for(ActionListener listener : listeners){
			listener.actionPerformed(IRRELEVANT);
		}
	}
	
}
