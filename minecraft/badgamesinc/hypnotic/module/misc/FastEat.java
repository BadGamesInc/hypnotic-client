package badgamesinc.hypnotic.module.misc;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastEat extends Mod {

	public FastEat() {
		super("FastEat", 0, Category.MISC, "Eat things insanely fast");
		Hypnotic.instance.setmgr.rSetting(new Setting("Packets per second", this, 10, 1, 100, true));
	}
    
    @Override
    public void onUpdate() {
        if (mc.thePlayer.getHealth() > 0.0f && mc.thePlayer.onGround && mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood && mc.gameSettings.keyBindUseItem.pressed) {
            for (int i = 0; i < 70; ++i) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
            }
        }
    }
}
