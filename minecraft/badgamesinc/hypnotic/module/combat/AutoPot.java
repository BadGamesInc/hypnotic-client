// 
// Decompiled by Procyon v0.5.36
// 

package badgamesinc.hypnotic.module.combat;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.events.listeners.UpdateListener;
import badgamesinc.hypnotic.gui.clickgui.settings.Setting;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.TimerUtils;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
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
    
    public AutoPot() {
        super("AutoPot", 0, Category.COMBAT, "Automatically splashes healing pots for you");
        this.timer = new TimerUtils();
    }
    
    @Override
    public void setup() {
        Hypnotic.instance.setmgr.rSetting(new Setting("Health", this, 10.0, 5.0, 18.0, true));
    }
    
    public double getHealthSettingValue() {
        return Hypnotic.instance.setmgr.getSettingByName("Health").getValDouble();
    }
    
    @Override
    public void onUpdate() {
        if (mc.currentScreen instanceof GuiContainer && !(mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        if (mc.thePlayer.getHealth() >= (float)this.getHealthSettingValue()) {
            return;
        }
        final int potionInInventory = this.findPotion(9, 36);
        final int potionInHotbar = this.findPotion(36, 45);
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
    
    private int findPotion(final int startSlot, final int endSlot) {
        for (int i = startSlot; i < endSlot; ++i) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() == Items.potionitem && ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : ((ItemPotion)stack.getItem()).getEffects(stack)) {
                    if (((PotionEffect)o).getPotionID() == Potion.heal.id) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
}
