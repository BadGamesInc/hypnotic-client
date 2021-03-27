package badgamesinc.hypnotic.gui;

import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.util.ResourceLocation;

public class MainMenu extends GuiScreen {

	public MainMenu() {
		
	}
	
	public void initGui() {
		
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		mc.getTextureManager().bindTexture(new ResourceLocation("Hypnotic/MainMenu.jpg"));
		this.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
		
		//this.drawRect(0, height - 50, width, height, 0xffff56f9);
		//this.drawRect(0, height - 55, width, height - 50, -1);
		
		String[] buttons = { "Singleplayer", "Multiplayer", "Options", "Alts", "Quit"};
		
		int count = 0;
		for(String name : buttons) {
			this.drawCenteredString(this.fontRendererObj, name, (int) (width / buttons.length) * count + (width / buttons.length) / 2f + 8, height - 30, -1);
			count++;
		}
	
	}
	
	public void mouseCliecked(int mouseX, int mouseY, int button) {
		String[] buttons = { "Singleplayer", "Multiplayer", "Options", "Alts", "Quit" };
		
		int count = 0;
		for(String name : buttons) {
			float x = (width / buttons.length) * count + (width / buttons.length) / 2f + 8 - mc.fontRendererObj.getStringWidth(name) / 2f;
			float y = height - 30;
			
			if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRendererObj.getStringWidth(name) && mouseY < y + mc.fontRendererObj.FONT_HEIGHT) {
				switch(name) {
					case "Singleplayer":
						mc.displayGuiScreen(new GuiSelectWorld(this));
						break;
						
					case "Multiplayer":
						mc.displayGuiScreen(new GuiMultiplayer(this));
						break;
				}
			}
			
			count++;
		}
	}
	
	public void onGuiClosed() {
		
	}
}
