package badgamesinc.hypnotic.module.player;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventPostMotionUpdate;
import badgamesinc.hypnotic.event.events.EventPreMotionUpdate;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.BlockUtils;
import badgamesinc.hypnotic.util.TimerUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Scaffold extends Mod{

	private BlockPos currentPos;
	private EnumFacing currentFacing;
	private boolean rotated = false;
	private TimerUtils timer = new TimerUtils();
	
	public Scaffold() {
		super("Scaffold", Keyboard.KEY_R, Category.PLAYER, "Places blocks underneath you");
	}
	
	@Override
	public void setup() {
		Hypnotic.instance.setmgr.rSetting(new Setting("Place Delay", this, 0, 0, 100, true));
		Hypnotic.instance.setmgr.rSetting(new Setting("Fast Tower", this, true));
	}
	
	public double placeDelayValue() {
		return Hypnotic.instance.setmgr.getSettingByName("Place Delay").getValDouble();
	}
	
	@Override
	public void onUpdate() {
		
	}
	
	@EventTarget
	public void onPreMotion(EventPreMotionUpdate e) {
		rotated = false;
		currentPos = null;
		currentFacing = null;
		
		BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0d, mc.thePlayer.posZ);
		if(mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
			setBlockAndFacing(pos);
			
			if(currentPos != null) {
				float facing[] = BlockUtils.getDirectionToBlock(currentPos.getX(), currentPos.getY(), currentPos.getZ(), currentFacing);
				
				float yaw = facing[0];
				float pitch = Math.min(90, facing[1] + 9);
				
				rotated = true;
				mc.thePlayer.setRotationYawHead(yaw);
				e.setPitch(pitch);
				if(Hypnotic.instance.setmgr.getSettingByName("Fast Tower").getValBoolean() && mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock && mc.thePlayer.moveForward == 0 && mc.thePlayer.moveStrafing == 0) {
					mc.thePlayer.motionY = 0.44f;
				}
			} 
		}
	}
	
	@EventTarget
	public void onPostMotion(EventPostMotionUpdate e) {
		if(timer.hasTimeElapsed((int) placeDelayValue(), true)) {
			if(mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
				if(mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem(), currentPos, currentFacing, new Vec3(currentPos.getX(), currentPos.getY(), currentPos.getZ()))) {
					mc.thePlayer.swingItem();
				}
			}
		}
	}
	
	private void setBlockAndFacing(BlockPos var1) {
		if(mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
			currentPos = var1.add(0, -1, 0);
			currentFacing = EnumFacing.UP;
		} else if(mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
			currentPos = var1.add(-1, 0, 0);
			currentFacing = EnumFacing.EAST;
		} else if(mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
			currentPos = var1.add(1, 0, 0);
			currentFacing = EnumFacing.WEST;
		} else if(mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
			currentPos = var1.add(0, 0, -1);
			currentFacing = EnumFacing.SOUTH;
		} else if(mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
			currentPos = var1.add(0, 0, 1);
			currentFacing = EnumFacing.NORTH;
		} else {
			currentPos = null;
			currentFacing = null;
		}
	}
}
