package badgamesinc.hypnotic.module.combat;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.TimerUtils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class AutoPot extends Mod
{
    public TimerUtils timer;
    public BooleanSetting speedPot = new BooleanSetting("Speed Pots", true);
    public NumberSetting health = new NumberSetting("Health", 10, 1, 19, 1);
    
    public AutoPot() {
        super("AutoPot", 0, Category.COMBAT, "Automatically splashes healing pots for you");
        this.timer = new TimerUtils();
        addSettings(speedPot, health);
    }
    
    public double getHealthSettingValue() {
        return health.getValue();
    }
    
    @Override
    public void onUpdate() {
        if (mc.currentScreen instanceof GuiContainer && !(mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        final int speedPotionInInventory = this.findSpeedPotion(9, 36);
        final int speedPotionInHotbar = this.findSpeedPotion(36, 45);
        if (speedPotionInInventory == -1 && speedPotionInHotbar == -1) {
            return;
        }
        if (this.timer.hasTimeElapsed(250L, true)) {
            if (speedPotionInHotbar != -1) {
                final int oldSlot = mc.thePlayer.inventory.currentItem;
                final NetHandlerPlayClient sendQueue = mc.thePlayer.sendQueue;
                sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, 90.0f, mc.thePlayer.onGround));
                sendQueue.addToSendQueue(new C09PacketHeldItemChange(speedPotionInHotbar - 36));
                mc.playerController.updateController();
                sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventoryContainer.getSlot(speedPotionInHotbar).getStack()));
                sendQueue.addToSendQueue(new C09PacketHeldItemChange(oldSlot));
                sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
            }
            else {
                mc.playerController.windowClick(0, speedPotionInInventory, 0, 1, mc.thePlayer);
            }
        }
        if (mc.thePlayer.getHealth() >= (float)this.getHealthSettingValue()) {
            return;
        }
        final int potionInInventory = this.findHealthPotion(9, 36);
        final int potionInHotbar = this.findHealthPotion(36, 45);
        if (potionInInventory == -1 && potionInHotbar == -1) {
            return;
        }
        if (this.timer.hasTimeElapsed(250L, true)) {
            if (potionInHotbar != -1) {
                final int oldSlot = mc.thePlayer.inventory.currentItem;
                final NetHandlerPlayClient sendQueue = mc.thePlayer.sendQueue;
                sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, 90.0f, mc.thePlayer.onGround));
                sendQueue.addToSendQueue(new C09PacketHeldItemChange(potionInHotbar - 36));
                mc.playerController.updateController();
                sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventoryContainer.getSlot(potionInHotbar).getStack()));
                sendQueue.addToSendQueue(new C09PacketHeldItemChange(oldSlot));
                sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
            }
            else {
                mc.playerController.windowClick(0, potionInInventory, 0, 1, mc.thePlayer);
            }
        }
    }
    
    private int findHealthPotion(final int startSlot, final int endSlot) {
        for (int i = startSlot; i < endSlot; ++i) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() == Items.potionitem && ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : ((ItemPotion)stack.getItem()).getEffects(stack)) {
                	
                    if (((PotionEffect)o).getPotionID() == Potion.heal.id || ((PotionEffect)o).getPotionID() == Potion.regeneration.id) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    private int findSpeedPotion(final int startSlot, final int endSlot) {
    	for (int i = startSlot; i < endSlot; ++i) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() == Items.potionitem && ItemPotion.isSplash(stack.getItemDamage())) {
            	if (speedPot.isEnabled()) {
            		if (mc.thePlayer.isPotionActive(Potion.moveSpeed.id)) {
                		continue;
                	}
            		for (final Object o : ((ItemPotion)stack.getItem()).getEffects(stack)) {
                        if (((PotionEffect)o).getPotionID() == Potion.moveSpeed.id) {
                            return i;
                        }
                    }
            	}
            }
        }
        return -1;
    }
}
