package badgamesinc.hypnotic.module.misc;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

import badgamesinc.hypnotic.event.EventTarget;
import badgamesinc.hypnotic.event.events.Event2D;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.module.render.NameTags;
import badgamesinc.hypnotic.settings.settingtypes.BooleanSetting;
import badgamesinc.hypnotic.settings.settingtypes.NumberSetting;
import badgamesinc.hypnotic.util.ItemUtils;
import badgamesinc.hypnotic.util.TimeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;

public class ChestStealer extends Mod {

	private double xPos, yPos, zPos, minx;

    TimeHelper timer = new TimeHelper();
    public NumberSetting delaySet = new NumberSetting("Steal Speed", 100, 0, 500, 10);
    private BooleanSetting autoclose = new BooleanSetting("Close", true);
    private BooleanSetting baditems = new BooleanSetting("Bad Items", false);
    private BooleanSetting silent = new BooleanSetting("Silent", false);
    private BooleanSetting showItems = new BooleanSetting("Show Items With Silent", false);
    
    private double delay;
	
	public ChestStealer() {
		super("ChestStealer", 0, Category.MISC, "Steals all items from a chest");
		addSettings(delaySet, autoclose, baditems, silent, showItems);
	}
	
	GuiChest silentCurrent;
    
    public void onUpdate() {
    	
        if (this.isChestEmpty()) {
            this.setDelay();
        }

        if(!silent.isEnabled()) {

            if (mc.currentScreen instanceof GuiChest) {

                final GuiChest chest = (GuiChest) mc.currentScreen;
                boolean close = autoclose.isEnabled();
                if (isValidChest(chest)) {
                    if ((this.isChestEmpty() || ItemUtils.isInventoryFull()) && close && timer.hasReached((long) delay)) {
                        Minecraft.getMinecraft().thePlayer.closeScreen();
                        timer.reset();
                        return;
                    }

                    if (timer.hasReached((long) delay)) {
                        for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
                            final ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
                            if (stack != null && timer.hasReached((long) delay) && (!ItemUtils.isBad(stack) || baditems.isEnabled())) {
                                mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, mc.thePlayer);
                                this.setDelay();
                                this.timer.reset();
                                continue;
                            }
                        }
                        timer.reset();
                    }
                }
            }
        } else {
            if(mc.currentScreen instanceof GuiChest){
                silentCurrent = (GuiChest)mc.currentScreen;
                if (isValidChest(silentCurrent)) {
                    mc.setIngameFocus();
                    mc.currentScreen = null;
                }
            }
            if (silentCurrent != null) {
                final GuiChest chest = silentCurrent;
                boolean close = autoclose.isEnabled();
                if (isValidChest(chest)) {
                    if ((this.isChestEmpty(chest) || ItemUtils.isInventoryFull()) && close && timer.hasReached((long) delay)) {
                        Minecraft.getMinecraft().thePlayer.closeScreen();
                        silentCurrent = null;
                        timer.reset();
                        return;
                    }

                    if (timer.hasReached((long) delay)) {
                        for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
                            final ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
                            if (stack != null && timer.hasReached((long) delay) && (!ItemUtils.isBad(stack) || baditems.isEnabled())) {
                                mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, mc.thePlayer);
                                this.setDelay();
                                this.timer.reset();
                                continue;
                            }
                        }
                        timer.reset();
                    }
                }
            }
        }
    }

    private boolean isChestEmpty() {
        if (mc.currentScreen instanceof GuiChest) {
            final GuiChest chest = (GuiChest)mc.currentScreen;
            for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
                final ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
                if (stack != null && (!ItemUtils.isBad(stack) || baditems.isEnabled())) {
                    return false;
                }
            }
        }
        return true;
    }

    private void setDelay() {
        if (delaySet.getValue() <= 5) {
            this.delay = delaySet.getValue();
        } else {
            this.delay = delaySet.getValue() + ThreadLocalRandom.current().nextDouble(-40, 40);
        }

    }


    private boolean isValidChest(GuiChest chest) {

        int radius = 5;
        for(int x = -radius; x < radius; x++){
            for(int y = radius; y > -radius; y--){
                for(int z = -radius; z < radius; z++){
                    this.xPos = mc.thePlayer.posX + x;
                    this.yPos = mc.thePlayer.posY + y;
                    this.zPos = mc.thePlayer.posZ + z;

                    BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
                    Block block = mc.theWorld.getBlockState(blockPos).getBlock();

                    if(block instanceof BlockChest){
                        return true;

                    }
                }
            }
        }
        return false;
    }

    private boolean isValidItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemSword ||
                itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemFood ||
                itemStack.getItem() instanceof ItemPotion || itemStack.getItem() instanceof ItemBlock;
    }

    private boolean isChestEmpty(final GuiChest chest) {
        for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
            ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
            if (stack != null) {
                if (isValidItem(stack)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isInventoryFull() {
        for (int index = 9; index <= 44; ++index) {
            ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (stack == null) {
                return false;
            }
        }

        return true;
    }
    
    @EventTarget
    public void event2d(Event2D e) {
    	int x = 190;
    	if (silentCurrent != null && silent.isEnabled() && isValidChest(silentCurrent) && showItems.isEnabled()) {
    		Gui.drawRect(196 + x, 100, 380 + x, 170, new Color(0, 0, 0, 100).getRGB());
    		int off = 0;
    		for (int i = 0; i < 9; i++) {
    			int yoff = 0;
    			if (silentCurrent.lowerChestInventory.getStackInSlot(i) != null) {
	    			NameTags.renderItem(silentCurrent.lowerChestInventory.getStackInSlot(i), 200 + x + off * 20, 100 + yoff);
    			}
    			off++;
    		}
    		int off2 = 0;
    		for (int i = 9; i < 18; i++) {
    			int yoff = 0;
    			if (silentCurrent.lowerChestInventory.getStackInSlot(i) != null) {
	    			NameTags.renderItem(silentCurrent.lowerChestInventory.getStackInSlot(i), 200 + x + off2 * 20, 120 + yoff);
    			}
    			off2++;
    		}
    		int off3 = 0;
    		for (int i = 18; i < 27; i++) {
    			int yoff = 0;
    			if (silentCurrent.lowerChestInventory.getStackInSlot(i) != null) {
	    			NameTags.renderItem(silentCurrent.lowerChestInventory.getStackInSlot(i), 200 + x + off3 * 20, 140 + yoff);
    			}
    			off3++;
    		}
    	}
    }

}
