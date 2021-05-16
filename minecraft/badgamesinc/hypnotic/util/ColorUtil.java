package badgamesinc.hypnotic.util;

import java.awt.Color;

import badgamesinc.hypnotic.Hypnotic;

public class ColorUtil {
	
	public static Color getClickGUIColor(){
		return new Color((int)Hypnotic.instance.setmgr.getSettingByName("GuiRed").getValDouble(), (int)Hypnotic.instance.setmgr.getSettingByName("GuiGreen").getValDouble(), (int)Hypnotic.instance.setmgr.getSettingByName("GuiBlue").getValDouble());
	}
}
