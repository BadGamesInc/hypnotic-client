package badgamesinc.hypnotic.module.player;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex3d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.Event;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event2D;
import badgamesinc.hypnotic.event.events.Event3D;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.event.events.EventPlayerDeath;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.ModeSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ColorUtil;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.MoveUtils;
import badgamesinc.hypnotic.util.MovementUtils;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.Rotation;
import badgamesinc.hypnotic.util.RotationUtils;
import badgamesinc.hypnotic.util.SetBlockAndFacing;
import badgamesinc.hypnotic.util.TimeHelper;
import badgamesinc.hypnotic.util.Timer;
import badgamesinc.hypnotic.util.WorldUtils;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;


public class Scaffold extends Mod {
    private float[] rotations = new float[2];
    private static final Map<Integer, Boolean> glCapMap = new HashMap<>();
    private List<Block> badBlocks = Arrays.asList(Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.trapped_chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.tallgrass, Blocks.tripwire, Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily, Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, Blocks.sand, Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.web, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence, Blocks.anvil);
    private BlockData blockData;
    private BooleanSetting blockFly = new BooleanSetting("Downwards", true);
    private BooleanSetting tower = new BooleanSetting("Tower", true);

    private BooleanSetting keeprots = new BooleanSetting("Keep Rots", true);
    private BooleanSetting towermove = new BooleanSetting("TowerMove", false);
    private BooleanSetting swing = new BooleanSetting("Swing", false);
    private BooleanSetting keepY = new BooleanSetting("Keep Y", false);
    int stage = 0;

    public NumberSetting delay = new NumberSetting("Delay", 0, 0, 100, 10);
    public BooleanSetting keepSprint = new BooleanSetting("Keep Sprint", true);
    public static boolean isPlaceTick = false;
    public static boolean stopWalk = false;
    public ModeSetting scaffoldMode = new ModeSetting("Mode", "Hypixel", "Hypixel", "AAC");
    private double startY;
    public TimeHelper towerTimer = new TimeHelper();
    private final GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 18, false, false, false);
    private int count;
    private BlockPos currentPos;
    private EnumFacing currentFacing;
    private boolean rotated = false;
    private TimeHelper timer = new TimeHelper();
    public BooleanSetting raycast = new BooleanSetting("Raycast", false);
    public BooleanSetting legit = new BooleanSetting("Legit", true);
    public BooleanSetting boost = new BooleanSetting("Boost", false);
    public BooleanSetting redeskyBoost = new BooleanSetting("Timer Boost", false);
    public BooleanSetting safeWalk = new BooleanSetting("Safewalk", false);
    public BooleanSetting eagle = new BooleanSetting("Eagle", false);
    public BooleanSetting hypixelSprintBoost = new BooleanSetting("Hypixel Boost", false);
    private static Timer sprintTimer = new Timer();
    
    private BlockPos espPos;

    float sprintTicks = 0;
    float oldPitch = 0;
    private RotationUtils RayCastUtil;
    int prevSlot = 0;

    public Scaffold() {
        super("Scaffold", Keyboard.KEY_R, Category.PLAYER, "Places blocks underneath you"); 
        addSettings(scaffoldMode, delay, keeprots, blockFly, boost, redeskyBoost, keepY, raycast, keepSprint, legit, swing, tower, towermove, safeWalk, eagle, hypixelSprintBoost);
    }

    float yaw = 0;
    float pitch = 0;

    private boolean isBlockUnder() {
        for (int i = (int) (mc.thePlayer.posY - 1.0); i > 0; --i) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
            if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) continue;
            return true;
        }
        return false;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        timer.reset();
        slotTimer.reset();
        ticks = 0;
        startY = mc.thePlayer.posY;
        prevSlot = mc.thePlayer.inventory.currentItem;
        sprintTicks = 0;
    }
    
    @Override
    public void onUpdate() {
    	this.setDisplayName("Scaffold " + ColorUtils.white + "[" + scaffoldMode.getSelected() + "]");
    	 espPos = new BlockPos(mc.thePlayer.posX, keepY.isEnabled() ? this.startY - 1 : mc.thePlayer.posY - ((Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && (mc.thePlayer.onGround || mc.thePlayer.fallDistance > 0.3) && MovementUtils.isMoving() && blockFly.isEnabled()) ? 2 : 1), mc.thePlayer.posZ);
    }

    float lastYaw = 0;

    public void fakeJump() {
        mc.thePlayer.isAirBorne = true;
        mc.thePlayer.triggerAchievement(StatList.jumpStat);

    }

    int ticks = 0;

    @EventTarget
    public void onMotionUpdate(EventMotionUpdate event) {
        if (event.getState() == Event.State.PRE) {
            if (!this.keepSprint.isEnabled()) {
                mc.thePlayer.setSprinting(false);
                mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                mc.gameSettings.keyBindSprint.pressed = false;

            }
            if (scaffoldMode.getSelected().equalsIgnoreCase("Hypixel")) {
                int slot = this.getSlot();
                //if (mc.thePlayer.onGround)
                	//MovementUtils.setMotion(0.09);
                this.stopWalk = (getBlockCount() == 0 || slot == -1) && safeWalk.isEnabled();
                this.isPlaceTick = keeprots.isEnabled() ? blockData != null && slot != -1 : blockData != null && slot != -1 && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer).add(0, -1, 0)).getBlock() == Blocks.air;
                if (slot == -1) {
                    moveBlocksToHotbar();

                    return;
                }
                if (!keepSprint.isEnabled()) {
                    mc.thePlayer.setSprinting(false);
                    mc.gameSettings.keyBindSprint.pressed = false;
                    mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                }
                if (scaffoldMode.is("Hypixel") && hypixelSprintBoost.isEnabled()) {
                	if (sprintTicks < 30) {
	                	sprintTicks++;
	                	if (mc.thePlayer.moveForward != 0 && mc.thePlayer.getFoodStats().getFoodLevel() > 6)
	                		mc.thePlayer.setSprinting(true);
                	} else {
                		mc.thePlayer.setSprinting(false);
                	}
                	
                	/*if (sprintTimer.hasTimeElapsed(10000, true) && sprintTicks >= 150) {
                		sprintTicks = 0;
                	}*/
                }
                this.blockData = getBlockData();
                if (this.blockData == null) {
                    return;
                }

                if (mc.gameSettings.keyBindJump.isKeyDown() && tower.isEnabled() && (this.towermove.isEnabled() || !MoveUtils.isMoving()) && !mc.thePlayer.isPotionActive(Potion.jump)) {
                        EntityPlayerSP player = mc.thePlayer;
                                if (!MoveUtils.isOnGround(0.79) || mc.thePlayer.onGround) {
                                    player.motionY = redeskyBoost.isEnabled() ? 0.6 : 0.41985;
                                    stage = 1;
                                }
                                if(towerTimer.hasReached(1500)){
                                    towerTimer.reset();
                                    player.motionY = -1;
                                }


                } else {
                    towerTimer.reset();
                }
                
                if (this.isPlaceTick) {
                    Rotation targetRotation = new Rotation(badgamesinc.hypnotic.util.SetBlockAndFacing.BlockUtil.getDirectionToBlock(blockData.getPosition().getX(), blockData.getPosition().getY(), blockData.getPosition().getZ(), blockData.getFacing())[0], 79.44f);
                    Rotation limitedRotation = SetBlockAndFacing.BlockUtil.limitAngleChange(new Rotation(yaw, event.getPitch()), targetRotation, (float) ThreadLocalRandom.current().nextDouble(20, 30));
                    yaw = limitedRotation.getYaw();
                    pitch = limitedRotation.getPitch();
                    event.setYaw(yaw);
                    event.setPitch(79.44f);
                    if (mc.thePlayer.fallDistance > 2) {
                		RenderUtils.resetPlayerYaw();
                		RenderUtils.resetPlayerPitch();
                	} else {
	                    RenderUtils.setCustomPitch(50);
	                    RenderUtils.setCustomYaw(event.getYaw());
                	}
                    
                    if (redeskyBoost.isEnabled())
                    	mc.timer.timerSpeed = 1.8f;

                }
            } else {
            	
            	if (eagle.isEnabled()) {
                    BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
                    boolean air = mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir;
                    if (air) {
                        setSneaking(true);
                    } else {
                        setSneaking(false);
                    }

                } else {
                	BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
                    boolean air = mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir;
                    if (air) {
                        event.setSneaking(true);
                    } else {
                        event.setSneaking(false);
                    }
                }
            	if (mc.thePlayer.isPotionActive(Potion.moveSpeed))
            		mc.thePlayer.setSprinting(false);
                rotated = false;
                currentPos = null;
                currentFacing = null;

                BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
                if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
                    setBlockAndFacing(pos);

                    if (currentPos != null) {
                        float facing[] = SetBlockAndFacing.BlockUtil.getDirectionToBlock(currentPos.getX(), currentPos.getY(), currentPos.getZ(), currentFacing);

                        float yaw = facing[0] + randomNumber(3, -3);
                        float pitch = Math.min(90, facing[1] + 9 + randomNumber(3, -3));

                        rotations[0] = yaw;
                        rotations[1] = pitch;

                        rotated = !raycast.isEnabled() || rayTrace(yaw, pitch);
                        if (legit.isEnabled()) {
                            mc.thePlayer.rotationYaw = rotations[0];
                            mc.thePlayer.rotationPitch = rotations[1];
                        }
                        event.setYaw(yaw);
                        event.setPitch(pitch);
                    }
                } else {
                    if (keeprots.isEnabled()) {
                        if (legit.isEnabled()) {
                            mc.thePlayer.rotationYaw = rotations[0];
                            mc.thePlayer.rotationPitch = rotations[1];
                        }
                        event.setYaw(rotations[0]);
                        event.setPitch(rotations[1]);
                    }
                }
                RenderUtils.setCustomYaw(rotations[0]);
                RenderUtils.setCustomPitch(50);
                mc.timer.timerSpeed = 1f;


            }
        } else {
            if (scaffoldMode.getSelected().equalsIgnoreCase("Hypixel")) {
                int slot = this.getSlot();
                BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);
                for (int i = 0; i < 9; i++) {
                    if (mc.thePlayer.inventory.getStackInSlot(i) == null)
                        continue;
                    if (mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem = i));
                    }
                }
                if (slot != -1 && this.blockData != null) {
                   // final int currentSlot = mc.thePlayer.inventory.currentItem;
                    if (pos.getBlock() instanceof BlockAir) {
                        if (this.getPlaceBlock(this.blockData.blockPos, this.blockData.getFacing())) {
                            if (boost.isEnabled()) {
                            	if (MoveUtils.isMoving() && !mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.onGround)
                            		MoveUtils.setMotion((1.035 / 2.5) * (MoveUtils.getSpeedEffect() > 0 ? 1.1 : 1.0));
                            }
                           // mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(currentSlot));
                        }
                    }else{
                        mc.timer.timerSpeed = 1.0f;
                    }
                    
                    //mc.thePlayer.inventory.currentItem = currentSlot;
                }
            } else {
                for (int i = 0; i < 9; i++) {
                    if (mc.thePlayer.inventory.getStackInSlot(i) == null)
                        continue;
                    if (mc.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem = i));
                    }
                }
                if (currentPos != null) {
                    if (timer.hasReached((long) this.delay.getValue()) && rotated) {
                        if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
                            if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem(), currentPos, currentFacing, new Vec3(currentPos.getX() * 0.5, currentPos.getY() * 0.5, currentPos.getZ() * 0.5))) {
                                timer.reset();
                                if (swing.isEnabled()) {
                                    mc.thePlayer.swingItem();
                                } else {
                                    mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
                                }


                            }
                        }
                    }
                }
            }
        }
    }

    private boolean getPlaceBlock(final BlockPos pos, final EnumFacing facing) {
        final Vec3 eyesPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        Vec3i data = this.blockData.getFacing().getDirectionVec();
        if (timer.hasReached((long) delay.getValue())) {
            if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, facing, getVec3(new BlockData(pos, facing)))) {
                if (this.swing.isEnabled()) {
                    mc.thePlayer.swingItem();
                } else {
                    mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                }

                timer.reset();
                return true;
            }


        }
        return false;
    }

    private Vec3 getVec3(BlockData data) {
        BlockPos pos = data.getPosition();
        EnumFacing face = data.getFacing();
        double x = (double) pos.getX() + 0.5D;
        double y = (double) pos.getY() + 0.5D;
        double z = (double) pos.getZ() + 0.5D;
        x += (double) face.getFrontOffsetX() / 2.0D;
        z += (double) face.getFrontOffsetZ() / 2.0D;
        y += (double) face.getFrontOffsetY() / 2.0D;
        if (face != EnumFacing.UP && face != EnumFacing.DOWN) {
            y += this.randomNumber(0.49D, 0.5D);
        } else {
            x += this.randomNumber(0.3D, -0.3D);
            z += this.randomNumber(0.3D, -0.3D);
        }

        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += this.randomNumber(0.3D, -0.3D);
        }

        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += this.randomNumber(0.3D, -0.3D);
        }

        return new Vec3(x, y, z);
    }

    private double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    private boolean rayTrace(float yaw, float pitch) {
        Vec3 vec3 = mc.thePlayer.getPositionEyes(1.0f);
        Vec3 vec31 = RayCastUtil.getVectorForRotation(new float[]{yaw, pitch});
        Vec3 vec32 = vec3.addVector(vec31.xCoord * 5, vec31.yCoord * 5, vec31.zCoord * 5);


        MovingObjectPosition result = mc.theWorld.rayTraceBlocks(vec3, vec32, false);


        return result != null && result.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && currentPos.equals(result.getBlockPos());
    }

    static Random rng = new Random();

    public static int getRandom(final int floor, final int cap) {
        return floor + rng.nextInt(cap - floor + 1);
    }

    @EventTarget
    public void on3D(Event3D event) {
    	/*
    	Block air = Blocks.air;
    	if (getBlockCount() > 0 && ((Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && blockFly.isEnabled() && !Hypnotic.instance.moduleManager.flight.isEnabled()) ? true : (keepY.isEnabled() ? true : isBlockUnder())))
        for (int i = 0; i < 5; i++) {
			RenderUtils.drawLine(espPos.getX(), espPos.getY(), espPos.getZ(), espPos.getX() + 1, espPos.getY(), espPos.getZ());
			RenderUtils.drawLine(espPos.getX(), espPos.getY() + 1, espPos.getZ(), espPos.getX() + 1, espPos.getY() + 1, espPos.getZ());
			RenderUtils.drawLine(espPos.getX(), espPos.getY(), espPos.getZ(), espPos.getX(), espPos.getY(), espPos.getZ() + 1);
			RenderUtils.drawLine(espPos.getX(), espPos.getY() + 1, espPos.getZ(), espPos.getX(), espPos.getY() + 1, espPos.getZ() + 1);
			RenderUtils.drawLine(espPos.getX(), espPos.getY(), espPos.getZ(), espPos.getX(), espPos.getY() + 1, espPos.getZ());
			RenderUtils.drawLine(espPos.getX(), espPos.getY() + 1, espPos.getZ(), espPos.getX(), espPos.getY() + 1, espPos.getZ());
			RenderUtils.drawLine(espPos.getX() + 1, espPos.getY(), espPos.getZ(), espPos.getX() + 1, espPos.getY() + 1, espPos.getZ());
			RenderUtils.drawLine(espPos.getX() + 1, espPos.getY() + 1, espPos.getZ(), espPos.getX() + 1, espPos.getY() + 1, espPos.getZ());
			RenderUtils.drawLine(espPos.getX(), espPos.getY(), espPos.getZ() + 1, espPos.getX(), espPos.getY() + 1, espPos.getZ() + 1);
			RenderUtils.drawLine(espPos.getX(), espPos.getY() + 1, espPos.getZ() + 1, espPos.getX(), espPos.getY() + 1, espPos.getZ() + 1);
			RenderUtils.drawLine(espPos.getX() + 1, espPos.getY(), espPos.getZ() + 1, espPos.getX(), espPos.getY(), espPos.getZ() + 1);
			RenderUtils.drawLine(espPos.getX() + 1, espPos.getY() + 1, espPos.getZ() + 1, espPos.getX(), espPos.getY() + 1, espPos.getZ() + 1);
			RenderUtils.drawLine(espPos.getX() + 1, espPos.getY(), espPos.getZ() + 1, espPos.getX() + 1, espPos.getY() + 1, espPos.getZ() + 1);
			RenderUtils.drawLine(espPos.getX() + 1, espPos.getY() + 1, espPos.getZ(), espPos.getX() + 1, espPos.getY() + 1, espPos.getZ() + 1);
			RenderUtils.drawLine(espPos.getX() + 1, espPos.getY(), espPos.getZ(), espPos.getX() + 1, espPos.getY(), espPos.getZ() + 1);
		}*/
    	//RenderUtils.blockESPBox(espPos, -1);
    	//RenderUtils.drawSolidBlockESP(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posX, 255, 255, 255, 255);
       // drawCircle(mc.thePlayer, event.getPartialTicks(), 0.5);
       // drawCircle(mc.thePlayer, event.getPartialTicks(), 0.4);
    }
    
    @EventTarget
    public void onDeath(EventPlayerDeath event) {
    	RenderUtils.resetPlayerYaw();
    	RenderUtils.resetPlayerPitch();
    }
    
    private void drawCircle(Entity entity, float partialTicks, double rad) {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glLineWidth(3.0f);
        glBegin(GL_LINE_STRIP);

        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;

        final float r = ((float) 1 / 255) * Color.WHITE.getRed();
        final float g = ((float) 1 / 255) * Color.WHITE.getGreen();
        final float b = ((float) 1 / 255) * Color.WHITE.getBlue();

        final double pix2 = Math.PI * 2.0D;

        for (int i = 0; i <= 6; ++i) {
            GlStateManager.color(rainbow(i * 100).getRed(), rainbow(i * 100).getGreen(), rainbow(i * 100).getRed(), 255);
            glVertex3d(x + rad * Math.cos(i * pix2 / 6.0), y, z + rad * Math.sin(i * pix2 / 6.0));
        }

        glEnd();
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }
    public static Color rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f);
    }

    public void setBlockAndFacing(BlockPos var1) {

        if (this.mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, 0);
            currentFacing = EnumFacing.UP;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {

            currentPos = var1.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;

        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {

            currentPos = var1.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;

        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, -1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, 1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 0, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, -1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 0, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, 1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, 0);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, 0);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, -1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, 1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, -1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, 1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-2, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(2, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 0, -2);
            currentFacing = EnumFacing.SOUTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 0, 2);
            currentFacing = EnumFacing.NORTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(-2, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, -2);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-2, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, 2);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(2, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, -2);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(2, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, 2);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, 0);
            currentFacing = EnumFacing.DOWN;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, 0);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, 0);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, -1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, 1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, -1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, 1);
            currentFacing = EnumFacing.WEST;
        }
    }

    public void getExpandBlock(BlockPos var1) {

        if (this.mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, 0);
            currentFacing = EnumFacing.UP;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {

            currentPos = var1.add(0, 0, -1);
            currentFacing = EnumFacing.SOUTH;

        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {

            currentPos = var1.add(0, 0, 1);
            currentFacing = EnumFacing.NORTH;

        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, -1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 0, 1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 0, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, -1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 0, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 0, 1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, 0);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, -1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, 0);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, -1, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, -1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, -1, 1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, -1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, -1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, -1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, -1, 1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-2, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, 0);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(2, 0, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, 0);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 0, -2);
            currentFacing = EnumFacing.SOUTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 0, 2);
            currentFacing = EnumFacing.NORTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(-2, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, -2);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-2, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(-2, 0, 2);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(2, 0, -2)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, -2);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(2, 0, 2)).getBlock() != Blocks.air) {
            currentPos = var1.add(2, 0, 2);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, 0);
            currentFacing = EnumFacing.DOWN;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, 0);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 1, 0)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, 0);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, -1);
            currentFacing = EnumFacing.SOUTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(0, 1, 1);
            currentFacing = EnumFacing.NORTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, -1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(-1, 1, 1);
            currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 1, -1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, -1);
            currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 1, 1)).getBlock() != Blocks.air) {
            currentPos = var1.add(1, 1, 1);
            currentFacing = EnumFacing.WEST;
        }
    }

    private float[] aimAtLocation(BlockPos paramBlockPos, EnumFacing paramEnumFacing) {
        double d1 = paramBlockPos.getX() + 0.5D - mc.thePlayer.posX + paramEnumFacing.getFrontOffsetX() / 2.0D;
        double d2 = paramBlockPos.getZ() + 0.5D - mc.thePlayer.posZ + paramEnumFacing.getFrontOffsetZ() / 2.0D;
        double d3 = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - (paramBlockPos.getY() + 0.5D);
        double d4 = MathHelper.sqrt_double(d1 * d1 + d2 * d2);
        float f1 = (float) (Math.atan2(d2, d1) * 180.0D / 3.141592653589793D) - 90.0F;
        float f2 = (float) (Math.atan2(d3, d4) * 180.0D / 3.141592653589793D);
        if (f1 < 0.0F) {
            f1 += 360.0F;
        }
        return new float[]{f1, f2};
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.setSneaking(false);
        mc.timer.timerSpeed = 1f;
        RenderUtils.resetPlayerYaw();
        RenderUtils.resetPlayerPitch();
        mc.thePlayer.inventory.currentItem = prevSlot;
    }

    private void setSneaking(boolean b) {
        KeyBinding sneakBinding = mc.gameSettings.keyBindSneak;
        mc.gameSettings.keyBindSneak.pressed = b;
    }

    public BlockData getBlockData() {
        final EnumFacing[] invert = {EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST};
        double yValue = 0;
        if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()) && !mc.gameSettings.keyBindJump.isKeyDown() && blockFly.isEnabled()) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
            yValue -= 0.6;
        }
        BlockPos aa = new BlockPos(mc.thePlayer.getPositionVector()).offset(EnumFacing.DOWN).add(0, yValue, 0);
        BlockPos playerpos = aa;

        boolean tower = !this.towermove.isEnabled() && this.tower.isEnabled() && !MoveUtils.isMoving();
        if (!this.blockFly.isEnabled() && this.keepY.isEnabled() && !tower) {
            playerpos = new BlockPos(new Vec3(mc.thePlayer.getPositionVector().xCoord, this.startY, mc.thePlayer.getPositionVector().zCoord)).offset(EnumFacing.DOWN);
        } else {
            this.startY = mc.thePlayer.posY;
        }

        for (EnumFacing facing : EnumFacing.values()) {
            if (playerpos.offset(facing).getBlock().getMaterial() != Material.air) {
                return new BlockData(playerpos.offset(facing), invert[facing.ordinal()]);
            }
        }
        final BlockPos[] addons = {
                new BlockPos(-1, 0, 0),
                new BlockPos(1, 0, 0),
                new BlockPos(0, 0, -1),
                new BlockPos(0, 0, 1)};

        for (int length2 = addons.length, j = 0; j < length2; ++j) {
            final BlockPos offsetPos = playerpos.add(addons[j].getX(), 0, addons[j].getZ());
            if (mc.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
                for (int k = 0; k < EnumFacing.values().length; ++k) {
                    if (mc.theWorld.getBlockState(offsetPos.offset(EnumFacing.values()[k])).getBlock().getMaterial() != Material.air) {

                        return new BlockData(offsetPos.offset(EnumFacing.values()[k]), invert[EnumFacing.values()[k].ordinal()]);
                    }
                }
            }
        }

        return null;
    }

    int slotIndex = 0;
    TimeHelper slotTimer = new TimeHelper();

    public int getSlot() {
        ArrayList<Integer> slots = new ArrayList<>();
        for (int k = 0; k < 9; ++k) {
            final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
            if (itemStack != null && this.isValid(itemStack) && itemStack.stackSize >= 1) {
                slots.add(k);
            }
        }
        if (slots.isEmpty()) {
            return -1;
        }
        if (slotTimer.hasReached(150)) {
            if (slotIndex >= slots.size() || slotIndex == slots.size() - 1) {
                slotIndex = 0;
            } else {
                slotIndex++;
            }
            slotTimer.reset();
        }
        return slots.get(slotIndex);
    }

    @EventTarget
    public void on2D(Event2D event) {
    	fontRenderer.drawCenteredString(getBlockCount() + ColorUtils.white + " Blocks left", GuiScreen.width / 2 + 50, GuiScreen.height / 2 - 5, (Hypnotic.instance.moduleManager.arrayMod.colorMode.is("Rainbow") ? ColorUtils.rainbow(4, 0.5f, 0.5f) : ColorUtil.getClickGUIColor().getRGB()), true);
    }
    
    public static void renderItem(ItemStack stack, int x, int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().zLevel = -100.0f;
        GlStateManager.scale(1.0f, 1.0f, 0.01f);
        GlStateManager.enableDepth();
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stack, x, y + 8);
        Minecraft.getMinecraft().getRenderItem().zLevel = 0.0f;
        GlStateManager.scale(1.0f, 1.0f, 1.0f);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5, 0.5, 0.5);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GL11.glPopMatrix();
    }


    private boolean isValid(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemBlock) {
            boolean isBad = false;

            ItemBlock block = (ItemBlock) itemStack.getItem();
            for (int i = 0; i < this.badBlocks.size(); i++) {
                if (block.getBlock().equals(this.badBlocks.get(i))) {
                    isBad = true;
                }
            }

            return !isBad;
        }
        return false;
    }

    public int getBlockCount() {
        int count = 0;
        for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; ++k) {
            final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
            if (itemStack != null && this.isValid(itemStack) && itemStack.stackSize >= 1) {
                count += itemStack.stackSize;
            }
        }
        return count;
    }

    public class BlockData {
        private BlockPos blockPos;
        private EnumFacing enumFacing;

        public BlockData(final BlockPos blockPos, final EnumFacing enumFacing) {
            this.blockPos = blockPos;
            this.enumFacing = enumFacing;
        }

        public EnumFacing getFacing() {
            return this.enumFacing;
        }

        public BlockPos getPosition() {
            return this.blockPos;
        }
    }

    private void moveBlocksToHotbar() {
        boolean added = false;
        if (!isHotbarFull()) {
            for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; ++k) {
                if (k > 8 && !added) {
                    final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
                    if (itemStack != null && this.isValid(itemStack)) {
                        shiftClick(k);
                        added = true;
                    }
                }
            }
        }
    }

    public boolean isHotbarFull() {
        int count = 0;
        for (int k = 0; k < 9; ++k) {
            final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[k];
            if (itemStack != null) {
                count++;
            }
        }
        return count == 8;
    }

    public float setSmooth(float current, float target, float speed) {
        if (target - current > 0) {
            current -= speed;
        } else {
            current += speed;
        }
        return current;
    }

    public static void shiftClick(int slot) {
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.getMinecraft().thePlayer);
    }

    public static int randomNumber(int max, int min) {
        return Math.round(min + (float) Math.random() * ((max - min)));
    }
}