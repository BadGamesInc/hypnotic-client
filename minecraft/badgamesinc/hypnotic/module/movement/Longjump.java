package badgamesinc.hypnotic.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.Event;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventLastDistance;
import badgamesinc.hypnotic.event.events.EventMotion;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.event.events.EventUpdate;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.KillAura;
import badgamesinc.hypnotic.module.combat.TargetStrafe;
import badgamesinc.hypnotic.util.MoveUtils;
import badgamesinc.hypnotic.util.SetBlockAndFacing;
import badgamesinc.hypnotic.util.TimeHelper;
import badgamesinc.hypnotic.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Longjump extends Mod {
    public Setting mode;
    public Setting speed;
    int stage = 0;
    private double Gaming = 0;
    double dist;
    int stage4;
    int hops = 1;
    double moveSpeed = 0;
    double rounded = 0;
    double lastDist = 0;
    public double motionXZ;
    private TimeHelper timer = new TimeHelper();
    private double mstage = 0;
    private boolean doSlow;
    boolean half;
    double motionY;
    double air;
    boolean direction = false;
    int airTicks = 0;
    boolean wasOnGround;
    int ticksSinceJump;
    int timerDelay;
    int level = 0;


    public Longjump(){
        super("LongJump", Keyboard.KEY_V, Category.MOVEMENT, "Move faster");
        ArrayList<String> options = new ArrayList<>();
        options.add(" Redesky ");


        Hypnotic.instance.setmgr.rSetting(mode = new Setting("LongJump Mode", this, " Redesky ", options));
    }
    private boolean isBlockUnder() {
        for (int i = (int) (mc.thePlayer.posY - 1.0); i > 0; --i) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }
    @EventTarget
    public void onMotion(EventMotion event){



        if(Hypnotic.instance.moduleManager.getModule(KillAura.class).target != null && Hypnotic.instance.moduleManager.getModule(KillAura.class).target.posY - Hypnotic.instance.moduleManager.getModule(KillAura.class).target.prevPosY >=0) {
            if (!isBlockUnder() || mc.thePlayer.isCollidedHorizontally) {
                airTicks++;
                if (airTicks >= 1) {
                    direction = !direction;
                    airTicks = 0;
                }
            } else {
                airTicks = 0;
            }
        }else {
            airTicks++;
            if(airTicks >= 2){
                direction = !direction;
                airTicks = 0;
            }

        }

        double slowdown;
        if(mode.getValString().equalsIgnoreCase(" Redesky ")){

        	mc.timer.timerSpeed = 0.6f;
            switch (stage) {
                case 1:
                    moveSpeed = 0.62;
                    lastDist = 0.0D;
                    ++stage;
                    break;
                case 2:
                    lastDist = 0.0D;
                    float motionY = 0.70f;
                    if (mc.thePlayer.onGround && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
                        if (mc.thePlayer.isPotionActive(Potion.jump))
                            motionY += ((mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.099F);
                        event.setY(mc.thePlayer.motionY = motionY);
                        moveSpeed = 0.4 * 1.4;

                    } else if ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {

                    }
                    break;
                case 3:
                    double boost = (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? (mc.thePlayer.isPotionActive(Potion.jump) ? 0.915f : 0.725f) :
                            0.71625f);
                    moveSpeed = moveSpeed - boost * (lastDist - MoveUtils.getBaseMoveSpeed());
                    break;
                default:
                    ++stage;
                    if ((mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0 || mc.thePlayer.isCollidedVertically) && stage > 0) {
                        stage = mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F ? 0 : 1;
                    }
                    moveSpeed = moveSpeed - lastDist / 159D;
                    break;
            }
            moveSpeed = Math.max(moveSpeed, MoveUtils.getBaseMoveSpeed());
            double amplifier = mc.thePlayer.isPotionActive(Potion.moveSpeed.id) ? 0.1 : 0.277;
            MoveUtils.setMotion(event, moveSpeed + amplifier);
            motion = moveSpeed;
            if(TargetStrafe.canStrafe()){
                TargetStrafe.strafe(event, motion, Hypnotic.instance.moduleManager.getModule(KillAura.class).target, this.direction);
            }
            ++stage;
        }





    }

    @EventTarget
    public void onLastDist(EventLastDistance event){
    }

    public void damagePlayer() {
        if (mc.thePlayer.onGround) {
            final double offset = 0.4122222218322211111111F;
            final NetHandlerPlayClient netHandler = mc.getNetHandler();
            final EntityPlayerSP player = mc.thePlayer;
            final double x = player.posX;
            final double y = player.posY;
            final double z = player.posZ;
            for (int i = 0; i < 9; i++) {
                netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + offset, z, false));
                netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.000002737272, z, false));
                netHandler.addToSendQueue(new C03PacketPlayer(false));
            }
            netHandler.addToSendQueue(new C03PacketPlayer(true));
        }

    }

    @EventTarget
    public void onMotionUpdate(EventMotionUpdate event){
        if(event.getState() == Event.State.PRE) {
            this.setDisplayName("LongJump §7" + mode.getValString() + "");
            
        if(TargetStrafe.canStrafe()){
            TargetStrafe.strafe(event, motion, Hypnotic.instance.moduleManager.getModule(KillAura.class).target, this.direction);
        }
        motion = MoveUtils.getSpeed();
        }


    }


    public static int airSlot() {
        for (int j = 0; j < 8; ++j) {
            if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory[j] == null) {
                return j;
            }
        }
        return -10;
    }

    @Override
    public void onDisable(){
        super.onDisable();
        mc.timer.timerSpeed = 1f;
        level = 0;
        mc.thePlayer.stepHeight = 0.625f;
        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
    }

    @Override
    public void onEnable(){
        level = (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, mc.thePlayer.motionY, 0.0)).size() > 0 || mc.thePlayer.isCollidedVertically) ? 1 : 4;
        timerDelay = 0;
        super.onEnable();;
        this.stage = 0;
        moveSpeed = 0.024;
        this.stage4 = 0;
        this.lastDist = 0;
        this.moveSpeed = 0;
        mstage = 0;
        doSlow = true;
        Gaming = -2;
        hops = 1;
        timer.reset();;
        air = 0;

    }
    public static double motion = 0;
    @EventTarget
    public void onUpdate(EventUpdate event){motion = MoveUtils.getSpeed();
        double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
        double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
        if(mode.getValString().equalsIgnoreCase("Gaming2")) {
        	if(mc.thePlayer.isMovingOnGround() && mc.thePlayer.moveForward != 0){
                mc.thePlayer.jump();
            }
        }
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }


}
