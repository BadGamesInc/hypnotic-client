package net.minecraft.client.gui.inventory;

import badgamesinc.hypnotic.Hypnotic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiChest extends GuiContainer
{
    /** The ResourceLocation containing the chest GUI texture. */
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;

    /**
     * window height is calculated with these values; the more rows, the heigher
     */
    private int inventoryRows;

    public GuiChest(IInventory upperInv, IInventory lowerInv)
    {
        super(new ContainerChest(upperInv, lowerInv, Minecraft.getMinecraft().thePlayer));
        this.upperChestInventory = upperInv;
        this.lowerChestInventory = lowerInv;
        this.allowUserInput = false;
        int i = 222;
        int j = i - 108;
        this.inventoryRows = lowerInv.getSizeInventory() / 9;
        this.ySize = j + this.inventoryRows * 18;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items). Args : mouseX, mouseY
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRendererObj.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Args : renderPartialTicks, mouseX, mouseY
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        if (Hypnotic.instance.moduleManager.getModuleByName("ChestStealer").isEnabled()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < GuiChest.this.inventoryRows * 9; ++i) {
                            final Slot slot = GuiChest.this.inventorySlots.inventorySlots.get(i);
                            if (slot.getStack() != null) {
                                Thread.sleep((int) Hypnotic.instance.moduleManager.stealer.speed.getValue());
                                GuiChest.this.handleMouseClick(slot, slot.slotNumber, 0, 1);
                                GuiChest.this.handleMouseClick(slot, slot.slotNumber, 0, 6);
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }
}
