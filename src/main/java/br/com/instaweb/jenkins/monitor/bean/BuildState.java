package br.com.instaweb.jenkins.monitor.bean;

import static com.google.common.base.Preconditions.checkNotNull;
import br.com.instaweb.jenkins.monitor.ui.tray.Icon;

public enum BuildState {

	unknown(Icon.LOADING),
	disabled(Icon.GRAY),
	aborted(Icon.GRAY),
	aborted_anime(Icon.GRAY_ANIMATED){
		@Override
		public boolean isBuilding() {
			return true;
		}
	},
	blue(Icon.GREEN),
	blue_anime(Icon.GREEN_ANIMATED){
		
		@Override
		public boolean isBuilding() {
			return true;
		}
	},
	
	red(Icon.RED),
	red_anime(Icon.RED_ANIMATED){
		@Override
		public boolean isBuilding() {
			return true;
		}
	},

	yellow(Icon.YELLOW),
	yellow_anime(Icon.YELLOW_ANIMATED){
	
		@Override
		public boolean isBuilding() {
			return true;
		}
	};
	
	private Icon icon;
	
	private BuildState(Icon icon){
		checkNotNull(icon, "BuildState icon cannot be null");
		this.icon = icon;
	}
	
	public Icon getIcon() {
		return icon;
	}
	
	public boolean isBuilding(){
		return false;
	}
}
