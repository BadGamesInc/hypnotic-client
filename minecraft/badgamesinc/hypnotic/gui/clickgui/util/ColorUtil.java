package badgamesinc.hypnotic.gui.clickgui.util;

import java.awt.Color;

import badgamesinc.hypnotic.Hypnotic;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ColorUtil {
	
	public static Color getClickGUIColor(){
		return new Color((int)Hypnotic.instance.setmgr.getSettingByName("GuiRed").getValDouble(), (int)Hypnotic.instance.setmgr.getSettingByName("GuiGreen").getValDouble(), (int)Hypnotic.instance.setmgr.getSettingByName("GuiBlue").getValDouble());
	}
}
