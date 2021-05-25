package badgamesinc.hypnotic.util;

import java.awt.Color;

import badgamesinc.hypnotic.Hypnotic;

public class ColorUtil {
	
	public static Color getClickGUIColor(){
		return new Color((int)Hypnotic.instance.moduleManager.clickGui.guiRed.getValue(), (int)Hypnotic.instance.moduleManager.clickGui.guiGreen.getValue(), (int)Hypnotic.instance.moduleManager.clickGui.guiBlue.getValue());
	}
}
