package badgamesinc.hypnotic.module.movement;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.client.entity.EntityPlayerSP;

public class Speed extends Mod
{
    ArrayList<String> options;
    
    public Speed() {
        super("Speed", 36, Category.MOVEMENT, "Zoom around those noobs");
    }
    
    @Override
    public void setup() {
        final ArrayList<String> options = new ArrayList<String>();
        options.add("OnGround");
        options.add("Bhop");
        options.add("Strafe");
        Hypnotic.instance.setmgr.rSetting(new Setting("SpeedMode", this, "Bhop", options));
        Hypnotic.instance.setmgr.rSetting(new Setting("GroundSpeed", this, 1.1, 1.1, 1.7, false));
        Hypnotic.instance.setmgr.rSetting(new Setting("BhopSpeed", this, 0.05, 0.05, 0.5, false));
    }
    
    public double getOnGroundSpeedValue() {
        return Hypnotic.instance.setmgr.getSettingByName("GroundSpeed").getValDouble();
    }
    
    public double getBhopSpeedValue() {
        return Hypnotic.instance.setmgr.getSettingByName("BhopSpeed").getValDouble();
    }
    
    public String getMode() {
        return Hypnotic.instance.setmgr.getSettingByName("SpeedMode").getValString();
    }
    
    public Setting getModeSetting() {
        return Hypnotic.instance.setmgr.getSettingByName("SpeedMode");
    }
    
    public ArrayList<String> getSettings() {
        return this.options;
    }
    
    @Override
    public void onUpdate() {
        if (this.getMode().equalsIgnoreCase("OnGround")) {
            if (mc.thePlayer.moveForward == 0.0f && mc.thePlayer.moveStrafing == 0.0f && mc.thePlayer.onGround) {
                mc.thePlayer.setVelocity(0.0, 0.0, 0.0);
            }
            if (mc.thePlayer.onGround) {
                final EntityPlayerSP thePlayer = mc.thePlayer;
                thePlayer.motionX *= this.getOnGroundSpeedValue();
                final EntityPlayerSP thePlayer2 = mc.thePlayer;
                thePlayer2.motionZ *= this.getOnGroundSpeedValue();
                final EntityPlayerSP thePlayer3 = mc.thePlayer;
                thePlayer3.moveStrafing *= 5.0f;
            }
            else {
                final EntityPlayerSP thePlayer4 = mc.thePlayer;
                thePlayer4.motionY /= 1.0;
                final EntityPlayerSP thePlayer5 = mc.thePlayer;
                thePlayer5.moveStrafing *= 1.3f;
            }
        }
        if (this.getMode().equalsIgnoreCase("Bhop")) {
            if (!this.isEnabled()) {
                return;
            }
            if (mc.thePlayer.onGround) {
                if (mc.thePlayer.moveForward != 0.0f || mc.thePlayer.moveStrafing != 0.0f) {
                    mc.thePlayer.jump();
                }
                mc.timer.timerSpeed = 1.12f;
                final EntityPlayerSP thePlayer6 = mc.thePlayer;
                thePlayer6.motionX /= 4.0;
                final EntityPlayerSP thePlayer7 = mc.thePlayer;
                thePlayer7.motionZ /= 4.0;
            }
            else {
                mc.timer.timerSpeed = 1.12f;
                final EntityPlayerSP thePlayer8 = mc.thePlayer;
                thePlayer8.motionX *= 1.0;
                final EntityPlayerSP thePlayer9 = mc.thePlayer;
                thePlayer9.motionY *= 1.05;
                final EntityPlayerSP thePlayer10 = mc.thePlayer;
                thePlayer10.moveStrafing *= 5.0f;
                mc.thePlayer.landMovementFactor = (float)this.getBhopSpeedValue();
                mc.thePlayer.jumpMovementFactor = (float)this.getBhopSpeedValue();
            }
        }
        if(getMode().equalsIgnoreCase("Strafe")) {
        	mc.thePlayer.moveStrafing *= 5f;
        }
        final String mode = Hypnotic.instance.setmgr.getSettingByName("SpeedMode").getValString();
        this.setDisplayName("Speed §7- " + mode);
    }
    
    @Override
    public void onDisable() {
        if (this.getMode().equalsIgnoreCase("Bhop")) {
            mc.timer.timerSpeed = 1.0f;
            mc.thePlayer.landMovementFactor = 0.03f;
            mc.thePlayer.jumpMovementFactor = 0.03f;
        }
    }
}

