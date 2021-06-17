package badgamesinc.hypnotic.module.misc;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.Wrapper;

public class AntiBan extends Mod {

	public AntiBan() {
		super("AntiBan", 0, Category.MISC, "100% Working no scam");
	}
	
	@Override
	public void onEnable() {
		Wrapper.rawTellPlayer(ColorUtils.gray + "[" + ColorUtils.yellow + "Summer" + ColorUtils.gray + "]" + ColorUtils.white + " : DO NOT USE ANTIBAN ON HYPIXEL");
		this.toggle();
	}

}
