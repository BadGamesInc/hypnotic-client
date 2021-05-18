package badgamesinc.hypnotic.module.movement;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventCollide;
import badgamesinc.hypnotic.event.events.EventUpdate;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.PlayerUtils;
import badgamesinc.hypnotic.util.TimerUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Phase extends Mod {
	   
	   TimerUtils timer = new TimerUtils();
	   
	   public Setting phaseMode;

	   public Phase() {
	      super("Phase", 0, Category.PLAYER, "Phase throught the floor");
	      ArrayList<String> options = new ArrayList<>();
	      options.add("Redesky");
	      options.add("Vanilla Phase");
	      Hypnotic.instance.setmgr.rSetting(phaseMode = new Setting("Phase Mode", this, "Redesky", options));
	   }
	   
	   private int reset;
	   private double dist = 1D;

	    @Override
	    public void onUpdate() {
	    	this.setDisplayName("Phase " + ColorUtils.white + "[" + phaseMode.getValString() + "] ");
	    	if (Hypnotic.instance.setmgr.getSettingByName("Phase Mode").getValString().equalsIgnoreCase("Redesky")) {
			      this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0E-8D, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, false));
			      this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0D, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, false));
			      this.toggle();
			}
	    	
	    	if (Hypnotic.instance.setmgr.getSettingByName("Phase Mode").getValString().equalsIgnoreCase("Vanilla Phase")) {
		        reset -= 1;
		        double xOff = 0;
		        double zOff = 0;
		        double multi = 2.6D;
		        double mx = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw + 90F));
		        double mz = Math.sin(Math.toRadians(mc.thePlayer.rotationYaw + 90F));
		        xOff = mc.thePlayer.moveForward * 2.6D * mx + mc.thePlayer.moveStrafing * 2.6D * mz;
		        zOff = mc.thePlayer.moveForward * 2.6D * mz + mc.thePlayer.moveStrafing * 2.6D * mx;
		        if(PlayerUtils.isInsideBlock() && mc.thePlayer.isSneaking())
		            reset = 1;
		        if(reset > 0)
		            mc.thePlayer.boundingBox.offset(xOff, 0, zOff);
	    	}
	    }

	    @EventTarget
	    public boolean onCollision(EventCollide event) {
	    	if((PlayerUtils.isInsideBlock() && mc.gameSettings.keyBindJump.isKeyDown()) || (!(PlayerUtils.isInsideBlock()) && event.getBoundingBox() != null && event.getBoundingBox().maxY > mc.thePlayer.boundingBox.minY && mc.thePlayer.isSneaking()))
	            event.setBoundingBox(null);
	        return true;
	    }
	    
	}
