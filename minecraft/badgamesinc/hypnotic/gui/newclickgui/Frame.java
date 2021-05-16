package badgamesinc.hypnotic.gui.newclickgui;

import java.util.ArrayList;

import badgamesinc.hypnotic.Hypnotic;
import badgamesinc.hypnotic.gui.newclickgui.button.Button;
import badgamesinc.hypnotic.module.Category;
import badgamesinc.hypnotic.module.Mod;
import badgamesinc.hypnotic.util.ColorUtils;
import badgamesinc.hypnotic.util.pcp.GlyphPageFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Frame {

	public float x, y;
	public float width;
	public float height;
	public float guiColor;
	public boolean extended = false;
	
	Category category;
	Minecraft mc = Minecraft.getMinecraft();
	public static GlyphPageFontRenderer fontRenderer = GlyphPageFontRenderer.create("Roboto-Medium", 18, false, false, false);
	
	public ArrayList<Button> buttons;
	
	public Frame (Category category, float x, float y) {
		this.x = x;
		this.y = y;
		this.width = 105;
	    this.height = 15 + fontRenderer.getFontHeight() - 10;
	    this.category = category;
	    
	    
	    buttons = new ArrayList<>();
	    
	    int offsetY = (int) (height);
	    for (Mod m : Hypnotic.instance.moduleManager.getModulesInCategory(category)) {
	    	buttons.add(new Button(m, x, y + offsetY, this));
	    	offsetY += height + 3;
	    }
	}
	
	
	
	public void render (int mouseX, int mouseY) {
		String title = Character.toUpperCase(category.name().toLowerCase().charAt(0)) + category.name().toLowerCase().substring(1);
		String extendedText = extended ? "+" : "-";
	    
		int rainbowOrder = 0;
		
		if (title.equalsIgnoreCase("Combat")) {
			rainbowOrder = 0;
		} else if (title.equalsIgnoreCase("Movement")) {
			rainbowOrder = 1;
		} else if (title.equalsIgnoreCase("Player")) {
			rainbowOrder = 2;
		} else if (title.equalsIgnoreCase("Render")) {
			rainbowOrder = 4;
		} else if (title.equalsIgnoreCase("World")) {
			rainbowOrder = 5;
		} else if (title.equalsIgnoreCase("Misc")) {
			rainbowOrder = 6;
		} else if (title.equalsIgnoreCase("Gui")) {
			rainbowOrder = 7;
		}
	    guiColor = ColorUtils.rainbow(7, 0.5f, 0.5f, (long) (rainbowOrder * 110));
		
		if (Button.settingsOpen)
			return;	

	    Gui.drawRect(x, y, x + width, y + height, (int) guiColor);
        fontRenderer.drawString(title, x + 2, y + 2, -1, true);
        fontRenderer.drawString(extendedText, x + width - 10, y + 2, -1, true);
        
        for (Button button : buttons) {
        	button.draw(mouseX, mouseY);
        }
	}
	
	public void onClick(int mouseX, int mouseY, int mouseButton) {
		for (Button button : buttons) {
        	button.onClick(mouseX, mouseY, mouseButton);
        }
	}
}
