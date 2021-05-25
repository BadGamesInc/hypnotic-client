package badgamesinc.hypnotic.module.render;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;

public class BlockAnimations extends Mod {

	public ModeSetting animations = new ModeSetting("Animation", "Slide", "1.8", "1.7", "Slap", "Spin", "Stab", "Slide", "Sigma", "Leaked", "Astolfo", "Exhibition");
	
	public BlockAnimations() {
		super("BlockAnimations", 0, Category.RENDER, "Change how it looks when you block hit");
		addSettings(animations);
	}
	
	@Override
	public void onEnable() {
		this.toggle();
	}

}
