package br.com.instaweb.jenkins.monitor.bean;

import static com.google.common.base.Preconditions.checkNotNull;
import br.com.instaweb.jenkins.monitor.ui.tray.Icon;

public enum BuildState {

	disabled(Icon.GRAY),
	aborted(Icon.GRAY),
	aborted_anime(Icon.GRAY_ANIMATED),
	blue(Icon.GREEN),
	blue_anime(Icon.GREEN_ANIMATED),
	red(Icon.RED),
	red_anime(Icon.RED_ANIMATED),
	yellow(Icon.YELLOW),
	yellow_anime(Icon.YELLOW_ANIMATED);
	
	private Icon icon;

	private BuildState(Icon icon){
		checkNotNull(icon, "BuildState icon cannot be null");
		this.icon = icon;
	}
	
	public Icon getIcon() {
		return icon;
	}
}
