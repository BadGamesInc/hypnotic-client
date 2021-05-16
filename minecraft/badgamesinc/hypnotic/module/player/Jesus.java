package badgamesinc.hypnotic.module.player;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.events.listeners.UpdateListener;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class Jesus extends Mod implements UpdateListener
{
	public Jesus() {
		super("Jesus", 0, Category.PLAYER, "Walk on water, like jesus");
	}

	private int ticksOutOfWater = 10;
	public int time = 0;
	public final int delay = 4;
	
	@Override
	public void onUpdate()
	{
		if(!mc.gameSettings.keyBindSneak.pressed)
			if(mc.thePlayer.isInWater())
			{
				mc.thePlayer.motionY = 0.11;
				ticksOutOfWater = 0;
			}else
			{
				if(ticksOutOfWater == 0)
					mc.thePlayer.motionY = 0.30;
				else if(ticksOutOfWater == 1)
					mc.thePlayer.motionY = 0;
				
				ticksOutOfWater++;
			}
	}
	
	public boolean isOverWater()
	{
		final EntityPlayerSP thePlayer = mc.thePlayer;
		
		boolean isOnWater = false;
		boolean isOnSolid = false;
		
		for(final Object o : mc.theWorld.getCollidingBoundingBoxes(thePlayer,
			thePlayer.getEntityBoundingBox().offset(0, -1.0D, 0)
				.contract(0.001D, 0D, 0.001D)))
		{
			final AxisAlignedBB bbox = (AxisAlignedBB)o;
			final BlockPos blockPos =
				new BlockPos(bbox.maxX - (bbox.maxX - bbox.minX) / 2.0,
					bbox.maxY - (bbox.maxY - bbox.minY) / 2.0,
					bbox.maxZ - (bbox.maxZ - bbox.minZ) / 2.0);
			final Block block = mc.theWorld.getBlockState(blockPos).getBlock();
			if(block.getMaterial() == Material.water
				|| block.getMaterial() == Material.lava)
				isOnWater = true;
			else if(block.getMaterial() != Material.air)
				isOnSolid = true;
		}
		
		return isOnWater && !isOnSolid;
	}
	
	public boolean shouldBeSolid()
	{
		return isEnabled() && !(mc.thePlayer == null)
			&& !(mc.thePlayer.fallDistance > 3)
			&& !mc.gameSettings.keyBindSneak.pressed && !mc.thePlayer.isInWater();
	}
}
