package badgamesinc.hypnotic.module.player;

import org.lwjgl.input.Mouse;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.combat.OtherAura;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;

public class AutoTool extends Mod {

	private BooleanSetting swords = new BooleanSetting("Swords", true);
	private BooleanSetting tools = new BooleanSetting("Tools", true);
	   
	public AutoTool() {
		super("AutoTool", 0, Category.PLAYER, "Automatically equips the best tool for the job");
		addSettings(swords, tools);
	}

	 @EventTarget
	   public void onEvent(EventMotionUpdate event) {
	      if (event.isPre()) {
	         if (this.tools.isEnabled() && mc.currentScreen == null && Mouse.isButtonDown(0) && mc.objectMouseOver != null) {
	            BlockPos blockPos = mc.objectMouseOver.getBlockPos();
	            if (blockPos != null) {
	               Block block = mc.theWorld.getBlockState(blockPos).getBlock();
	               float strength = 1.0F;
	               int bestToolSlot = -1;

	               for(int i = 0; i < 9; ++i) {
	                  ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
	                  if (itemStack != null && itemStack.getStrVsBlock(block) > strength) {
	                     strength = itemStack.getStrVsBlock(block);
	                     bestToolSlot = i;
	                  }
	               }

	               if (bestToolSlot != -1) {
	                  mc.thePlayer.inventory.currentItem = bestToolSlot;
	               }
	            }
	         }

	         if (Hypnotic.instance.moduleManager.getModule(OtherAura.class).target != null && Hypnotic.instance.moduleManager.getModule(OtherAura.class).isEnabled() && this.swords.isEnabled()) {
	            float damage = 1.0F;
	            int bestSwordSlot = -1;

	            for(int i = 0; i < 9; ++i) {
	               ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
	               if (itemStack != null && itemStack.getItem() instanceof ItemSword) {
	                  float damageLevel = InventoryManager.getDamageLevel(itemStack);
	                  if (damageLevel > damage) {
	                     damage = damageLevel;
	                     bestSwordSlot = i;
	                  }
	               }
	            }

	            if (bestSwordSlot != -1) {
	               mc.thePlayer.inventory.currentItem = bestSwordSlot;
	            }
	         }
	      }

	   }
}
