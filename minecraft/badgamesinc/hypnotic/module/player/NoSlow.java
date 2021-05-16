package badgamesinc.hypnotic.module.player;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.event.Event;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.KillAura;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.util.ColorUtils;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow extends Mod {

	public Setting mode;
	
	public NoSlow() {
		super("NoSlow", 0, Category.PLAYER, "Move at normal speeds while using items");
		ArrayList<String> options = new ArrayList<>();
		options.add("NCP");
		options.add("Vanilla");
		Hypnotic.instance.setmgr.rSetting(mode = new Setting("NoSlow mode", this, "NCP", options));
	}
	
	
	@Override
	public void onUpdate() {
		this.setDisplayName("NoSlow " + ColorUtils.white + mode.getValString() + "  "); 
	}
	
}
