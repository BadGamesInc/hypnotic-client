package badgamesinc.hypnotic.module.player;

import org.apache.commons.lang3.RandomUtils;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventPostMotionUpdate;
import badgamesinc.hypnotic.event.events.EventPreMotionUpdate;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.KillAura;
import badgamesinc.hypnotic.util.MoveUtils;
import badgamesinc.hypnotic.util.PlayerUtils;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow extends Mod {

	public NoSlow() {
		super("NoSlow", 0, Category.PLAYER, "Move at normal speeds while using items");
	}
	
}
