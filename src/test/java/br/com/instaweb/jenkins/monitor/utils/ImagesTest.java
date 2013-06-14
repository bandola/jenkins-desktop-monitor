package br.com.instaweb.jenkins.monitor.utils;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import org.junit.Test;

import br.com.instaweb.jenkins.monitor.ui.tray.Icon;

public class ImagesTest {

	@Test
	public void loadIcons() throws Exception {
		for (Icon icon : Icon.values()) {
			assertThat(String.format("Could not load icon with path: '%s'", icon.getFilePath()),
					Images.loadImageIcon(icon.getFilePath()), notNullValue());
		}
	}
}
