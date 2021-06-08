package badgamesinc.hypnotic.gui.notifications;

public enum Type {
	
	INFO("info"),
	WARNING("warning"),
	DEBUG("debug"),
	CHECK("check"),
	X("x");
	
	public String filePrefix;
	
	Type(String filePrefix) {
		
		this.filePrefix = filePrefix;
		
	}
	
}
