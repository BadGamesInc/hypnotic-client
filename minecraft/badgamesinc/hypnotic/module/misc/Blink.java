package badgamesinc.hypnotic.module.misc;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.event.events.EventSendPacket;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class Blink extends Mod {

	public EntityOtherPlayerMP fakePlayer;
	
	public Blink() {
		super("Blink", 0, Category.MISC, "Stops you from sending packets (visualy teleports you)");
	}
	
	@Override
	public void onEnable() {
		(this.fakePlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile())).clonePlayer(mc.thePlayer, true);
		this.fakePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        this.fakePlayer.rotationYawHead = mc.thePlayer.rotationYaw;
        this.fakePlayer.rotationPitch = mc.thePlayer.rotationPitch;
        this.fakePlayer.setSneaking(mc.thePlayer.isSneaking());
		mc.theWorld.spawnEntityInWorld(fakePlayer);
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		mc.theWorld.removeEntity(fakePlayer);
		super.onDisable();
	}
	
	@EventTarget
	public void sendPacket(EventSendPacket e) {
		if (mc.theWorld != null && mc.thePlayer != null)
			e.setCancelled(true);
		else
			e.setCancelled(false);
	}

}
