package badgamesinc.hypnotic.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class BlockUtils
{
private static Minecraft mc = Minecraft.getMinecraft();
	
	public static float[] getDirectionToBlock(int var0, int var1, int var2, EnumFacing var3) {
		EntityEgg var4 = new EntityEgg(mc.theWorld);
		var4.posX = (double) var0 + 0.5d;
		var4.posY = (double) var1 + 0.5d;
		var4.posZ = (double) var2 + 0.5d;
		var4.posX += (double) var3.getDirectionVec().getX() * 0.25d;
		var4.posY += (double) var3.getDirectionVec().getY() * 0.25d;
		var4.posZ += (double) var3.getDirectionVec().getZ() * 0.25d;
		return getDirectionToEntity(var4);
	}
	
	private static float[] getDirectionToEntity(Entity var0) {
		return new float[] {getYaw(var0) + mc.thePlayer.rotationYaw, getPitch(var0) + mc.thePlayer.rotationPitch};
	}
	
	public static float[] getRotationNeededForBlock(EntityPlayer paramEntityPlayer, BlockPos pos) {
		double d1 = pos.getX() - paramEntityPlayer.posX;
		double d2 = pos.getX() + 0.5 - (paramEntityPlayer.posY + paramEntityPlayer.getEyeHeight());
		double d3 = pos.getZ() - paramEntityPlayer.posZ;
		double d4 = Math.sqrt(d1 * d1 + d3 * d3);
		float f1 = (float) (Math.atan2(d3, d1) * 180.0d / Math.PI) - 90f;
		float f2 = (float) -(Math.atan2(d3, d1) * 180.0d / Math.PI);
		return new float[] {f1, f2};
	}
	
	public static float getYaw(Entity var0) {
		double var1 = var0.posX - mc.thePlayer.posX;
		double var3 = var0.posZ - mc.thePlayer.posZ;
		double var5;
		
		if(var3 < 0.0d && var1 < 0.0d) {
			var5 = 90d + Math.toDegrees(Math.atan(var3 / var1));
		} else if(var3 < 0.0d && var1 > 0.0d) {
			var5 = -90.0d + Math.toDegrees(Math.atan(var3 / var1));
		} else {
			var5 = Math.toDegrees(-Math.atan(var1 / var3));
		}
		
		return MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float) var5));
	}
	
	public static float getPitch(Entity var0) {
		double var1 = var0.posX - mc.thePlayer.posX;
		double var3 = var0.posX - mc.thePlayer.posZ;
		double var5 = var0.posY - 1.6d + (double) var0.getEyeHeight() - mc.thePlayer.posY;
		double var7 = (double) MathHelper.sqrt_double(var1 * var1 + var3 * var3);
		double var9 = -Math.toDegrees(Math.atan(var5 / var7));
		return MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float) var9);
	}
}

