package br.com.instaweb.jenkins.monitor.bean;

import java.net.URL;

public class Build {

	private int number;
	private URL url;

	public int getNumber() {
		return number;
	}

	public URL getUrl() {
		return url;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
}
