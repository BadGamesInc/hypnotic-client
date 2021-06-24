package badgamesinc.hypnotic.util;

public class Logger {

	public static void log(String message, String thingsayingthestuff, LogType type) {
		if (thingsayingthestuff == "") {
			thingsayingthestuff = "Hypnotic";
		}
		System.out.println("[" + thingsayingthestuff + "] " + type + ": " + message);
	}
	
	public static void log(String message, String thingsayingthestuff) {
		if (thingsayingthestuff == "") {
			thingsayingthestuff = "Hypnotic";
		}
		System.out.println("[" + thingsayingthestuff + "] " + message);
	}
	
	public static void log(String message, LogType type) {
		System.out.println("[Hypnotic] " + type + ": " + message);
	}
	
	public static void log(String message) {
		System.out.println("[Hypnotic] " + message);
	}
	
	public enum LogType {
		INFO, WARNING
	}
}
