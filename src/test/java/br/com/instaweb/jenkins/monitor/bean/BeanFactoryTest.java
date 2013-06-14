package br.com.instaweb.jenkins.monitor.bean;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

import org.junit.Test;

import br.com.instaweb.jenkins.monitor.ui.tray.AWTTrayManager;
import br.com.instaweb.jenkins.monitor.ui.tray.TrayManager;

public class BeanFactoryTest {

	@Test
	public void canRegisterBean() throws Exception {
		assertThat("Bean should not exist before it was registered", BeanFactory.getBean(TrayManager.class), nullValue());
		BeanFactory.register(new AWTTrayManager());
		assertThat("Bean should exist after it was registered", BeanFactory.getBean(TrayManager.class), notNullValue());
	}

}
