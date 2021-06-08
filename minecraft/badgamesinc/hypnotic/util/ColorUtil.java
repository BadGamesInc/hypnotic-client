package badgamesinc.hypnotic.util;

import java.awt.Color;

import badgamesinc.hypnotic.Hypnotic;

public class ColorUtil {
	
	public static Color getClickGUIColor(){
		return new Color((int)Hypnotic.instance.moduleManager.clickGui.red.getValue(), (int)Hypnotic.instance.moduleManager.clickGui.green.getValue(), (int)Hypnotic.instance.moduleManager.clickGui.blue.getValue());
	}
}
