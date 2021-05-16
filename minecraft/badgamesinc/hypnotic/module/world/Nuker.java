package badgamesinc.hypnotic.module.world;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.Event;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.TimerUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class Nuker extends Mod {

	private int xPos;
	private int yPos;
	private int zPos;
	private static int radius = 4;
	public TimerUtils timer = new TimerUtils();
	
	public Nuker() {
		super("Nuker", 0, Category.WORLD, "Destroy lots of blocks");
	}
	
	public void setup()
	{
		Hypnotic.instance.setmgr.rSetting(new Setting("Break Delay", this, 2, 0, 10, false)); 
	}
	
	public double getSettingValue() {
		return Hypnotic.instance.setmgr.getSettingByName("Break Delay").getValDouble(); 	
	}
	
	@EventTarget
	public void eventMotionUpdate(EventMotionUpdate e) {
		if(e.getState() == Event.State.PRE) {
			if(!this.isEnabled())
				return;
			
			for(int x = -radius; x < radius; x++) {
				for(int y = radius; y > -radius; y--) {
					for(int z = -radius; z < radius; z++) {
						this.xPos = (int) (mc.thePlayer.posX + x);
						this.yPos = (int) (mc.thePlayer.posY + y);
						this.zPos = (int) (mc.thePlayer.posZ + z);
						
						BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
						Block block = mc.theWorld.getBlockState(blockPos).getBlock();
						
						float[] rots = this.getRotations(blockPos, EnumFacing.NORTH);
								
						if(block.getMaterial() == Material.air)
							continue;
						
						if(timer.hasTimeElapsed((long) (getSettingValue() * 1000), true) && getSettingValue() != 0) {
							e.setYaw(rots[0]);
							e.setPitch(rots[1]);
							RenderUtils.setCustomYaw(rots[0]);
							RenderUtils.setCustomPitch(rots[1]);
							mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
							mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
							mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
						} else if(getSettingValue() == 0) {
							e.setYaw(rots[0]);
							e.setPitch(rots[1]);
							RenderUtils.setCustomYaw(rots[0]);
							RenderUtils.setCustomPitch(rots[1]);
							mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
							mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
							mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
						}
					}
				}
			}
		}
	}
	
	public float[] getRotations(BlockPos paramBlockPos, EnumFacing paramEnumFacing) {
        double d1 = paramBlockPos.getX() + 0.5D - mc.thePlayer.posX + paramEnumFacing.getFrontOffsetX() / 2.0D;
        double d2 = paramBlockPos.getZ() + 0.5D - mc.thePlayer.posZ + paramEnumFacing.getFrontOffsetZ() / 2.0D;
        double d3 = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - (paramBlockPos.getY() + 0.5D);
        double d4 = MathHelper.sqrt_double(d1 * d1 + d2 * d2);
        float f1 = (float) (Math.atan2(d2, d1) * 180.0D / Math.PI) - 90.0F;
        float f2 = (float) (Math.atan2(d3, d4) * 180.0D / Math.PI);
        if (f1 < 0.0F) {
            f1 += 360.0F;
        }
        return new float[]{f1, f2};
    }
}
