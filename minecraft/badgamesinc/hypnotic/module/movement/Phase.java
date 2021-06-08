package badgamesinc.hypnotic.module.movement;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventCollide;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MovementUtils;
import badgamesinc.hypnotic.util.PlayerUtils;
import badgamesinc.hypnotic.util.TimerUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Phase extends Mod {
	   
	   TimerUtils timer = new TimerUtils();
	   
	   public ModeSetting phaseMode = new ModeSetting("Mode", "Vanilla", "Vanilla");

	   public Phase() {
	      super("Phase", 0, Category.PLAYER, "Phase throught the floor");
	      addSettings(phaseMode);
	   }
	   
	   private int reset;
	   private double dist = 1D;

	    @Override
	    public void onUpdate() {
	    	this.setDisplayName("Phase " + ColorUtils.white + "[" + phaseMode.getSelected() + "] ");
	    	
	    	if (phaseMode.getSelected().equalsIgnoreCase("Vanilla")) {
		        reset -= 1;
		        double xOff = 0;
		        double zOff = 0;
		        double multi = 2.6D;
		        double mx = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90F));
		        double mz = Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90F));
		        xOff = mc.thePlayer.moveForward * 20.6D * mx + mc.thePlayer.moveStrafing * 2.6D * mz;
		        zOff = mc.thePlayer.moveForward * 20.6D * mz + mc.thePlayer.moveStrafing * 2.6D * mx;
		        if(PlayerUtils.isInsideBlock() && mc.thePlayer.isSneaking())
		            reset = 1;
		        if(reset > 0)
		            mc.thePlayer.boundingBox.offset(xOff, 0, zOff);
	    	}
	    }

	    @EventTarget
	    public boolean onCollision(EventCollide event) {
	    	if((PlayerUtils.isInsideBlock() && mc.gameSettings.keyBindJump.isKeyDown()) || (!(PlayerUtils.isInsideBlock()) && event.getBoundingBox() != null && event.getBoundingBox().maxY > mc.thePlayer.boundingBox.minY && mc.thePlayer.isSneaking())) {
	            event.setBoundingBox(null);
	    	}
	    	if (PlayerUtils.isInsideBlock()) {
	    		MovementUtils.setMotion(1.5);
	    	}
	        return true;
	    }
	    
	}
