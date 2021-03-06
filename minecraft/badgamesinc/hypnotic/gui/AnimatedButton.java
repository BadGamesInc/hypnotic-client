package badgamesinc.hypnotic.gui;

import java.awt.Color;

import badgamesinc.hypnotic.util.ColorUtil;
import badgamesinc.hypnotic.util.font.FontManager;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public class AnimatedButton extends GuiButton {
    int lastX = xPosition;
    int targetX;
    int lastX2 = xPosition + width;
    int targetX2;
    int lastY = yPosition;
    int targetY;
    int lastY2 = yPosition + height;
    int targetY2;
    int hoverTicks = 200;
    int hoverTicks2 = 0;
    int hoverTicks3 = 0;
    private final GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 18, false, false, false);


    public AnimatedButton(int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
    }

    public AnimatedButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY){
    	int color = ColorUtil.getClickGUIColor().getRGB();
        if(this.visible){
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            
            if(this.hovered){
            	color = new Color(255, 138, 196, 255).getRGB();
                targetX = xPosition + width + 5;
                targetX2 = xPosition;
                targetY = yPosition + height + 11;
                targetY2 = yPosition;

                if(lastX != targetX){
                    float diff = targetX - lastX;
                    targetX = lastX;
                    lastX += diff / 6;
                }
                if(lastX2 != targetX2){
                    float diff = targetX2 - lastX2;
                    targetX2 = lastX2;
                    lastX2 += diff / 6;
                }
                if(lastY != targetY){
                    float diff = targetY - lastY;
                    targetY = lastY;
                    lastY += diff / 12;
                }
                if(lastY2 != targetY2){
                    float diff = targetY2 - lastY2;
                    targetY2 = lastY2;
                    lastY2 += diff / 12;
                }
                if (hoverTicks > 100) {
                	hoverTicks-=15;
                }
                if (hoverTicks2 < this.width / 2 - 25) {
                	hoverTicks2+=5;
                }
                if (hoverTicks2 >= this.width / 2 - 25 && hoverTicks2 < this.width / 2 - 20) {
                	hoverTicks2+=3;
                }
                if (hoverTicks2 >= this.width / 2 - 20 && hoverTicks2 < this.width / 2 - 15) {
                	hoverTicks2+=2;
                }
                if (hoverTicks2 < this.width / 2) {
                	hoverTicks2+=2;
                }
                if (hoverTicks2 > this.width / 2) {
                	hoverTicks2 = this.width / 2;
                }
               // Gui.drawRect(xPosition, yPosition + 2, targetX, yPosition, 0xFFFFFFFF);
               // Gui.drawRect(xPosition + width, yPosition + height - 2, targetX2, yPosition + height, 0xFFFFFFFF);
                //Gui.drawRect(xPosition + width - 1, targetY, xPosition + width + 1, yPosition, 0xFFFFFFFF);
                //Gui.drawRect(xPosition + 1, targetY2, xPosition - 1, yPosition + height, 0xFFFFFFFF);
            } else {
            	//System.out.println(hoverTicks);
                targetX = xPosition;
                targetX2 = xPosition + width + 5;
                targetY = yPosition;
                targetY2 = yPosition + height + 11;

                if(lastX != targetX){
                    float diff = targetX - lastX;
                    targetX = lastX;
                    lastX += diff / 6;
                }
                if(lastX2 != targetX2){
                    float diff = targetX2 - lastX2;
                    targetX2 = lastX2;
                    lastX2 += diff / 6;
                }
                if(lastY != targetY){
                    float diff = targetY - lastY;
                    targetY = lastY;
                    lastY += diff / 12;
                }
                if(lastY2 != targetY2){
                    float diff = targetY2 - lastY2;
                    targetY2 = lastY2;
                    lastY2 += diff / 12;
                }
                if (hoverTicks < 220) {
                	hoverTicks+=10;
                }
                if (hoverTicks2 > 0) {
                	hoverTicks2-=5;
                }
                if (hoverTicks2 > 0 && hoverTicks2 < 5) {
                	hoverTicks2=0;
                }
                //Gui.drawRect(xPosition, yPosition, targetX, yPosition + 1, 0xFFFFFFFF);
                //Gui.drawRect(xPosition  + width, yPosition + height , targetX2, yPosition + height - 1, 0xFFFFFFFF);
                //Gui.drawRect(xPosition + width, targetY, xPosition + width + 1, yPosition, 0xFFFFFFFF);
                //Gui.drawRect(xPosition , targetY2, xPosition - 1, yPosition + height, 0xFFFFFFFF);
            }
            
            Gui.drawRect(this.xPosition + 2, this.yPosition + 0, this.xPosition + this.width - 2, this.yPosition + this.height - 0, new Color(20, 20, 20, hoverTicks).getRGB());
            //Gui.drawRect((this.xPosition + 2), this.yPosition + this.height - 3, this.xPosition + this.width - 2, this.yPosition + this.height - 0, -1);
            Gui.drawRect((this.xPosition + this.width / 2 + 2) - hoverTicks2, this.yPosition + this.height - 2, (this.xPosition + this.width / 2 + 2), this.yPosition + this.height - 0, -1);
            Gui.drawRect((this.xPosition + this.width / 2 - 2), this.yPosition + this.height - 2, (this.xPosition + this.width / 2 - 2) + hoverTicks2, this.yPosition + this.height - 0, -1);
            FontManager.roboto.drawCenteredString(this.displayString, (float) (xPosition + (((xPosition + width) - xPosition - 2) / 2.0D)), (float) (yPosition + (((yPosition + height) - yPosition) / 2.0D) - 4), isMouseOver() ? ColorUtil.getClickGUIColor().getRGB() : -1);


        }
    }
}
