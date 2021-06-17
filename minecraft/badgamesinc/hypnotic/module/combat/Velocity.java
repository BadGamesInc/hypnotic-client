package badgamesinc.hypnotic.module.combat;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MathUtils;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Mod {

	public NumberSetting horizontalV = new NumberSetting("Horizontal", 0, 0, 100, 1);
	public NumberSetting verticalV = new NumberSetting("Vertical", 0, 0, 100, 1);
	
	public Velocity() {
		super("Velocity", 0, Category.COMBAT, "Modify your horizontal and vertical velocity");
		addSettings(horizontalV, verticalV);
	}
	
	@Override
	public void onUpdate() {
		double horizontal = horizontalV.getValue();
		double vertical = verticalV.getValue();
		
		this.setDisplayName("Velocity " + ColorUtils.white + "[H: " + MathUtils.round(horizontal, 2) + " V: " + MathUtils.round(vertical, 2) + "]");
	}
	
	@EventTarget
	public void eventPacket(EventReceivePacket event) {
		
		if (event.getPacket() instanceof S12PacketEntityVelocity) {
			
			S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();
			double horizontal = horizontalV.getValue();
			double vertical = verticalV.getValue();
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
