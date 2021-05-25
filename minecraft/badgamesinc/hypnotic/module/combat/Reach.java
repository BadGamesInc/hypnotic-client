package badgamesinc.hypnotic.module.combat;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;

public class Reach extends Mod
{
    public NumberSetting reach = new NumberSetting("Reach", 4, 0, 6, 0.1);
    
    public Reach() {
        super("Reach", 0, Category.COMBAT, "Give yourself extended reach");
        addSettings(reach);
    }
}
