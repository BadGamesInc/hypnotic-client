package badgamesinc.hypnotic.module.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventMotionUpdate;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.Stopwatch;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class InventoryManager extends Mod {

	public static final Stopwatch INV_STOPWATCH = new Stopwatch();
	private List allSwords = new ArrayList();
	private List[] allArmors = new List[4];
    private List trash = new ArrayList();
	private boolean cleaning;
	private int[] bestArmorSlot;
	private int bestSwordSlot;
	public NumberSetting delay = new NumberSetting("Delay", 100, 0, 1000, 10);
	public NumberSetting swordSlot = new NumberSetting("Sword Slot", 1, 1, 9, 1);
	
    public InventoryManager() {
        super("InvManager", Keyboard.KEY_NONE, Category.PLAYER, "Ejects garbage and sorts your inventory");
        this.addSettings(swordSlot, delay);
    }

    @EventTarget
    public void eventMotion(EventMotionUpdate event) {
    	if (mc.currentScreen instanceof GuiInventory && event.isPre()) {
            this.collectItems();
            this.collectBestArmor();
            this.collectTrash();
            int trashSize = this.trash.size();
            boolean trashPresent = trashSize > 0;
            EntityPlayerSP player = mc.thePlayer;
            int windowId = player.openContainer.windowId;
            int bestSwordSlot = this.bestSwordSlot;
            if (trashPresent) {
               if (!this.cleaning) {
                  this.cleaning = true;
                  player.sendQueue.addToSendQueueSilent(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
               }

               for(int i = 0; i < trashSize; ++i) {
                  int slot = (Integer)this.trash.get(i);
                  if (this.checkDelay()) {
                     break;
                  }

                  mc.playerController.windowClick(windowId, slot < 9 ? slot + 36 : slot, 1, 4, player);
                  INV_STOPWATCH.reset();
               }

               if (this.cleaning) {
                  player.sendQueue.addToSendQueueSilent(new C0DPacketCloseWindow(windowId));
                  this.cleaning = false;
               }
            }

            if (bestSwordSlot != -1 && !this.checkDelay()) {
               mc.playerController.windowClick(windowId, bestSwordSlot < 9 ? bestSwordSlot + 36 : bestSwordSlot, ((Double)this.swordSlot.getValue()).intValue() - 1, 2, player);
               INV_STOPWATCH.reset();
            }
         }
    }
    
    private boolean checkDelay() {
        return !INV_STOPWATCH.elapsed(((Double)this.delay.getValue()).longValue());
     }

     public void collectItems() {
        this.bestSwordSlot = -1;
        this.allSwords.clear();
        float bestSwordDamage = -1.0F;

        for(int i = 0; i < 36; ++i) {
           ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
           if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemSword) {
              float damageLevel = getDamageLevel(itemStack);
              this.allSwords.add(i);
              if (bestSwordDamage < damageLevel) {
                 bestSwordDamage = damageLevel;
                 this.bestSwordSlot = i;
              }
           }
        }

     }
    
    private void collectBestArmor() {
        int[] bestArmorDamageReducement = new int[4];
        this.bestArmorSlot = new int[4];
        Arrays.fill(bestArmorDamageReducement, -1);
        Arrays.fill(this.bestArmorSlot, -1);

        int i;
        ItemStack itemStack;
        ItemArmor armor;
        int armorType;
        for(i = 0; i < this.bestArmorSlot.length; ++i) {
           itemStack = mc.thePlayer.inventory.armorItemInSlot(i);
           this.allArmors[i] = new ArrayList();
           if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor) {
              armor = (ItemArmor)itemStack.getItem();
              armorType = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
              bestArmorDamageReducement[i] = armorType;
           }
        }

        for(i = 0; i < 36; ++i) {
           itemStack = mc.thePlayer.inventory.getStackInSlot(i);
           if (itemStack != null && itemStack.getItem() != null && itemStack.getItem() instanceof ItemArmor) {
              armor = (ItemArmor)itemStack.getItem();
              armorType = 3 - armor.armorType;
              this.allArmors[armorType].add(i);
              int slotProtectionLevel = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
              if (bestArmorDamageReducement[armorType] < slotProtectionLevel) {
                 bestArmorDamageReducement[armorType] = slotProtectionLevel;
                 this.bestArmorSlot[armorType] = i;
              }
           }
        }

     }

     private void collectTrash() {
        this.trash.clear();

        int i;
        for(i = 0; i < 36; ++i) {
           ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
           if (itemStack != null && itemStack.getItem() != null && !isValidItem(itemStack)) {
              this.trash.add(i);
           }
        }

        for(i = 0; i < this.allArmors.length; ++i) {
           List armorItem = this.allArmors[i];
           if (armorItem != null) {
              List integers = this.trash;
              int i1 = 0;

              for(int armorItemSize = armorItem.size(); i1 < armorItemSize; ++i1) {
                 Integer slot = (Integer)armorItem.get(i1);
                 if (slot != this.bestArmorSlot[i]) {
                    integers.add(slot);
                 }
              }
           }
        }

        List integers = this.trash;
        int i1 = 0;

        for(int allSwordsSize = this.allSwords.size(); i1 < allSwordsSize; ++i1) {
           Integer slot = (Integer)this.allSwords.get(i1);
           if (slot != this.bestSwordSlot) {
              integers.add(slot);
           }
        }

     }

     public boolean isValidItem(ItemStack itemStack) {
         if (itemStack.getDisplayName().startsWith("§a")) {
            return true;
         } else {
        	Item enchantTable = ItemBlock.getItemFromBlock(Blocks.enchanting_table);
            return (itemStack.getItem() instanceof ItemEnderPearl || itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemFood || itemStack.getItem() instanceof ItemPotion && !isBadPotion(itemStack) || itemStack.getItem() instanceof ItemBlock || itemStack.getDisplayName().contains("Play") || itemStack.getDisplayName().contains("Game") || itemStack.getDisplayName().contains("Right Click")) && itemStack.getItem() != enchantTable;
         }
         
      }
     
     public boolean isBadPotion(ItemStack stack) {
         if (stack != null && stack.getItem() instanceof ItemPotion) {
            ItemPotion potion = (ItemPotion)stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
               Iterator var2 = potion.getEffects(stack).iterator();

               while(var2.hasNext()) {
                  Object o = var2.next();
                  PotionEffect effect = (PotionEffect)o;
                  if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId() || effect.getPotionID() == Potion.moveSlowdown.getId() || effect.getPotionID() == Potion.weakness.getId()) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
     public static float getDamageLevel(ItemStack stack) {
         if (stack.getItem() instanceof ItemSword) {
            ItemSword sword = (ItemSword)stack.getItem();
            float sharpness = (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F;
            float fireAspect = (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 1.5F;
            return sword.getDamageVsEntity() + sharpness + fireAspect;
         } else {
            return 0.0F;
         }
      }
}
