package badgamesinc.hypnotic.module.render;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;

public class BlockAnimations extends Mod {

	public BlockAnimations() {
		super("BlockAnimations", 0, Category.RENDER, "Change how it looks when you block hit");
	}
	
	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("1.8");
		options.add("1.7");
		options.add("Slap");
		options.add("Spin");
		options.add("Stab");
		options.add("Slide");
		options.add("Sigma");
		options.add("Leaked");	
		options.add("Astolfo");
		options.add("Exhibition");
		Hypnotic.instance.setmgr.rSetting(new Setting("Block mode", this, "Exhibition", options));
	}
	
	@Override
	public void onEnable() {
		this.toggle();
	}

}
