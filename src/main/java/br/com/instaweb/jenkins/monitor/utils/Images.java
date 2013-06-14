package br.com.instaweb.jenkins.monitor.utils;

import static com.google.common.base.Preconditions.checkArgument;

import java.net.URL;

import javax.swing.ImageIcon;

public class Images {

	public static ImageIcon loadImageIcon(String path) {
		URL url = Images.class.getResource(path);
		checkArgument(url != null, "Icon '%s' not found!", path);
		return new ImageIcon(url, "icon");
	}

}
