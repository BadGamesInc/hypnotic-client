package badgamesinc.hypnotic.module.player;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.event.Event;
import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.event.events.EventPreMotionUpdate;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.Setting;
import badgamesinc.hypnotic.util.Timer;
import badgamesinc.hypnotic.util.TimerUtils;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class InventoryManager extends Mod{

private TimerUtils timer;
    
    public InventoryManager() {
        super("InvManager", 0, Category.PLAYER, "Manage your inventory");
        this.timer = new TimerUtils();
    }
    
    @Override
    public void setup() {
        Hypnotic.instance.setmgr.rSetting(new Setting("Delay", this, 200.0, 20.0, 1000.0, true));
        Hypnotic.instance.setmgr.rSetting(new Setting("SwordSlot", this, 1.0, 1.0, 9.0, true));
        Hypnotic.instance.setmgr.rSetting(new Setting("Clean", this, true));
        Hypnotic.instance.setmgr.rSetting(new Setting("CleanBadItems", this, true));
    }
    
    @EventTarget
    public void onPreMotionUpdate(EventMotionUpdate e) {
    	if(e.getState() == Event.State.PRE) {
	        final double delay = Math.max(20.0, Hypnotic.instance.setmgr.getSettingByName("Delay").getValDouble() + ThreadLocalRandom.current().nextDouble(-20.0, 20.0));
	        if (mc.currentScreen != null) {
	            this.timer.reset();
	            return;
	        }
	        if (this.timer.hasTimeElapsed((long)delay, true)) {
	            final int bestSword = this.getBestSword();
	            final int bestPick = this.getBestPickaxe();
	            final int bestAxe = this.getBestAxe();
	            final int bestShovel = this.getBestShovel();
	            for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; ++k) {
	                final ItemStack is = mc.thePlayer.inventory.mainInventory[k];
	                if (is != null && !(is.getItem() instanceof ItemArmor)) {
	                    final boolean clean = Hypnotic.instance.setmgr.getSettingByName("Clean").getValBoolean();
	                    if (clean) {
	                        if (is.getItem() instanceof ItemSword && bestSword != -1 && bestSword != k) {
	                            this.drop(k, is);
	                            this.timer.reset();
	                            return;
	                        }
	                        if (is.getItem() instanceof ItemPickaxe && bestPick != -1 && bestPick != k) {
	                            this.drop(k, is);
	                            this.timer.reset();
	                            return;
	                        }
	                        if (is.getItem() instanceof ItemAxe && bestAxe != -1 && bestAxe != k) {
	                            this.drop(k, is);
	                            this.timer.reset();
	                            return;
	                        }
	                        if (this.isShovel(is.getItem()) && bestShovel != -1 && bestShovel != k) {
	                            this.drop(k, is);
	                            this.timer.reset();
	                            return;
	                        }
	                    }
	                    final int swordSlot = (int)(Hypnotic.instance.setmgr.getSettingByName("SwordSlot").getValDouble() - 1.0);
	                    if (bestSword != -1 && bestSword != swordSlot) {
	                        for (int i = 0; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
	                            final Slot s = mc.thePlayer.inventoryContainer.inventorySlots.get(i);
	                            if (s.getHasStack() && s.getStack() == mc.thePlayer.inventory.mainInventory[bestSword]) {
	                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, s.slotNumber, swordSlot, 2, mc.thePlayer);
	                                this.timer.reset();
	                                return;
	                            }
	                        }
	                    }
	                    if (Hypnotic.instance.setmgr.getSettingByName("CleanBadItems").getValBoolean() && this.isBad(is.getItem())) {
	                        this.drop(k, is);
	                        this.timer.reset();
	                        return;
	                    }
	                }
	            }
	        }
    	}
    }
    
    private int getBestSword() {
        int bestSword = -1;
        float bestDamage = 1.0f;
        for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; ++k) {
            final ItemStack is = mc.thePlayer.inventory.mainInventory[k];
            if (is != null && is.getItem() instanceof ItemSword) {
                final ItemSword itemSword = (ItemSword)is.getItem();
                float damage = itemSword.getDamageVsEntity();
                damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, is);
                if (damage > bestDamage) {
                    bestDamage = damage;
                    bestSword = k;
                }
            }
        }
        return bestSword;
    }
    
    private int getBestPickaxe() {
        int bestPick = -1;
        float bestDamage = 1.0f;
        for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; ++k) {
            final ItemStack is = mc.thePlayer.inventory.mainInventory[k];
            if (is != null && is.getItem() instanceof ItemPickaxe) {
                final ItemPickaxe itemSword = (ItemPickaxe)is.getItem();
                final float damage = itemSword.getStrVsBlock(is, Block.getBlockById(4));
                if (damage > bestDamage) {
                    bestDamage = damage;
                    bestPick = k;
                }
            }
        }
        return bestPick;
    }
    
    private int getBestAxe() {
        int bestPick = -1;
        float bestDamage = 1.0f;
        for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; ++k) {
            final ItemStack is = mc.thePlayer.inventory.mainInventory[k];
            if (is != null && is.getItem() instanceof ItemAxe) {
                final ItemAxe itemSword = (ItemAxe)is.getItem();
                final float damage = itemSword.getStrVsBlock(is, Block.getBlockById(17));
                if (damage > bestDamage) {
                    bestDamage = damage;
                    bestPick = k;
                }
            }
        }
        return bestPick;
    }
    
    private int getBestShovel() {
        int bestPick = -1;
        float bestDamage = 1.0f;
        for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; ++k) {
            final ItemStack is = mc.thePlayer.inventory.mainInventory[k];
            if (is != null && this.isShovel(is.getItem())) {
                final ItemTool itemSword = (ItemTool)is.getItem();
                final float damage = itemSword.getStrVsBlock(is, Block.getBlockById(3));
                if (damage > bestDamage) {
                    bestDamage = damage;
                    bestPick = k;
                }
            }
        }
        return bestPick;
    }
    
    private boolean isShovel(final Item is) {
        return Item.getItemById(256) == is || Item.getItemById(269) == is || Item.getItemById(273) == is || Item.getItemById(277) == is || Item.getItemById(284) == is;
    }
    
    private void drop(final int slot, final ItemStack item) {
        boolean hotbar = false;
        for (int k = 0; k < 9; ++k) {
            final ItemStack itemK = mc.thePlayer.inventory.getStackInSlot(k);
            if (itemK != null && itemK == item) {
                hotbar = true;
            }
        }
        if (hotbar) {
            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));
            final C07PacketPlayerDigging.Action diggingAction = C07PacketPlayerDigging.Action.DROP_ALL_ITEMS;
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(diggingAction, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
        else {
            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 0, mc.thePlayer);
            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, mc.thePlayer);
        }
    }
    
    private boolean isBad(final Item i) {
        return i.getUnlocalizedName().contains("tnt") 
        		|| i.getUnlocalizedName().contains("stick")
        		|| i.getUnlocalizedName().contains("egg")
        		|| i.getUnlocalizedName().contains("string")
        		|| i.getUnlocalizedName().contains("flint")
        		|| i.getUnlocalizedName().contains("bucket")
        		|| i.getUnlocalizedName().contains("feather")
        		|| i.getUnlocalizedName().contains("snow")
        		|| i.getUnlocalizedName().contains("piston")
        		|| i instanceof ItemGlassBottle ||
        		i.getUnlocalizedName().contains("web")
        		|| i.getUnlocalizedName().contains("slime")
        		|| i.getUnlocalizedName().contains("trip")
        		|| i.getUnlocalizedName().contains("wire")
        		|| i.getUnlocalizedName().contains("sugar")
        		|| i.getUnlocalizedName().contains("note")
        		|| i.getUnlocalizedName().contains("record")
        		|| i.getUnlocalizedName().contains("flower")
        		|| i.getUnlocalizedName().contains("wheat")
        		|| i.getUnlocalizedName().contains("fishing")
        		|| i.getUnlocalizedName().contains("boat")
        		|| i.getUnlocalizedName().contains("leather")
        		|| i.getUnlocalizedName().contains("seeds")
        		|| i.getUnlocalizedName().contains("skull") 
        		|| i.getUnlocalizedName().contains("torch") 
        		|| i.getUnlocalizedName().contains("anvil") 
        		|| i.getUnlocalizedName().contains("enchant") 
        		|| i.getUnlocalizedName().contains("exp") 
        		|| i.getUnlocalizedName().contains("shears");
    }
}
