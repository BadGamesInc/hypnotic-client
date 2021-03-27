package badgamesinc.hypnotic.module.render;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class ItemCustomization extends Mod{

	public ItemCustomization() {
		super("Item Customize", 0, Category.RENDER, "Change how the item you are holding looks");
	}
	
	@Override
	public void setup() {
		Hypnotic.instance.setmgr.rSetting(new Setting("Hand Speed", this, 1, 1, 12, false));
		Hypnotic.instance.setmgr.rSetting(new Setting("Item X", this, 52, 1, 100, false));
		Hypnotic.instance.setmgr.rSetting(new Setting("Item Y", this, 45, 1, 100, false));
		Hypnotic.instance.setmgr.rSetting(new Setting("Item Size", this, 38, 1, 100, false));
	}
	
	@Override
	public void onEnable() {
		this.toggle();
	}

}
