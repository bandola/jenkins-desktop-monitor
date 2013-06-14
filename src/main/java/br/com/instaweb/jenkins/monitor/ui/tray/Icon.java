package br.com.instaweb.jenkins.monitor.ui.tray;

import java.awt.Image;

import br.com.instaweb.jenkins.monitor.utils.Images;

public enum Icon {

	GREEN("/green.png"),
	GREEN_ANIMATED("/green_anime.gif"),
	RED("/red.gif"),
	RED_ANIMATED("/red_anime.gif"),
	UNKONWN("/gray.png"), 
	GRAY("/gray.png"), 
	GRAY_ANIMATED("/gray_anime.gif"),
	YELLOW("/yellow.png"),
	YELLOW_ANIMATED("/yellow_anime.gif");
	
	private String filePath;
	private Image image;
	
	private Icon(String filePath){
		this.filePath = filePath;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public Image getImage(){
		if(this.image == null){
			this.image = Images.loadImageIcon(filePath).getImage();
		}
		return this.image;
	}

	public static Icon fromColor(String upperCase) {
		return null;
	}
}
