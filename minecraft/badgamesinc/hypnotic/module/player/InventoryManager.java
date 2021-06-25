package badgamesinc.hypnotic.module.player;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
import org.lwjgl.input.Keyboard;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.EventReceivePacket;
import badgamesinc.hypnotic.event.events.EventSendPacket;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.TimeHelper;
import badgamesinc.hypnotic.util.Timer;

import java.util.Arrays;
import java.util.List;

public class InventoryManager extends Mod {

    public BooleanSetting spoof = new BooleanSetting("Spoof Inventory", true);
    public NumberSetting delay = new NumberSetting("Click Delay", 170, 20, 300, 10);
    public Timer timer = new Timer();
    public boolean inventoryOpen;
    public List<String> junk = Arrays.asList("stick", "egg", "string", "cake", "mushroom", "flint", "dyePowder", "feather", "chest", "snowball", "fish", "enchant", "exp", "shears", "anvil", "torch", "seeds", "leather", "reeds", "skull", "record", "piston", "snow", "poison");

    public InventoryManager() {
        super("InvManager", Keyboard.KEY_NONE, Category.PLAYER, "Ejects garbage and sorts your inventory");
        this.addSettings(spoof, delay);
    }

    public static float getSwordStrength(ItemStack stack) {
        return (!(stack.getItem() instanceof ItemSword) ? 0.0F : (float) EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F) + (!(stack.getItem() instanceof ItemSword) ? 0.0F : (float) EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack));
    }
    
    @EventTarget
    public void sendPacket(EventSendPacket event) {
    	Packet packet = event.getPacket();
    	if (packet instanceof C16PacketClientStatus) {
            C16PacketClientStatus open = (C16PacketClientStatus) packet;
            if (open.getStatus() == EnumState.OPEN_INVENTORY_ACHIEVEMENT)
                inventoryOpen = true;
        }
        if (packet instanceof C0DPacketCloseWindow) {
            inventoryOpen = false;
        }
    }
    
    @EventTarget
    public void receivePacket(EventReceivePacket event) {
    	Packet packet = event.getPacket();
    }
    
    @Override
    public void onUpdate() {
    	if ((mc.currentScreen == null || (mc.currentScreen instanceof GuiContainer && ((GuiContainer) mc.currentScreen).inventorySlots == mc.thePlayer.inventoryContainer)) && purgeUnusedArmor() && purgeUnusedTools() && purgeJunk() && manageSword()) {
            if (hotbarHasSpace())
                manageHotbar();
        }
    	super.onUpdate();
    }

    private boolean manageSword() {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null) {
                Item item = stack.getItem();
                if (!stack.getDisplayName().toLowerCase().contains("(right click)") && item instanceof ItemSword && timer.hasTimeElapsed((long) delay.getValue(), true)) {
                    moveToHotbarSlot1(i);
                    return false;
                }
            }
        }
        return true;
    }

    public boolean purgeUnusedArmor() {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null) {
                Item item = stack.getItem();

                if (item instanceof ItemArmor) {
                    if (!isBestArmor(stack) && timer.hasTimeElapsed((long) delay.getValue(), true)) {
                        purge(i);
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean purgeUnusedTools() {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null) {
                Item item = stack.getItem();

                if (item instanceof ItemTool) {
                    if (!stack.getDisplayName().toLowerCase().contains("(right click)") && !isBestTool(stack) && timer.hasTimeElapsed((long) delay.getValue(), true)) {
                        purge(i);
                        return false;
                    }
                }
                if (item instanceof ItemSword) {
                    if (!stack.getDisplayName().toLowerCase().contains("(right click)") && !isBestSword(stack) && timer.hasTimeElapsed((long) delay.getValue(), true)) {
                        purge(i);
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean purgeJunk() {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null) {
                Item item = stack.getItem();

                for (String shortName : junk) {
                    if (!stack.getDisplayName().toLowerCase().contains("(right click)") && item.getUnlocalizedName().contains(shortName) && timer.hasTimeElapsed((long) delay.getValue(), true)) {
                        purge(i);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void manageHotbar() {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null) {
                Item item = stack.getItem();

                if (!stack.getDisplayName().toLowerCase().contains("(right click)") &&
                        ((item instanceof ItemPickaxe && hotbarNeedsItem(ItemPickaxe.class)) || (item instanceof ItemAxe && hotbarNeedsItem(ItemAxe.class)) || (item instanceof ItemSword && hotbarNeedsItem(ItemSword.class)) ||
                                (item instanceof ItemAppleGold && hotbarNeedsItem(ItemAppleGold.class)) || (item instanceof ItemEnderPearl && hotbarNeedsItem(ItemEnderPearl.class)) || (item instanceof ItemBlock && (((ItemBlock) item).getBlock().isFullCube()) &&
                                !hotbarHasBlocks())) &&
                        !hotbar && timer.hasTimeElapsed((long) delay.getValue(), true)) {
                    moveToHotbar(i);
                    return;
                }
            }
        }
    }

    public boolean hotbarHasSpace() {
        for (int i = 36; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);

            if (slot.getStack() == null)
                return true;
        }
        return false;
    }

    public boolean hotbarNeedsItem(Class<?> type) {
        for (int i = 36; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);

            if (type.isInstance(slot.getStack()))
                return false;
        }
        return true;
    }

    public boolean hotbarHasBlocks() {
        for (int i = 36; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);

            if (slot.getStack() != null && slot.getStack().getItem() instanceof ItemBlock && ((ItemBlock) slot.getStack().getItem()).getBlock().isFullCube())
                return true;
        }
        return false;
    }

    public boolean isBestTool(ItemStack compareStack) {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null && compareStack != stack && stack.getItem() instanceof ItemTool) {
                ItemTool item = (ItemTool) stack.getItem();
                ItemTool compare = (ItemTool) compareStack.getItem();
                if (item.getClass() == compare.getClass()) {
                    if (compare.getStrVsBlock(stack, preferredBlock(item.getClass())) <= item.getStrVsBlock(compareStack, preferredBlock(compare.getClass())))
                        return false;
                }
            }
        }

        return true;
    }

    public boolean isBestSword(ItemStack compareStack) {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null && compareStack != stack && stack.getItem() instanceof ItemSword) {
                ItemSword item = (ItemSword) stack.getItem();
                ItemSword compare = (ItemSword) compareStack.getItem();
                if (item.getClass() == compare.getClass()) {
                    if (compare.attackDamage + getSwordStrength(compareStack) <= item.attackDamage + getSwordStrength(stack))
                        return false;
                }
            }
        }

        return true;
    }

    public Block preferredBlock(Class clazz) {
        return clazz == ItemPickaxe.class ? Blocks.cobblestone : clazz == ItemAxe.class ? Blocks.log : Blocks.dirt;
    }

    public boolean isBestArmor(ItemStack compareStack) {
        for (int i = 0; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
            ItemStack stack = slot.getStack();
            boolean hotbar = i >= 36;

            if (stack != null && compareStack != stack && stack.getItem() instanceof ItemArmor) {
                ItemArmor item = (ItemArmor) stack.getItem();
                ItemArmor compare = (ItemArmor) compareStack.getItem();
                boolean noArmor = mc.thePlayer.getCurrentArmor(0) == null || mc.thePlayer.getCurrentArmor(1) == null || mc.thePlayer.getCurrentArmor(2) == null || mc.thePlayer.getCurrentArmor(3) == null;
                if (item.armorType == compare.armorType) {
                    if (AutoArmor.getProtectionValue(compareStack) <= AutoArmor.getProtectionValue(stack) && !noArmor)
                        return false;
                }
            }
        }

        return true;
    }

    public boolean has(Item item, int count) {
        for (int i = 9; i < 45; i++) {
            Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);

            if (slot.getStack() != null && (slot.getStack().getItem().equals(item)))
                count -= slot.getStack().stackSize;
        }
        return count >= 0;
    }

    public void moveToHotbar(int slot) {
        if (spoof.isEnabled())
            openInvPacket();

        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, mc.thePlayer);

        if (spoof.isEnabled())
            closeInvPacket();
    }

    public void moveToHotbarSlot1(int slot) {
        if (spoof.isEnabled())
            openInvPacket();

        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 2, mc.thePlayer);

        if (spoof.isEnabled())
            closeInvPacket();
    }

    public void purge(int slot) {
        if (spoof.isEnabled())
            openInvPacket();

        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, mc.thePlayer);

        if (spoof.isEnabled())
            closeInvPacket();
    }

    public void openInvPacket() {
        if (!inventoryOpen)
            mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(EnumState.OPEN_INVENTORY_ACHIEVEMENT));

        inventoryOpen = true;
    }

    public void closeInvPacket() {
        if (inventoryOpen)
            mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));

        inventoryOpen = false;
    }

}
