package br.com.instaweb.jenkins.monitor.tasks.status;

import java.awt.Desktop;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import br.com.instaweb.jenkins.monitor.bean.JenkinsJob;
import br.com.instaweb.jenkins.monitor.events.BuildErrorEvent;

import com.google.common.eventbus.Subscribe;

public class BuildErrorNotifier {

	private TrayIcon icon;
	
	public BuildErrorNotifier(TrayIcon icon){
		this.icon = icon;
	}
	
	@Subscribe 
	public void buildError(BuildErrorEvent event) {
		final JenkinsJob currentJob = event.getCurent();
		String title = String.format("Build #%s Failed", currentJob.lastBuildNumber());
		
		icon.displayMessage(title, "Click here to see the build log", MessageType.INFO);
		icon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openWebpage(currentJob.getLastBuild().getUrl());
			}
		});
	}
	
	class BuildNotifier{
		@Subscribe 
		public void buildError(BuildErrorEvent event) {
			final JenkinsJob currentJob = event.getCurent();
			String title = String.format("Build #%s Failed", currentJob.lastBuildNumber());
			
			icon.displayMessage(title, "Click here to see the build log", MessageType.INFO);
			icon.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					openWebpage(currentJob.getLastBuild().getUrl());
				}
			});
		}
	}
	
	public static void openWebpage(URI uri) throws IOException {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            desktop.browse(uri);
	    }
	}

	public static void openWebpage(URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (Exception e) {
	        throw new IllegalArgumentException("Could not open the url " + url, e);
	    }
	}
}
