package badgamesinc.hypnotic.module.world;

import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Nuker extends Mod {

	private int xPos;
	private int yPos;
	private int zPos;
	private static int radius = 4;
	
	public Nuker() {
		super("Nuker", 0, Category.WORLD, "Destroy lots of blocks");
	}
	
	@Override
	public void onUpdate() {
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
					
					if(block.getMaterial() == Material.air)
						continue;
					
					
					mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
					mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
				}
			}
		}
	}
}
