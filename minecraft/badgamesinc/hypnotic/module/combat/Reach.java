package badgamesinc.hypnotic.module.combat;

import com.ibm.icu.text.DecimalFormat;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;

public class Reach extends Mod
{
    public static int reach;
    
    public Reach() {
        super("Reach", 0, Category.COMBAT, "Give yourself extended reach");
    }
    
    @Override
    public void setup() {
        Hypnotic.instance.setmgr.rSetting(new Setting("Reach", this, 4.0, 4.0, 6.0, false));
    }
    
    public static double getReach() {
        return Hypnotic.instance.setmgr.getSettingByName("Reach").getValDouble();
    }
    

}
