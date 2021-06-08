package badgamesinc.hypnotic.module.render;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;

public class ItemCustomization extends Mod{

	public NumberSetting handspeed = new NumberSetting("Hand Speed", 1, 1, 12, 0.1);
    public NumberSetting swordXValue = new NumberSetting("Sword X", 52, 1, 100, 1);
    public NumberSetting swordYValue = new NumberSetting("Sword Y", 56, 1, 100, 1);
    public NumberSetting itemSize = new NumberSetting("Item Size", 40, 1, 100, 1);
    public BooleanSetting weirdHit = new BooleanSetting("Weird Hit", false);
    
	public ItemCustomization() {
		super("Item Customize", 0, Category.RENDER, "Change how the item you are holding looks");
		addSettings(handspeed, swordXValue, swordYValue, itemSize, weirdHit);
	}
	
	@Override
	public void onEnable() {
		this.toggle();
	}

}
