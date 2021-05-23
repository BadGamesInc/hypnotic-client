package badgamesinc.hypnotic.module.combat;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MathUtils;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Mod {

	public Velocity() {
		super("Velocity", 0, Category.COMBAT, "Modify your horizontal and vertical velocity");
	}
	
	@Override
	public void setup() {
		Hypnotic.instance.setmgr.rSetting(new Setting("Horizontal", this, 0, 0, 100, false));
		Hypnotic.instance.setmgr.rSetting(new Setting("Vertical", this, 0, 0, 100, false));
	}
	
	@Override
	public void onUpdate() {
		
		double horizontal = Hypnotic.instance.setmgr.getSettingByName("Horizontal").getValDouble();
		double vertical = Hypnotic.instance.setmgr.getSettingByName("Vertical").getValDouble();
		
		this.setDisplayName("Velocity " + ColorUtils.white + "[H: " + MathUtils.round(horizontal, 2) + " V: " + MathUtils.round(vertical, 2) + "] ");
	}
	
	public void eventPacket(EventReceivePacket event) {
		
		if (event.getPacket() instanceof S12PacketEntityVelocity) {
			
			S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();
			double horizontal = Hypnotic.instance.setmgr.getSettingByName("Horizontal").getValDouble();
			double vertical = Hypnotic.instance.setmgr.getSettingByName("Vertical").getValDouble();
			packet.setMotionX((int) ((packet.getMotionX() / 100) * horizontal));
			packet.setMotionY((int) ((packet.getMotionY() / 100) * vertical));
			packet.setMotionZ((int) ((packet.getMotionZ() / 100) * horizontal));
			
			if (horizontal == 0 && vertical == 0) {
				event.setCancelled(true);
			}
			
			
		}
		else if (event.getPacket() instanceof S27PacketExplosion) {
			event.setCancelled(true);
		}
	}

}
