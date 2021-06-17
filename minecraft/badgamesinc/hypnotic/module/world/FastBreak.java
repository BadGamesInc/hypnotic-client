package badgamesinc.hypnotic.module.world;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventBlockDamage;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;

public class FastBreak extends Mod {
	
	public NumberSetting speed = new NumberSetting("Speed", 0.25, 0.1, 2, 0.05);
	
	public FastBreak() {
		super("FastBreak", 0, Category.WORLD, "Break blocks faster");
		addSettings(speed);
	}
	
	@Override
	public void onUpdate() {
        mc.playerController.blockHitDelay = 0;
	}
	
	@EventTarget
	public void eventDamageBlock(EventBlockDamage e) {
		PlayerControllerMP playerController = mc.playerController;
		BlockPos pos = e.getBlockPos();
		mc.thePlayer.swingItem();
		playerController.curBlockDamageMP += this.getBlock((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()).getPlayerRelativeBlockHardness(mc.thePlayer, mc.theWorld, pos) * (speed.getValue() + 0.086F);
	}
	
	public Block getBlock(double posX, double posY, double posZ) {
	    BlockPos pos = new BlockPos((int)posX, (int)posY, (int)posZ);
	    return mc.theWorld.getChunkFromBlockCoords(pos).getBlock(pos);
	}
}
