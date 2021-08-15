package badgamesinc.hypnotic.module.combat;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;

import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event3D;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.RotationUtils;
import badgamesinc.hypnotic.util.TimeHelper;
import badgamesinc.hypnotic.util.Wrapper;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static net.minecraft.client.gui.inventory.GuiInventory.drawEntityOnScreen;
import static org.lwjgl.opengl.GL11.*;

public class OtherAura extends Mod {
    public NumberSetting minAps = new NumberSetting("Min APS", 8, 1, 20, 1);
    public NumberSetting maxAps = new NumberSetting("Max APS", 12, 1, 20, 1);
    public ModeSetting targetMode = new ModeSetting("Target Mode", "Priority", "Priority", "Switch", "Multi");
    public ModeSetting priority = new ModeSetting("Priority Mode", "Health", "Health", "Angle", "Distance");
    public NumberSetting Range = new NumberSetting("Range", 4, 0, 10, 0.1);
    public BooleanSetting AutoBlock = new BooleanSetting("AutoBlock", true);
    public ModeSetting AutoBlockMode = new ModeSetting("AutoBlock Mode", "Interact", "Interact", "Normal");
    public BooleanSetting KeepSprint = new BooleanSetting("Keep Sprint", true);
    public BooleanSetting Rotations = new BooleanSetting("Rotations", true);
    public ModeSetting RotationsMode = new ModeSetting("Rotations Mode", "Normal", "Normal", "Smooth", "AAC");;
    public BooleanSetting AACCheck = new BooleanSetting("AACCheck", false);
    public BooleanSetting invis = new BooleanSetting("Invisibles", false);
	public BooleanSetting players = new BooleanSetting("Players", true);
	public BooleanSetting animals = new BooleanSetting("Animals", false);
	public BooleanSetting monsters = new BooleanSetting("Monsters", false);
	public BooleanSetting passives = new BooleanSetting("Passives", false);
	public BooleanSetting teams = new BooleanSetting("Teams", false);
    public BooleanSetting ESP = new BooleanSetting("ESP", true);
    public EntityLivingBase target;
    List<EntityLivingBase> loaded = new ArrayList<>();
    TimeHelper timer = new TimeHelper();
    private float lastHealth = 0;
    int index = 0;
    TimeHelper switchTimer = new TimeHelper();

    public OtherAura() {
        super("Aura", Keyboard.KEY_O, Category.COMBAT, "");
        addSettings(targetMode, AutoBlockMode, RotationsMode, priority, minAps, maxAps, Range, AutoBlock, KeepSprint, Rotations, RotationsMode, AACCheck, invis, players, animals, monsters, passives, teams, ESP);
    }

    boolean direction = false;
    boolean blocking;

    private boolean isBlockUnder() {
        for (int i = (int) (mc.thePlayer.posY - 1.0); i > 0; --i) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }

    @Override
    public void onUpdate() {
        if (target != null) {
    		RenderUtils.setCustomYaw(RotationUtils.getRotations(target)[0]);
    		RenderUtils.setCustomPitch(RotationUtils.getRotations(target)[1]);
    	} else {
    		RenderUtils.resetPlayerYaw();
    		RenderUtils.resetPlayerPitch();
    	}
    	super.onUpdate();
    }

    
    @EventTarget
    public void onMotionEvent(EventMotionUpdate event) {
        if (!isBlockUnder() || mc.thePlayer.isCollidedHorizontally) {
            direction = !direction;
        }
        this.setDisplayName("Aura " + EnumChatFormatting.WHITE + "[" + this.targetMode.getSelected() + "]");
        
        if (event.isPre()) {
            if (targetMode.is("Priority")) {
                if (priority.is("Health")) {
                    target = getHealthPriority();
                } else if (priority.is("Angle")) {
                    target = getAnglePriority();
                } else {
                    target = getClosest(Range.getValue());
                }
                if (target != null) {
                    if (!KeepSprint.isEnabled()) {
                        mc.thePlayer.setSprinting(false);
                        mc.gameSettings.keyBindSprint.pressed = false;
                    }
                    if (mc.thePlayer.getDistanceToEntity(target) < Range.getValue()) {
                        boolean block = AutoBlock.isEnabled();
                        float[] rots = getRotations(target, event);
                        event.setYaw(rots[0]);
                        event.setPitch(rots[1]);
                        if (AACCheck.isEnabled() && (Math.round(event.getYaw()) - Math.round(RotationUtils.getRotations(target)[0]) > 3 || Math.round(event.getPitch()) - Math.round(RotationUtils.getRotations(target)[1]) > 3)) {
                            return;
                        }



                        if (timer.hasReached(randomClickDelay(minAps.getValue(), maxAps.getValue()))) {
                            if ( block) {
                                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                            }
                            blocking = false;
                            mc.thePlayer.swingItem();
                            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));

                            timer.reset();
                        }
                    }
                }

            } else if (targetMode.is("Switch")) {
                this.loaded = this.getTargets();
                if (this.loaded.size() == 0)
                    return;

                if (switchTimer.hasReached(400)) {
                    if (index < loaded.size() - 1) {
                        index++;
                    } else {
                        index = 0;
                    }
                    switchTimer.reset();
                }
                this.target = this.loaded.get(this.index);

                if (target != null) {
                    if (!KeepSprint.isEnabled()) {
                        mc.thePlayer.setSprinting(false);
                        mc.gameSettings.keyBindSprint.pressed = false;
                    }
                    if (mc.thePlayer.getDistanceToEntity(target) < Range.getValue()) {
                        boolean block = AutoBlock.isEnabled();
                        float[] rots = getRotations(target, event);
                        event.setYaw(rots[0]);
                        event.setPitch(rots[1]);
                        if (AACCheck.isEnabled() && (Math.round(event.getYaw()) - Math.round(RotationUtils.getRotations(target)[0]) > 3 || Math.round(event.getPitch()) - Math.round(RotationUtils.getRotations(target)[1]) > 3)) {
                            return;
                        }



                        if (timer.hasReached(randomClickDelay(minAps.getValue(), maxAps.getValue()))) {
                            if ( block) {
                                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                            }
                            blocking = false;
                            mc.thePlayer.swingItem();
                            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));

                            timer.reset();
                        }
                    }
                }

            } else {
                this.loaded = this.getTargets();
                if (this.loaded.size() == 0)
                    return;
                if (switchTimer.hasReached(1)) {
                    if (index < loaded.size() - 1) {
                        index++;
                    } else {
                        index = 0;
                    }
                    switchTimer.reset();
                    ;
                }
                this.target = this.loaded.get(this.index);

                if (target != null) {
                    if (!KeepSprint.isEnabled()) {
                        mc.thePlayer.setSprinting(false);
                        mc.gameSettings.keyBindSprint.pressed = false;
                    }
                    if (mc.thePlayer.getDistanceToEntity(target) < Range.getValue()) {
                        boolean block = AutoBlock.isEnabled();
                        float[] rots = getRotations(target, event);
                        event.setYaw(rots[0]);
                        event.setPitch(rots[1]);
                        if (AACCheck.isEnabled() && (Math.round(event.getYaw()) - Math.round(RotationUtils.getRotations(target)[0]) > 3 || Math.round(event.getPitch()) - Math.round(RotationUtils.getRotations(target)[1]) > 3)) {
                            return;
                        }



                        if (timer.hasReached(randomClickDelay(minAps.getValue(), maxAps.getValue()))) {
                            if ( block) {
                                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                            }
                            blocking = false;
                            mc.thePlayer.swingItem();
                            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));

                            timer.reset();
                        }
                    }
                }

            }
        } else {
            if(target != null) {
                boolean block = AutoBlock.isEnabled();
                if (block && mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                    Wrapper.getPlayer().setItemInUse(Wrapper.getPlayer().getCurrentEquippedItem(), Wrapper.getPlayer().getCurrentEquippedItem().getMaxItemUseDuration());
                    if (!blocking) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, null, 0.0f, 0.0f, 0.0f));
                        blocking = true;
                    }
                }
            }

        }
        if (this.loaded.isEmpty() && !this.targetMode.is("Priority")) {
            this.target = null;
        }
    }

    public static long randomClickDelay(final double minCPS, final double maxCPS) {
        return (long) ((Math.random() * (1000 / minCPS - 1000 / maxCPS + 1)) + 1000 / maxCPS);
    }

    public float[] getRotations(EntityLivingBase e, EventMotionUpdate event) {
    	
        if (RotationsMode.is("Normal")) {
            return RotationUtils.getRotations(e);
        } else if (RotationsMode.is("Smooth")) {
            float[] targetYaw = RotationUtils.getRotations(e);
            float yaw = 0;
            float speed = (float) ThreadLocalRandom.current().nextDouble(1.5, 2.2);
            float yawDifference = event.getLastYaw() - targetYaw[0];
            yaw = event.getLastYaw() - (yawDifference / speed);
            float pitch = 0;
            float pitchDifference = event.getLastPitch() - targetYaw[1];
            pitch = event.getLastPitch() - (pitchDifference / speed);
            return new float[]{yaw, pitch};
        } else if (RotationsMode.is("AAC")) {
            float[] rots = RotationUtils.getRotations(e);
            return new float[]{rots[0] + randomNumber(3, -3), rots[1] + randomNumber(3, -3)};
        }
        return null;
    }

    public EntityLivingBase getHealthPriority() {
        List<EntityLivingBase> entities = new ArrayList<>();
        for (Entity e : mc.theWorld.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                EntityLivingBase player = (EntityLivingBase) e;
                if (mc.thePlayer.getDistanceToEntity(player) < Range.getValue() && canAttack(player)) {
                    entities.add(player);
                }
            }
        }
        entities.sort((o1, o2) -> (int) (o1.getHealth() - o2.getHealth()));

        if (entities.isEmpty())
            return null;

        return entities.get(0);

    }


    public static int randomNumber(int max, int min) {
        return Math.round(min + (float) Math.random() * ((max - min)));
    }

    public EntityLivingBase getAnglePriority() {
        List<EntityLivingBase> entities = new ArrayList<>();
        for (Entity e : mc.theWorld.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                EntityLivingBase player = (EntityLivingBase) e;
                if (mc.thePlayer.getDistanceToEntity(player) < Range.getValue() && canAttack(player)) {
                    entities.add(player);
                }
            }
        }
        entities.sort((o1, o2) -> {
            float[] rot1 = RotationUtils.getRotations(o1);
            float[] rot2 = RotationUtils.getRotations(o2);
            return (int) ((mc.thePlayer.rotationYaw - rot1[0]) - (mc.thePlayer.rotationYaw - rot2[0]));
        });
        if (entities.isEmpty())
            return null;
        return entities.get(0);

    }

    private EntityLivingBase getClosest(double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (Object object : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity) object;
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase player = (EntityLivingBase) entity;
                if (canAttack(player)) {
                    double currentDist = mc.thePlayer.getDistanceToEntity(player);
                    if (currentDist <= dist) {
                        dist = currentDist;
                        target = player;
                    }
                }
            }
        }
        return target;
    }

    public List<EntityLivingBase> getTargets() {
        List<EntityLivingBase> load = new ArrayList<>();
        for (Entity e : mc.theWorld.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) e;
                if (canAttack(entity) && mc.thePlayer.getDistanceToEntity(entity) < this.Range.getValue()) {
                    load.add(entity);
                }
            }
        }
        return load;
    }

    public boolean canAttack(EntityLivingBase player) {
        if (player == mc.thePlayer)
            return false;
        if ((player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager)) {
            if (player instanceof EntityPlayer && !players.isEnabled())
                return false;
            if (player instanceof EntityAnimal && !animals.isEnabled())
                return false;
            if (player instanceof EntityMob && !monsters.isEnabled())
                return false;
            if ((player instanceof EntityVillager || player instanceof EntityArmorStand) && !passives.isEnabled())
                return false;

        }
        if (player instanceof EntityPlayer) {
            if (Hypnotic.instance.moduleManager.getModule(AntiBot.class).isBot((EntityPlayer) player) || !mc.thePlayer.canEntityBeSeen(player))
                return false;
        }
        if (player instanceof EntityPlayer && mc.thePlayer.isOnSameTeam((EntityPlayer) player) && teams.isEnabled())
            return false;
        if (player.isInvisible() && invis.isEnabled())
            return false;
        if (player instanceof EntityArmorStand)
        	return false;
        return true;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        timer.reset();
    }

    double delay = 0;
    boolean step  = false;
    @EventTarget
    public void on3D(Event3D event){
        if(ESP.isEnabled()){
            if(target != null){
                //for(int i = 0; i < 5; i++){
                    drawCircle(target, event.getPartialTicks(), 0.8, delay / 100);
              //  }
            }
        }
        if(delay > 200){
            step = false;
        }
        if(delay < 0){
            step = true;
        }
        if(step){
            delay+= 3;
        }else {
            delay-= 3;
        }
    }

    public int getAlpha(int delay){
        double state = Math.ceil((System.currentTimeMillis()) / 10);
        state %= delay * 2;
        return (int) ((int) state > delay ? (delay * 2 - state) : state) > 0 ? (int) ((int) state > delay ? (delay * 2 - state) : state) : 1;
    }

    private void drawCircle(Entity entity, float partialTicks, double rad, double height) {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glLineWidth(2.0f);
        glBegin(GL_LINE_STRIP);

        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;

        final float r = ((float) 1 / 255) * Color.WHITE.getRed();
        final float g = ((float) 1 / 255) * Color.WHITE.getGreen();
        final float b = ((float) 1 / 255) * Color.WHITE.getBlue();

        final double pix2 = Math.PI * 2.0D;

        for (int i = 0; i <= 90; ++i) {
            glVertex3d(x + rad * Math.cos(i * pix2 / 45), y + height, z + rad * Math.sin(i * pix2 / 45));
        }

        glEnd();
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }
    
    @Override
    public void onDisable() {
    	RenderUtils.resetPlayerYaw();
    	RenderUtils.resetPlayerPitch();
    	super.onDisable();
    }


}
