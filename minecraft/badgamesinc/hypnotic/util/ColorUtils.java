package badgamesinc.hypnotic.util;

import java.awt.Color;

public class ColorUtils {

	public static String colorChar = "§";
	public static String purple = "§5";
	public static String red = "§c";
	public static String aqua = "§b";
	public static String green = "§a";
	public static String blue = "§9";
	public static String darkGreen = "§2";
	public static String darkBlue = "§1";
	public static String black = "§0";
	public static String darkRed = "§4";
	public static String darkAqua = "§3";
	public static String lightPurple = "§d";
	public static String yellow = "§e";
	public static String white = "§f";
	public static String gray = "§7";
	public static String darkGray = "§8";
	public static String reset = "§r";
	
	public static int rainbow(float seconds, float saturation, float brigtness) {
		float hue = (System.currentTimeMillis() % (int) (seconds * 1000)) / (float) (seconds * 1000);
		int color = Color.HSBtoRGB(hue, saturation, 1);
		return color;
	}
	
	public static int rainbow(float seconds, float saturation, float brigtness, long index) {
		float hue = ((System.currentTimeMillis() + index) % (int) (seconds * 1000)) / (float) (seconds * 1000);
		int color = Color.HSBtoRGB(hue, saturation, 1);
		return color;
	}
	
	public static Color fade(Color color, int index, int count) {
	      float[] hsb = new float[3];
	      Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
	      float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + (float)index / (float)count * 2.0F) % 2.0F - 1.0F);
	      brightness = 0.5F + 0.5F * brightness;
	      hsb[2] = brightness % 2.0F;
	      return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
	 }
		
}
