package net.minecraft.client.gui;

import badgamesinc.hypnotic.util.ColorUtil;
import badgamesinc.hypnotic.util.font.FontManager;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui
{
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
    public GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 18, false, false, false);


    /** Button width in pixels */
    protected int width;

    /** Button height in pixels */
    protected int height;

    /** The x position of this control. */
    public int xPosition;

    /** The y position of this control. */
    public int yPosition;

    /** The string displayed on this control. */
    public String displayString;
    public int id;

    /** True if this control is enabled, false to disable. */
    public boolean enabled;

    /** Hides the button completely if false. */
    public boolean visible;
    protected boolean hovered;
    
    /** 0 = Default, 1 = Comfortaa, 2 = Jello font, 3 = Roboto, 4 = Minecraft **/
    protected int fontType = 0;

    public GuiButton(int buttonId, int x, int y, String buttonText)
    {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText)
    {
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
    }
    
    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, int fontType)
    {
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
        this.fontType = fontType;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean mouseOver)
    {
        int i = 1;

        if (!this.enabled)
        {
            i = 0;
        }
        else if (mouseOver)
        {
            i = 2;
        }

        return i;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            //this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
            //this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
            int j = -1;
            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = ColorUtil.getClickGUIColor().getRGB();
            }
            switch(fontType) {
        	case 0:
        		fontRenderer.drawCenteredString(this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 12) / 2, j, true);
        		break;
        	case 1:
        		FontManager.comfortaa.drawCenteredString(this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 4) / 2, j);
        		break;
        	case 2:
        		FontManager.bigJello.drawCenteredString(this.displayString, this.xPosition + this.width / 2 - 4, this.yPosition + (this.height - 7) / 2, j);
        		break;
        	case 3:
        		FontManager.roboto.drawCenteredString(this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 6) / 2, j);
        		break;
        	case 4:
        		Gui.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 12) / 2, j);
        		break;
            }
            if (this.hovered) {
            	Gui.drawRect(this.xPosition + 2, this.yPosition + 2, this.xPosition + this.width - 2, this.yPosition + this.height - 2, 0x30f0f0f0);
            }
            Gui.drawRect(this.xPosition + 2, this.yPosition, this.xPosition, this.yPosition + this.height, -1);
            Gui.drawRect(this.xPosition + 2, this.yPosition + 2, this.xPosition + this.width, this.yPosition, -1);
            Gui.drawRect(this.xPosition + 2, this.yPosition + this.height - 2, this.xPosition + this.width, this.yPosition + this.height, -1);
            Gui.drawRect(this.xPosition + this.width - 2, this.yPosition + 2, this.xPosition + this.width, this.yPosition + this.height, -1);
            Gui.drawRect(this.xPosition + 2, this.yPosition + 2, this.xPosition + this.width - 2, this.yPosition + this.height - 2, 0x44000000);
            this.mouseDragged(mc, mouseX, mouseY);
            

            
            

            
        }
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY)
    {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    /**
     * Whether the mouse cursor is currently over the button.
     */
    public boolean isMouseOver()
    {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY)
    {
    }

    public void playPressSound(SoundHandler soundHandlerIn)
    {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getButtonWidth()
    {
        return this.width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
}
