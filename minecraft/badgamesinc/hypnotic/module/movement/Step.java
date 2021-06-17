package badgamesinc.hypnotic.module.movement;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventMotion;
import badgamesinc.hypnotic.event.events.EventStep;
import badgamesinc.hypnotic.event.events.StepPostEvent;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MoveUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.stats.StatList;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Step extends Mod {

	int stage = 0;

    public ModeSetting mode = new ModeSetting("Mode", "NCP", "NCP", "Spider");
    public NumberSetting timer = new NumberSetting("Step Timer", 0.5, 0, 1, 0.1);
    public BooleanSetting smooth = new BooleanSetting("Smooth Step", false);
    double stepY = 0, stepX = 0, stepZ = 0;
    boolean isStep = false;
    double jumpGround = 0;
    
	public Step() {
		super("Step", 0, Category.MOVEMENT, "Makes you step up whole blocks");
		addSettings(mode, timer, smooth);
	}

	@EventTarget
    public void onStep(EventMotion event){
		this.setDisplayName("Step " + ColorUtils.white + "[" + mode.getSelected() + "]");
        if(mode.is("Spider")){
            if(mc.thePlayer.isCollidedHorizontally){
                if(mc.thePlayer.onGround && shouldStep()){
                    fakeJump();
                    event.y = 0.41999998688698;
                    stage = 1;
                    return;
                }
                if(stage == 1){
                    event.y = 0.7531999805212 - 0.41999998688698;

                    stage = 2;
                    return;
                }

                if(stage == 2){
                    double yaw = MoveUtils.getDirection();
                    event.y = 1.001335979112147 - 0.7531999805212;

                    event.x = -sin(yaw) * 0.7;
                    event.z = cos(yaw) * 0.7;
                    stage = 4;
                }
            }else if(stage == 4){
                MoveUtils.setMotion(event, MoveUtils.getBaseMoveSpeed());
                stage = 0;
            }


        }
    }
	
    public void fakeJump(){
        mc.thePlayer.isAirBorne = true;
        mc.thePlayer.triggerAchievement(StatList.jumpStat);

    }
    
    int ticks = 2;
    
    @EventTarget
    public void onStep(EventStep event){
        if(!Hypnotic.instance.moduleManager.getModule(Speed.class).isEnabled() && mode.is("NCP")) {
            mc.thePlayer.stepHeight = 1;
            event.setStepHeight(1);
            if (ticks == 1) {
                mc.timer.timerSpeed = 1.0f;
                ticks = 2;
            }
            if (event.getStepHeight() > 0.625) {
                isStep = true;
                stepY = mc.thePlayer.posY;
                stepX = mc.thePlayer.posX;
                stepZ = mc.thePlayer.posZ;
            }
        }else {
            mc.thePlayer.stepHeight = 0.625f;
        }
    }

    @EventTarget
    public void onStepConfirm(StepPostEvent event){
        if(mode.is("NCP")){
            if(mc.thePlayer.getEntityBoundingBox().minY - stepY > 0.625 && !Hypnotic.instance.moduleManager.getModule(Speed.class).isEnabled()){
                fakeJump();
                mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(stepX, stepY + 0.41999998688698, stepZ,false));
                mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(stepX, stepY + 0.7531999805212, stepZ, false));
                isStep = false;
                mc.timer.timerSpeed = (float) this.timer.getValue();
                ticks = 1;
            }
        }
    }

    public boolean shouldStep(){
        double yaw = MoveUtils.getDirection();
        double x = -sin(yaw) * 0.4;
        double z = cos(yaw) * 0.4;
        return mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,mc.thePlayer.getEntityBoundingBox().offset(x, 1.001335979112147, z)).isEmpty();
    }
    
    @Override
    public void onDisable(){
        super.onDisable();
        mc.thePlayer.stepHeight = 0.625f;
        stage = 0;

    }
}
