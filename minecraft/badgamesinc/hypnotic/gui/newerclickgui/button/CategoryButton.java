package badgamesinc.hypnotic.gui.newerclickgui.button;

import java.awt.Color;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newerclickgui.ClickGUI;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.util.RenderUtils;
import badgamesinc.hypnotic.util.font.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class CategoryButton {
    Minecraft mc = Minecraft.getMinecraft();
    int x, y;
    int width, height;
    Category category;
    int mouseTicks = 0;
    ClickGUI parent;
    public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Comfortaa-Medium.ttf", 22, false, false, false);

    public CategoryButton(int x, int y, Category category, ClickGUI parent) {
        this.x = x;
        this.y = y;
        this.category = category;
        this.parent = parent;
        this.width = 45;
        this.height = 45;
    }

    public void draw(int mouseX, int mouseY){
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();

        if(isHovered(mouseX, mouseY)){
            if(mouseTicks < 1){
                mouseTicks++;
            }
        }else {
            if(mouseTicks > 0){
                mouseTicks--;
            }
        }
        
        if (this.category != Category.HIDDEN) {
	        String categoryName = Character.toUpperCase(category.name().toLowerCase().charAt(0)) + category.name().toLowerCase().substring(1);
			boolean isCurrent = parent.currentCategory == this.category;
	        int color = isCurrent ? ClickGUI.color : (isHovered(mouseX, mouseY) ? ClickGUI.color : -1);
	
			if (isHovered(mouseX, mouseY))
		        RenderUtils.drawRoundedRect(parent.width / 6 + 3, y + 13 - mouseTicks, parent.width / 6 + 77, y + 13 - mouseTicks + 20, 5, new Color(100, 100, 100, 255));
			if (isCurrent) 
	            RenderUtils.drawRoundedRect(parent.width / 6 + 3, y + 13 - mouseTicks, parent.width / 6 + 77, y + 13 - mouseTicks + 20, 5, new Color(color));
			fontRenderer.drawString(categoryName, x - 6 - mouseTicks, y + 15 - mouseTicks, -1, true);      
        }
    }

    public boolean isHovered(int mouseX, int mouseY) {
    	if (Button.open == true || ConfigsButton.open == true || this.category == Category.HIDDEN) {
    		return false;
    	}
        return mouseX > x - 10 && mouseX < x + width + 13 && mouseY > y + 8 && mouseY < y + height - 13;
    }

    public void mouseClicked(int mouseX, int mouseY){
        if(isHovered(mouseX, mouseY)){
        	if (Hypnotic.instance.moduleManager.clickGui.sound.isEnabled()) {
        		mc.thePlayer.playSound("random.click", 0.5f, 0.5f);
        	}
            parent.currentCategory = this.category;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
