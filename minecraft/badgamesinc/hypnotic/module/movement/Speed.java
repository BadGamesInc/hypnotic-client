package badgamesinc.hypnotic.module.movement;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.Event;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventLastDistance;
import badgamesinc.hypnotic.event.events.EventMotion;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.event.events.EventUpdate;
import badgamesinc.hypnotic.gui.notifications.Color;
import badgamesinc.hypnotic.gui.notifications.NotificationManager;
import badgamesinc.hypnotic.gui.notifications.Type;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.KillAura;
import badgamesinc.hypnotic.module.combat.OtherAura;
import badgamesinc.hypnotic.module.combat.TargetStrafe;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MoveUtils;
import badgamesinc.hypnotic.util.PlayerUtils;
import badgamesinc.hypnotic.util.SetBlockAndFacing;
import badgamesinc.hypnotic.util.TimeHelper;
import badgamesinc.hypnotic.util.WorldUtils;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

public class Speed extends Mod {
    public ModeSetting mode = new ModeSetting("Mode", "Hypixel", 
    		"Hypixel",
    		"NCP",
    		"BhopYPort",
    		"FastHop",
    		"BhopDamage",
    		"Gaming",
    		"Gaming2",
    		"LowHop");
    public NumberSetting speed = new NumberSetting("Speed", 1, 0, 10, 0.05);
    public BooleanSetting disable = new BooleanSetting("Disable On Flag", true);
    public BooleanSetting damage = new BooleanSetting("Damage", false);
    
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


    public Speed(){
        super("Speed", Keyboard.KEY_J, Category.MOVEMENT, "Move faster");
        addSettings(mode, speed, disable, damage);
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
    	if (Hypnotic.instance.moduleManager.getModule(OtherAura.class).target != null) {
	        if(Hypnotic.instance.moduleManager.getModule(OtherAura.class).target.posY - Hypnotic.instance.moduleManager.getModule(OtherAura.class).target.prevPosY >=0) {
	            /*if (!mc.thePlayer.onGround || mc.thePlayer.isCollidedHorizontally) {
	                airTicks+=0.1;
	                if (airTicks >= 1) {
	                    direction = !direction;
	                    airTicks = 0;
	                }
	            } else {
	                airTicks = 0;
	            }*/
	        } else {
	            airTicks++;
	            if(airTicks >= 2 && mc.thePlayer.isCollidedHorizontally){
	                direction = !direction;
	                airTicks = 0;
	            }
	
	        }
    	}
        double slowdown;
        if(mode.getSelected().equalsIgnoreCase("Gaming2")){
            motion = Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ());
            if(airSlot() == -10){
                Wrapper.tellPlayer("Clear a hotbar slot");
                this.toggle();
                return;
            }

            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(airSlot()));
            SetBlockAndFacing.BlockUtil.placeHeldItemUnderPlayer();
            double targetSpeed = this.speed.getValue();
            if(moveSpeed < targetSpeed){
                moveSpeed += 0.12505;
            }else {
                moveSpeed = targetSpeed;
            }
            MoveUtils.setMotion(moveSpeed);

            if(TargetStrafe.canStrafe()){
                TargetStrafe.strafe(event, motion, Hypnotic.instance.moduleManager.getModule(OtherAura.class).target, this.direction);
            }
        } else if(mode.getSelected().equalsIgnoreCase("Gaming")){
            motion = Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ());
            Entity player = mc.thePlayer;
            BlockPos pos = new BlockPos(player.posX, player.posY - 1, player.posZ);
            Block block = mc.theWorld.getBlockState(pos).getBlock();
            if (block instanceof BlockSlab || block instanceof BlockStairs || block instanceof BlockCarpet) {
                MoveUtils.setMoveSpeed(event, 0.4);
                //speed = 0;
                return;
            }
            mc.timer.timerSpeed = 1.0F;
            if (mc.thePlayer.isMovingOnGround()) {
                mc.timer.timerSpeed = 1F;
                event.setY(mc.thePlayer.motionY = 0.419);
                doSlow = true;
                dist = moveSpeed;
                moveSpeed = 0;
            } else {
                mc.timer.timerSpeed = 1.0F;
                if (doSlow) {
                    moveSpeed = dist + 0.56F;
                    doSlow = false;
                } else {
                    moveSpeed = lastDist * (moveSpeed > 2.2 ? 0.975 : moveSpeed >= 1.5 ? 0.98 : 0.985);
                    event.setY(event.getY() - 0.2);
                }
                event.setY(event.getY() * 0.0004);
                event.setY(event.getY() - 1.0e-4);
            }

            double max = this.speed.getValue();
            MoveUtils.setMotion(event, Math.max(Math.min(moveSpeed, max), doSlow ? 0 : 0.42));

            if(TargetStrafe.canStrafe()){
                TargetStrafe.strafe(event, motion, Hypnotic.instance.moduleManager.getModule(OtherAura.class).target, this.direction);
            }
        } else if(mode.getSelected().equalsIgnoreCase("FastHop")){

            switch (stage) {
                case 1:
                    moveSpeed = 0.62;
                    lastDist = 0.0D;
                    ++stage;
                    break;
                case 2:
                    lastDist = 0.0D;
                    float motionY = 0.419999f;
                    if ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) && mc.thePlayer.onGround) {
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
            MoveUtils.setMotion(event, moveSpeed);
            motion = moveSpeed;
            if(TargetStrafe.canStrafe()){
                TargetStrafe.strafe(event, motion, Hypnotic.instance.moduleManager.getModule(OtherAura.class).target, this.direction);
            }
            ++stage;
        } else if(mode.getSelected().equalsIgnoreCase("NCP") || mode.getSelected().equalsIgnoreCase("BhopDamage")){
            
            if (MoveUtils.isMoving()) {
                    double baseMoveSpeed = MoveUtils.getBaseMoveSpeed();
                    boolean inLiquid = MoveUtils.isInLiquid();
                    mc.timer.timerSpeed = 1.05f;
                    if (inLiquid) {
                        event.setY(Wrapper.getPlayer().motionY = MoveUtils.getJumpHeight());

                        if (ticksSinceJump > 2) {
                            moveSpeed = MoveUtils.getBaseMoveSpeed() + 1;
                        } else {
                            ticksSinceJump++;
                            moveSpeed = MoveUtils.calculateFriction(moveSpeed, lastDist, baseMoveSpeed);
                        }
                    } else if (MoveUtils.isOnGround()) {
                        wasOnGround = true;
                        moveSpeed = (baseMoveSpeed / (ticksSinceJump <= 2 ? MoveUtils.SPRINTING_MOD : 1.0)) * MoveUtils.MAX_DIST;
                        if (MoveUtils.isOnIce())
                            moveSpeed *= MoveUtils.ICE_MOD;
                        event.setY(Wrapper.getPlayer().motionY = MoveUtils.getJumpHeight() - 0.013);
                        ticksSinceJump = 0;
                    } else if (wasOnGround) {
                        double difference = MoveUtils.WATCHDOG_BUNNY_SLOPE * (lastDist - baseMoveSpeed);
                        moveSpeed = lastDist - difference;
                        wasOnGround = false;
                    } else {
                        ticksSinceJump++;
                        moveSpeed = MoveUtils.calculateFriction(moveSpeed, lastDist, baseMoveSpeed);
                    }

                    MoveUtils.setMotion(event, Math.max(moveSpeed, mc.thePlayer.isPotionActive(Potion.moveSpeed) ? baseMoveSpeed + 0.004 : baseMoveSpeed + 0.003));


            }
            motion = MoveUtils.getSpeed();
            if(TargetStrafe.canStrafe()) {
                TargetStrafe.strafe(event, motion, Hypnotic.instance.moduleManager.getModule(OtherAura.class).target, this.direction);
            }
        } else if(mode.getSelected().equalsIgnoreCase("LowHop")){
            
            if (MoveUtils.isMoving()) {
                    double baseMoveSpeed = MoveUtils.getBaseMoveSpeed();
                    boolean inLiquid = MoveUtils.isInLiquid();
                    mc.timer.timerSpeed = 1.05f;
                    if (inLiquid) {
                        event.setY(Wrapper.getPlayer().motionY = MoveUtils.getJumpHeight());

                        if (ticksSinceJump > 2) {
                            moveSpeed = MoveUtils.getBaseMoveSpeed() + 1;
                        } else {
                            ticksSinceJump++;
                            moveSpeed = MoveUtils.calculateFriction(moveSpeed, lastDist, baseMoveSpeed);
                        }
                    } else if (MoveUtils.isOnGround()) {
                        wasOnGround = true;
                        moveSpeed = (baseMoveSpeed / (ticksSinceJump <= 2 ? MoveUtils.SPRINTING_MOD : 1.0)) * MoveUtils.MAX_DIST;
                        if (MoveUtils.isOnIce())
                            moveSpeed *= MoveUtils.ICE_MOD;
                        
                        event.setY(Wrapper.getPlayer().motionY = MoveUtils.getJumpHeight() - (mc.gameSettings.keyBindJump.isKeyDown() || TargetStrafe.canStrafe() || !isAir() ? 0.019 : 0.119));
                        ticksSinceJump = 0;
                    } else if (wasOnGround) {
                        double difference = MoveUtils.WATCHDOG_BUNNY_SLOPE * (lastDist - baseMoveSpeed);
                        moveSpeed = lastDist - difference;
                        wasOnGround = false;
                    } else {
                        ticksSinceJump++;
                        moveSpeed = MoveUtils.calculateFriction(moveSpeed, lastDist, baseMoveSpeed);
                    }

                    MoveUtils.setMotion(event, Math.max(moveSpeed + speed.getValue() * 0.1f, baseMoveSpeed));


            }
            motion = MoveUtils.getSpeed();
            if(TargetStrafe.canStrafe()) {
                TargetStrafe.strafe(event, motion, Hypnotic.instance.moduleManager.getModule(OtherAura.class).target, this.direction);
            }
        }  else if(mode.getSelected().equalsIgnoreCase("BhopDamage")){
            if(mc.thePlayer.onGround){
                moveSpeed = 0.635;
                event.setY(event.getLegitMotion());

            }else {
                moveSpeed = moveSpeed - lastDist / 50;
            }
            MoveUtils.setMotion(event, moveSpeed);



        } else if(mode.getSelected().equalsIgnoreCase("BhopYPort")){
            if(mc.thePlayer.onGround){
                moveSpeed = (MoveUtils.getBaseMoveSpeed() * 1.026121442084547811);
                event.setY(0.28);
                ticksSinceJump = 0;
                wasOnGround = true;
            }else if (wasOnGround){
                double difference = MoveUtils.WATCHDOG_BUNNY_SLOPE * (lastDist - MoveUtils.getBaseMoveSpeed());
                moveSpeed = 0.39* (1 + (1 - 0.949999988079071));
                wasOnGround = false;
            } else if(!MoveUtils.isOnGround(0.6121442084547811) && !wasOnGround){
                moveSpeed = moveSpeed - lastDist / 158;
                if(ticksSinceJump == 0) {
                    event.setY(-0.42 / 1.8988465674311579);
                    ticksSinceJump++;
                }
            }else {
                moveSpeed = moveSpeed - lastDist / 158;
            }
            MoveUtils.setMotion(moveSpeed);
            if(TargetStrafe.canStrafe()) {
                TargetStrafe.strafe(event, moveSpeed, Hypnotic.instance.moduleManager.getModule(OtherAura.class).target, this.direction);
            }

        } else if(mode.getSelected().equalsIgnoreCase("Hypixel")) {
        	
        	Entity player = mc.thePlayer;
            BlockPos pos = new BlockPos(player.posX, player.posY - 0.5, player.posZ);
            Block block = mc.theWorld.getBlockState(pos).getBlock();
        	if (MoveUtils.isMoving()) {
                double baseMoveSpeed = MoveUtils.getBaseMoveSpeed();
                boolean inLiquid = MoveUtils.isInLiquid();
                if (inLiquid) {
                    //event.setY(Wrapper.getPlayer().motionY = MoveUtils.getJumpHeight());

                    if (ticksSinceJump > 2) {
                        moveSpeed = MoveUtils.getBaseMoveSpeed() + 1;
                    } else {
                        ticksSinceJump++;
                        moveSpeed = MoveUtils.calculateFriction(moveSpeed, lastDist, baseMoveSpeed);
                    }
                } else if (MoveUtils.isOnGround()) {
                	
                    wasOnGround = true;
                    moveSpeed = (baseMoveSpeed / (ticksSinceJump <= 2 ? MoveUtils.SPRINTING_MOD : 1.0)) * MoveUtils.MAX_DIST;
                    if (MoveUtils.isOnIce())
                        moveSpeed *= MoveUtils.ICE_MOD;
                    event.setY(Wrapper.getPlayer().motionY = MoveUtils.getJumpHeight() + 0.0);
                    ticksSinceJump = 0;
                } else if (wasOnGround) {
                    double difference = MoveUtils.WATCHDOG_BUNNY_SLOPE * (lastDist - baseMoveSpeed);
                    moveSpeed = lastDist - difference;
                    wasOnGround = false;
                } else {
                    ticksSinceJump++;
                    moveSpeed = MoveUtils.calculateFriction(moveSpeed, lastDist, baseMoveSpeed);
                }

                if (PlayerUtils.isInLiquid() || (block instanceof BlockSlab || block instanceof BlockStairs || block instanceof BlockCarpet))
                	return;
                MoveUtils.setMotion(event, (PlayerUtils.isInLiquid() || (block instanceof BlockSlab || block instanceof BlockStairs || block instanceof BlockCarpet)) ? baseMoveSpeed : Math.max(moveSpeed, baseMoveSpeed + 0.001));


        }
        motion = MoveUtils.getSpeed();
	        if(TargetStrafe.canStrafe()) {
	            TargetStrafe.strafe(event, motion + 0.001, Hypnotic.instance.moduleManager.getModule(OtherAura.class).target, direction);
	        }
        }





    }

    public boolean isAir() {
    	for (float i = 0; i < speed.getValue() + 1; i+=0.0001) {
    		if (!(WorldUtils.getForwardBlock(i).getBlock() instanceof BlockAir) && !(WorldUtils.getForwardBlock(i).getBlock() instanceof BlockSlab) || mc.thePlayer.isCollidedHorizontally)
    			return false;
    	}
    	for (float i = 0; i < speed.getValue() + 1; i+=0.0001) {
    		if (!(WorldUtils.getForwardBlock(-i).getBlock() instanceof BlockAir) && !(WorldUtils.getForwardBlock(i).getBlock() instanceof BlockSlab) || mc.thePlayer.isCollidedHorizontally)
    			return false;
    	}
    	return true;
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
            this.setDisplayName("Speed " + ColorUtils.white + "[" + mode.getSelected() + "]");
            switch (mode.getSelected()) {
                /*case "NCP":
                    if(mc.thePlayer.onGround){
                        mc.thePlayer.jump();
                    }else {
                        MoveUtils.setMotion(MoveUtils.getBaseMoveSpeed() * 1.015);
                        if(mc.theWorld.getBlockState(new BlockPos(MathHelper.floor_double(mc.thePlayer.posX), MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minY) - 1.1, MathHelper.floor_double(mc.thePlayer.posZ))).getBlock().slipperiness > 0.7){
                            MoveUtils.setMotion(0.62);
                        }

                    }
                    break;*/
                case "FastHop":
                case "NCP":
                    if(!MoveUtils.isMoving())
                        return;
                    if (MoveUtils.isOnGround()) {
                        event.setOnGround(true);
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
                    }
                    break;
                case "Hypixel":
                    if(!MoveUtils.isMoving())
                        return;
                    if (MoveUtils.isOnGround()) {
                        event.setOnGround(false);
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
                    }
                    break;
                case "Gaming2":
                	
                    break;

                case "Gaming":
                   mc.thePlayer.stepHeight = 0.0F;
                    if (mc.thePlayer.isCollidedHorizontally) {
                        this.moveSpeed = 0.0D;
                        this.hops = 1;
                    }

                    if (mc.thePlayer.fallDistance < 8.0F) {
                        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.4D, mc.thePlayer.posZ);
                        EntityPlayerSP var10000 = mc.thePlayer;
                        var10000.motionY += 0.02D;
                    } else {
                        this.moveSpeed = 0.0D;
                        this.hops = 1;
                        mc.thePlayer.motionY = -1.0D;
                    }


                    break;
                case "Bhop":
                	if(!MoveUtils.isMoving())
                        return;
                    if (MoveUtils.isOnGround()) {
                        event.setOnGround(false);
                        mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
                    }
                    break;
            }
        }
        if(TargetStrafe.canStrafe()){
            TargetStrafe.strafe(event, motion, Hypnotic.instance.moduleManager.getModule(OtherAura.class).target, this.direction);
        }
        motion = MoveUtils.getSpeed();


    }

    public static int airSlot() {
        for (int j = 0; j < 8; ++j) {
            if (Minecraft.getMinecraft().thePlayer.inventory.mainInventory[j] == null) {
                return j;
            }
        }
        return -10;
    }
    
    @EventTarget
    public void reveivePacket(EventReceivePacket e) {
    	if (disable.isEnabled()) {
	    	if (e.getPacket() instanceof S08PacketPlayerPosLook) {
	    		Hypnotic.instance.eventManager.unregister(this);
	    		this.wasFlag = true;
	    		NotificationManager.getNotificationManager().createNotification("Flag", this.getName() + " was disabled due to a flag", true, 1000, Type.WARNING, Color.YELLOW);
	    		this.toggleSilent();
	    	}
    	}
    }

    @Override
    public void onDisable(){
        super.onDisable();
        this.wasFlag = false;
        mc.timer.timerSpeed = 1f;
        level = 0;
        mc.thePlayer.stepHeight = 0.625f;
        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
        this.wasFlag = false;
    }

    @Override
    public void onEnable(){
    	//EntityPlayer ep;
    	//mc.theWorld.spawnEntityInWorld(ep);
        level = (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0, mc.thePlayer.motionY, 0.0)).size() > 0 || mc.thePlayer.isCollidedVertically) ? 1 : 4;
        timerDelay = 0;
        super.onEnable();
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
        if (damage.isEnabled())
        	PlayerUtils.damage();
        //Hypnotic.instance.moduleManager.getModule(KillAura.class).toggle();
        //Hypnotic.instance.moduleManager.getModule(KillAura.class).toggle();
    }
    
    public static double motion = 0;
    
    @EventTarget
    public void onUpdate(EventUpdate event) {
    	motion = MoveUtils.getSpeed();
        double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
        double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
        if(mode.getSelected().equalsIgnoreCase("Gaming2")) {
        	if(mc.thePlayer.isMovingOnGround() && mc.thePlayer.moveForward != 0){
                mc.thePlayer.jump();
            }
        }
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }


}
