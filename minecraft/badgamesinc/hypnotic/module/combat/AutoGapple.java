package badgamesinc.hypnotic.module.combat;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.events.listeners.UpdateListener;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.TimerUtils;
import net.minecraft.block.BlockContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class AutoGapple extends Mod implements UpdateListener{

	private int oldSlot;
	public TimerUtils timerUtils = new TimerUtils();
	
	public NumberSetting minHealthVal = new NumberSetting("Min Health", 8, 1, 19, 0.1);
    
    public AutoGapple() {
        super("AutoGapple", 0, Category.COMBAT, "Automatically eats gapps for you when your health is low");
        addSettings(minHealthVal);
        this.oldSlot = -1;
    }
    
    @Override
    public void onUpdate() {
    	this.setDisplayName("AutoGapple " + ColorUtils.white + "[Min: " + minHealthVal.getValue() + "]");
    	if (mc.thePlayer.isPotionActive(Potion.regeneration.id)) {
    		return;
    	}
        for (int i = 0; i < 36; ++i) {
            final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if (stack == null || stack.getItem() != Items.golden_apple) {}
        }
        final int gapInHotbar = this.findGap(0, 9);
        if (gapInHotbar == -1) {
            this.stopIfEating();
            final int gapInInventory = this.findGap(9, 36);
            return;
        }
        if (!this.shouldEatGap()) {
            this.stopIfEating();
            return;
        }
        if (this.oldSlot == -1) {
            this.oldSlot = mc.thePlayer.inventory.currentItem;
        }
        if (timerUtils.hasTimeElapsed(250L, true)) {
	        mc.thePlayer.inventory.currentItem = gapInHotbar;
	        mc.gameSettings.keyBindUseItem.pressed = true;
	        mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
        }
    }
    
    private int findGap(final int startSlot, final int endSlot) {
        for (int i = startSlot; i < endSlot; ++i) {
            final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof ItemAppleGold) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean shouldEatGap() {
        if (mc.thePlayer.getHealth() > this.minHealthVal.getValue()) {
            return false;
        }
        if (mc.currentScreen == null && mc.objectMouseOver != null) {
            final Entity entity = mc.objectMouseOver.entityHit;
            if (entity instanceof EntityVillager || entity instanceof EntityTameable) {
                return false;
            }
            if (mc.objectMouseOver.getBlockPos() != null && mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() instanceof BlockContainer) {
                return false;
            }
        }
        return true;
    }
    
    private void stopIfEating() {
        if (this.oldSlot == -1) {
            return;
        }
        mc.gameSettings.keyBindUseItem.pressed = false;
        mc.thePlayer.inventory.currentItem = this.oldSlot;
        this.oldSlot = -1;
    }
}
